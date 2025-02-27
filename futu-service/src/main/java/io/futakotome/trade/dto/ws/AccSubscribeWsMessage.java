package io.futakotome.trade.dto.ws;

import java.util.List;

public class AccSubscribeWsMessage implements Message {
    private List<AccSubscribeItem> accSubscribeItems;

    public List<AccSubscribeItem> getAccSubscribeItems() {
        return accSubscribeItems;
    }

    public void setAccSubscribeItems(List<AccSubscribeItem> accSubscribeItems) {
        this.accSubscribeItems = accSubscribeItems;
    }

    @Override
    public MessageType getType() {
        return MessageType.ACC_SUBSCRIBE;
    }

    public static class AccSubscribeItem {
        private String accId;
        private String cardNum;
        private String uniCardNum;

        public String getAccId() {
            return accId;
        }

        public void setAccId(String accId) {
            this.accId = accId;
        }

        public String getCardNum() {
            return cardNum;
        }

        public void setCardNum(String cardNum) {
            this.cardNum = cardNum;
        }

        public String getUniCardNum() {
            return uniCardNum;
        }

        public void setUniCardNum(String uniCardNum) {
            this.uniCardNum = uniCardNum;
        }
    }
}
