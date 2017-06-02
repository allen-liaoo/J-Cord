package org.alienideology.jcord.gateway;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFrame;
import org.alienideology.jcord.Identity;
import org.apache.commons.logging.impl.SimpleLog;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author AlienIdeology
 */
public class GatewayAdaptor extends WebSocketAdapter {

    private Identity identity;
    private WebSocket webSocket;
    private Thread heart;

    private boolean isConnected = false;

    public SimpleLog LOG = new SimpleLog("Gateway");

    public GatewayAdaptor(Identity identity, WebSocket webSocket) {
        this.identity = identity;
        this.webSocket = webSocket;
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
        handleOPCode(OPCode.getCode(opCode));
    }

    @Override
    public void onFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
        OPCode code = OPCode.getCode(frame.getOpcode());
        LOG.info("OP: " + frame.getOpcode() + "\t" + code);

        handleOPCode(code);
    }

    @Override
    public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
        DisconnectionCode code = DisconnectionCode.getCode(serverCloseFrame.getCloseCode());
        LOG.error("Gateway Disconnection Code: "+code+"\t"+code.message);
        isConnected = false;
    }

    private void handleOPCode(OPCode code) {
        switch (code) {
            case HELLO: {
                sendHeartBeat();
                break;
            }
            case HEARTBEAT_ACK:
            case HEARTBEAT: {
                LOG.debug("Heart: "+code);
                break;
            }
        }
    }

    private void sendHeartBeat() {
        heart = new Thread(() -> {
            while (isConnected) {
                webSocket.sendText(
                        new JSONObject()
                            .put("op", 1)
                            .put("d", 100).toString());
                LOG.info("Thump");
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
            .put("token", identity.getToken())
            .put("properties", new JSONObject()
                    .put("$os", System.getProperty("os.name"))
                    .put("$browser", "J-Cord")
                    .put("$device", "J-Cord")
                    .put("$referrer", "")
                    .put("$referring_domain", ""))
            .put("compress", true)
            .put("large_threshold", 250);
        webSocket.sendText(identify.toString());
        LOG.info("Identification Sent");
    }

}
