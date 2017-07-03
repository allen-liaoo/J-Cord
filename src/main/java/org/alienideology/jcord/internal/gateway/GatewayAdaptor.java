package org.alienideology.jcord.internal.gateway;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;
import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.ExceptionEvent;
import org.alienideology.jcord.event.handler.*;
import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.util.log.Logger;
import org.alienideology.jcord.util.log.LogLevel;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

/**
 * GatewayAdaptor - Communication java.client for Discord GateWay
 *
 * @author AlienIdeology
 */
public final class GatewayAdaptor extends WebSocketAdapter {

    public static int GATEWAY_VERSION = 5;
    public Logger LOG;

    private IdentityImpl identity;
    private WebSocket webSocket;
    private Thread heart;
    private long interval;

    /* Used for resuming and heartbeat */
    private int sequence;
    /* Use for resuming */
    private String session_id = null;

    /* <Event Name, Event Object> */
    public HashMap<String, EventHandler> eventHandler = new HashMap<>();

    /**
     * The listener for Gateway messages.
     *
     * @param identity The identity this gateway belongs to.
     * @param webSocket The WebSocket where events are fired.
     */
    public GatewayAdaptor(IdentityImpl identity, WebSocket webSocket) {
        this.identity = identity;
        this.webSocket = webSocket;
        LOG = identity.LOG.clone(this);
        setEventHandler();
    }

    @Override
    public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
        LOG.log(LogLevel.INFO, "[CONNECTION] Connected");
        identity.CONNECTION = Identity.Connection.CONNECTED;

        if (session_id == null || session_id.isEmpty()) {
            sendIdentification();
        } else {
            sendResume();
        }
    }

    /**
     * Json Message
     */
    @Override
    public void onTextMessage(WebSocket websocket, String text) throws Exception {
        JSONObject json = new JSONObject(text);

        /* Error Response */
        if (json.has("code")) {
            ErrorResponse response = ErrorResponse.getByKey(json.getInt("code"));
            handleError(new ErrorResponseException(response));
        /* Payload */
        } else {
            int opCode = json.getInt("op");
            handleOPCode(OPCode.getCode(opCode), text);
        }
    }

    @Override
    public void onBinaryMessage(WebSocket websocket, byte[] binary) throws Exception {

        /* Decoder */
        Inflater decoder = new Inflater();
        decoder.setInput(binary, 0, binary.length);
        byte[] result = new byte[128];

        StringBuilder decoded = new StringBuilder();

        while (!decoder.finished()) {
            int length = decoder.inflate(result);
            decoded.append(new String(result, 0, length, "UTF-8"));
        }

        onTextMessage(websocket, decoded.toString());
    }

    @Override
    public void onFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
        OPCode code = OPCode.getCode(frame.getOpcode());
        handleOPCode(code, frame.getPayloadText());
    }

    @Override
    public void onError(WebSocket websocket, WebSocketException cause) throws Exception {
        handleError(cause);
    }

    @Override
    public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
        LOG.log(LogLevel.INFO, "[CONNECTION] Disconnected");
        identity.CONNECTION = Identity.Connection.OFFLINE;
        DisconnectionCode code = DisconnectionCode.getCode(serverCloseFrame.getCloseCode());
        if(closedByServer) {
            handleError(new RuntimeException("Connection closed: "+code.code+"\tBy reason: "+code.message));
            LOG.log(LogLevel.FETAL, code+"\t"+code.message);
        }
    }

    /**
     * Handling OP Code
     * @param code OPCode to handle
     * @param message The json message
     */
    private void handleOPCode(OPCode code, String message) {
        JSONObject json = new JSONObject(message);
        switch (code) {
            /* Event */
            case DISPATCH: {
                handleEvent(json);
            }
            /* Server Side HandShake */
            case HELLO: {
                interval = json.getJSONObject("d").getLong("heartbeat_interval");
                sendHeartBeat();
                LOG.log(LogLevel.DEBUG, "[RECEIVED] Hello");
                break;
            }
            /* Client Side HandShake */
            case IDENTIFY: {
                break;
            }
            /* Heartbeat */
            case HEARTBEAT_ACK:
            case HEARTBEAT: {
                LOG.log(LogLevel.TRACE, "[HEART] "+code);
                break;
            }
            case RESUME: {
                sendIdentification();
                sendHeartBeat();
            }
            default: {
                LOG.log(LogLevel.FETAL, "[UNKNOWN] OP Code/Message : " +message);
            }
        }
    }

    /**
     * Handling event (OPCode: DISPATCH)
     * @param json the json content
     */
    private void handleEvent(JSONObject json) {
        sequence = json.getInt("s");
        String key = json.getString("t");
        JSONObject event = json.getJSONObject("d");

        EventHandler handler = eventHandler.get(key);

        if (handler == null) {
            LOG.log(LogLevel.FETAL, "[UNKNOWN] Event: " + key + json.toString(4));
        } else {

            switch (key) {
                case "READY": {
                    session_id = event.getString("session_id");
                    LOG.log(LogLevel.DEBUG, "[RECEIVED] Ready Event");
                    break;
                }
                case "RESUMED": {
                    session_id = event.getString("session_id");
                    LOG.log(LogLevel.DEBUG, "[RECEIVED] Resumed Event");
                    break;
                }
                default: {
                    break;
                }
            }

            // Handle Error Response
            if (event.has("code")) {
                handleError(new ErrorResponseException(ErrorResponse.getByKey(json.getInt("code"))));
                return;
            }

            // Only fire Non-Gateway events after the connection is ready.
            if (!(handler instanceof GatewayEventHandler)) {
                if (identity.CONNECTION.isReady()) {
                    handler.dispatchEvent(event, sequence);
                }
            } else {
                handler.dispatchEvent(event, sequence);
            }
        }
    }

    /**
     * Fire exceptions to DispatcherAdaptors
     * @param exception The exception being caught
     */
    private void handleError(Exception exception) {
        identity.getEventManager().dispatchEvent(new ExceptionEvent(identity, exception));
    }

    private void sendHeartBeat() {
        LOG.log(LogLevel.TRACE, "[HEART] Interval: "+interval);
        webSocket.setPingInterval(interval);
        heart = new Thread(() -> {
            while (identity.CONNECTION.isConnected()) {
                webSocket.sendText(
                        new JSONObject()
                            .put("op", OPCode.HEARTBEAT.key)
                            .put("d", sequence).toString());

                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    LOG.log(LogLevel.FETAL, e);
                }
            }
        });
        heart.setName("HEART");
        heart.start();
    }

    private void sendIdentification() throws IllegalArgumentException {
        JSONObject identify = new JSONObject()
            .put("op", OPCode.IDENTIFY.key)
            .put("d", new JSONObject()
                .put("token", identity.getToken())
                .put("properties", new JSONObject()
                    .put("$os", System.getProperty("os.name"))
                    .put("$browser", "J-Cord")
                    .put("$device", "J-Cord")
                    .put("$referrer", "")
                    .put("$referring_domain", "")
                )
                    .put("compress", true)
                    .put("large_threshold", 250)
                    //.put("shard", new int[]{})
            );

        webSocket.sendText(identify.toString());
        LOG.log(LogLevel.DEBUG, "[SENT] Identification");
    }

    private void sendResume() {
        identity.CONNECTION = Identity.Connection.RESUMING;
        JSONObject resume = new JSONObject()
            .put("op", OPCode.RESUME.key)
            .put("d", new JSONObject()
                .put("token", identity.getToken())
                .put("session_id", session_id)
                .put("seq", sequence)
            );

        webSocket.sendText(resume.toString());
        LOG.log(LogLevel.DEBUG, "[SENT] Resume");
    }

    private void setEventHandler() {
        /* Gateway Event */
        eventHandler.put("READY", new GatewayEventHandler(identity, this));
        eventHandler.put("RESUMED", new GatewayEventHandler(identity, this));
        eventHandler.put("PRESENCE_UPDATE", new PresenceUpdateEventHandler(identity));

        /* Guild Event */
        eventHandler.put("GUILD_CREATE", new GuildCreateEventHandler(identity));
        eventHandler.put("GUILD_UPDATE", new GuildUpdateEventHandler(identity));
        eventHandler.put("GUILD_DELETE", new GuildDeleteEventHandler(identity));
        eventHandler.put("GUILD_EMOJIS_UPDATE", new GuildEmojisUpdateEventHandler(identity));

        // Member Event
        eventHandler.put("GUILD_BAN_ADD", new GuildBanEventHandler(identity, true));
        eventHandler.put("GUILD_BAN_REMOVE", new GuildBanEventHandler(identity, false));
        eventHandler.put("GUILD_MEMBER_ADD", new GuildMemberAddEventHandler(identity));
        eventHandler.put("GUILD_MEMBER_UPDATE", new GuildMemberUpdateEventHandler(identity));
        eventHandler.put("GUILD_MEMBER_REMOVE", new GuildMemberRemoveEventHandler(identity));

        // Role Event
        eventHandler.put("GUILD_ROLE_CREATE", new GuildRoleCreateEventHandler(identity));
        eventHandler.put("GUILD_ROLE_UPDATE", new GuildRoleUpdateEventHandler(identity));
        eventHandler.put("GUILD_ROLE_DELETE", new GuildRoleDeleteEventHandler(identity));

        /* Channel Event */
        eventHandler.put("CHANNEL_CREATE", new ChannelCreateEventHandler(identity));
        eventHandler.put("CHANNEL_UPDATE", new ChannelUpdateEventHandler(identity));
        eventHandler.put("CHANNEL_DELETE", new ChannelDeleteEventHandler(identity));
        eventHandler.put("TYPING_START", new TypingStartEventHandler(identity));

        /* Message Event */
        eventHandler.put("MESSAGE_CREATE", new MessageCreateEventHandler(identity));
        eventHandler.put("MESSAGE_UPDATE", new MessageUpdateEventHandler(identity));
        eventHandler.put("MESSAGE_DELETE", new MessageDeleteEventHandler(identity));
        eventHandler.put("MESSAGE_DELETE_BULK", new MessageDeleteBulkEventHandler(identity));
        eventHandler.put("CHANNEL_PINS_UPDATE", new ChannelPinsUpdateEventHandler(identity));
        eventHandler.put("MESSAGE_REACTION_ADD", new MessageReactionEventHandler(identity, true));
        eventHandler.put("MESSAGE_REACTION_REMOVE", new MessageReactionEventHandler(identity, false));
        eventHandler.put("MESSAGE_REACTION_REMOVE_ALL", new MessageReactionRemoveAllEventHandler(identity));

        // TODO: Finish priority events
        // Priority: USER_UPDATE
        // Future: GUILD_SYNC, GUILD_MEMBERS_CHUNK, WEBHOOKS_UPDATE, VOICE_SERVER_UPDATE, VOICE_STATE_UPDATE
        // Unknown: MESSAGE_ACK

        // Clients: CALL_CREATE, CALL_UPDATE, CALL_DELETE, CHANNEL_RECIPIENT_ADD, CHANNEL_RECIPIENT_REMOVE, RELATIONSHIP_ADD, RELATIONSHIP_REMOVE
    }

}
