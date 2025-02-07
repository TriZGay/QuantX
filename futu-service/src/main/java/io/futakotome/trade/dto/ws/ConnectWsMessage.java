package io.futakotome.trade.dto.ws;

public class ConnectWsMessage implements Message {
    private boolean isConnect;

    public ConnectWsMessage() {
    }

    @Override
    public MessageType getType() {
        return MessageType.CONNECT;
    }

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }
}
