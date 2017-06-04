package org.alienideology.jcord.gateway;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFrame;
import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.Event;
import org.alienideology.jcord.event.GatewayEvent.ReadyEvent;
import org.alienideology.jcord.event.GuildEvent.GuildCreateEvent;
import org.alienideology.jcord.event.GuildEvent.GuildRoleCreateEvent;
import org.apache.commons.logging.impl.SimpleLog;
import org.json.JSONObject;

import java.util.*;
import java.util.zip.Inflater;

/**
 * Communication client for Discord GateWay
 * @author AlienIdeology
 */
public class GatewayAdaptor extends WebSocketAdapter {

    public static int GATEWAY_VERSION = 5;
    public SimpleLog LOG = new SimpleLog("Gateway");

    private Identity identity;
    private WebSocket webSocket;
    private Thread heart;

    /* Used for resuming and heartbeat */
    private int sequence;
    /* Use for resuming */
    private String session_id = null;
    private boolean isConnected = false;

    /* <Event Name, Event Object> */
    public HashMap<String, Event> eventHandler = new HashMap<>();

    /**
     * The listener for Gateway messages.
     * @param identity The identity this gateway belongs to.
     * @param webSocket The WebSocket where events are fired.
     */
    public GatewayAdaptor(Identity identity, WebSocket webSocket) {
        this.identity = identity;
        this.webSocket = webSocket;
        LOG.setLevel(SimpleLog.LOG_LEVEL_ALL);
        setEventHandler();
    }

    @Override
    public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
        LOG.info("Connected");
        isConnected = true;

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
        int opCode = json.getInt("op");
        handleOPCode(OPCode.getCode(opCode), text);
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

        JSONObject json = new JSONObject(decoded.toString());
        handleEvent(json);
    }

    @Override
    public void onFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
        OPCode code = OPCode.getCode(frame.getOpcode());
        handleOPCode(code, frame.getPayloadText());
    }

    @Override
    public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
        DisconnectionCode code = DisconnectionCode.getCode(serverCloseFrame.getCloseCode());
        LOG.error(code+"\t"+code.message);
        isConnected = false;
    }

    /**
     * Handling OP Code
     * @param code OPCode to handle
     * @param message The json message
     */
    private void handleOPCode(OPCode code, String message) {
        switch (code) {
            /* Event */
            case DISPATCH: {
                handleEvent(new JSONObject(message));
                //LOG.info("Event Json: "+message);
            }
            /* Server Side HandShake */
            case HELLO: {
                sendHeartBeat(new JSONObject(message).getJSONObject("d").getLong("heartbeat_interval"));
                LOG.info("[RECEIVED] Hello");
                break;
            }
            /* Client Side HandShake */
            case IDENTIFY: {
                break;
            }
            /* Heartbeat */
            case HEARTBEAT_ACK:
            case HEARTBEAT: {
                LOG.info("Heart: "+code);
                break;
            }
            default: {
                LOG.error("Unknown OP Code");
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

        Event e = eventHandler.get(key);

        if (e == null) {
            LOG.fatal("Unknown Event: "+key);// + json.toString(4));
        } else {

            switch (key) {
                case "READY": {
                    session_id = event.getString("session_id");
                    LOG.info("[RECEIVED] Ready event!!!");
                    break;
                }
                default: {
                    break;
                }
            }

            e.setSequence(sequence).handleEvent(event);
            identity.getDispatchers().forEach(listener -> listener.onEvent(e));
        }
    }

    private void sendHeartBeat(long interval) {
        LOG.info("Interval: "+interval);
        webSocket.setPingInterval(interval);
        heart = new Thread(() -> {
            while (isConnected) {
                webSocket.sendText(
                        new JSONObject()
                            .put("op", OPCode.HEARTBEAT.key)
                            .put("d", 100).toString());

                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    LOG.error(e);
                }
            }
        });
        heart.setName("Heart Beat");
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
        LOG.info("[SENT] Identification");
    }

    private void sendResume() {
        JSONObject resume = new JSONObject()
            .put("op", OPCode.RESUME.key)
            .put("d", new JSONObject()
                .put("token", identity.getToken())
                .put("session_id", session_id)
                .put("seq", sequence)
            );

        webSocket.sendText(resume.toString());
        LOG.info("[SENT] Resume");
    }

    private void setEventHandler() {
        /* Gateway Event */
        eventHandler.put("READY", new ReadyEvent(identity, this));

        /* Guild Event */
        eventHandler.put("GUILD_CREATE", new GuildCreateEvent(identity));
        eventHandler.put("GUILD_ROLE_CREATE", new GuildRoleCreateEvent(identity));
    }

}
