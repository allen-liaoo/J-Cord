package org.alienideology.jcord.gateway;

import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.Unirest;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFrame;
import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.Event;
import org.alienideology.jcord.event.GuildEvent.GuildRoleCreateEvent;
import org.apache.commons.logging.impl.SimpleLog;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Communication client for Discord GateWay
 * @author AlienIdeology
 */
public class GatewayAdaptor extends WebSocketAdapter {

    private Identity identity;
    private WebSocket webSocket;
    private Thread heart;

    private boolean isConnected = false;

    public SimpleLog LOG = new SimpleLog("Gateway");

    /* <Event Name, Event Object> */
    public HashMap<String, Event> eventHandler = new HashMap<>();

    public GatewayAdaptor(Identity identity, WebSocket webSocket) {
        this.identity = identity;
        this.webSocket = webSocket;
        setEventHandler();
    }

    @Override
    public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
        LOG.info("Connected");
        isConnected = true;
        sendIdentification();
    }

    @Override
    public void onTextMessage(WebSocket websocket, String text) throws Exception {
        JSONObject json = new JSONObject(text);
        int opCode = json.getInt("op");
        handleOPCode(OPCode.getCode(opCode), text);
    }

    @Override
    public void onFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
        OPCode code = OPCode.getCode(frame.getOpcode());
        if(!code.equals(OPCode.HEARTBEAT) && !code.equals(OPCode.HEARTBEAT_ACK))
            LOG.info("OP: " + frame.getOpcode() + "\t" + code);
        handleOPCode(code, frame.getPayloadText());
    }

    @Override
    public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
        DisconnectionCode code = DisconnectionCode.getCode(serverCloseFrame.getCloseCode());
        LOG.error("Disconnection Code: "+code+"\t"+code.message);
        isConnected = false;
    }

    private void handleOPCode(OPCode code, String message) {
        switch (code) {
            case DISPATCH: {
                handleEvent(new JSONObject(message));
            }
            case HELLO: {
                sendHeartBeat();
                break;
            }
            case HEARTBEAT_ACK:
            case HEARTBEAT: {
                //LOG.info("Heart: "+code);
                break;
            }
            default: {
                LOG.debug("Unknown OP Code");
            }
        }
    }

    private void handleEvent(JSONObject json) {
        int opCode = json.getInt("op");
        int session = json.getInt("s");
        JSONObject event = json.getJSONObject("d");
        String key = json.getString("t");

        Event e = eventHandler.get(key);
        e.setOpCode(opCode)
            .setSession(session)
            .handleEvent(json);
    }

    private void sendHeartBeat() {
        heart = new Thread(() -> {
            while (isConnected) {
                webSocket.sendText(
                        new JSONObject()
                            .put("op", 1)
                            .put("d", 100).toString());
                //LOG.info("Thump");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    LOG.debug(e);
                }
            }
        });
        heart.setName("Heart Beat");
        heart.start();
    }

    private void sendIdentification() {
        JSONObject identify = new JSONObject()
            .put("op", 2)
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
        LOG.info("Identification Sent");
    }

    private void setEventHandler() {
        eventHandler.put("GUILD_ROLE_CREATE", new GuildRoleCreateEvent(identity));
    }

}
