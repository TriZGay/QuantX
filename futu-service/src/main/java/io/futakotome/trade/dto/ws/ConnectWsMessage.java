package io.futakotome.trade.dto.ws;

public class ConnectWsMessage implements Message {
    private boolean connected;

    public ConnectWsMessage() {
    }

    @Override
    public MessageType getType() {
        return MessageType.CONNECT;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
