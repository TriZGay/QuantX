package io.futakotome.rtck.message;

public class RealTimeKLMessage implements Message {
//    private KLMessageContent content;

    @Override
    public MessageType getType() {
        return MessageType.RT_KL;
    }

//    public KLMessageContent getContent() {
//        return content;
//    }
//
//    public void setContent(KLMessageContent content) {
//        this.content = content;
//    }
}
