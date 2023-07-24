package io.futakotome.rtck.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ReactiveWebSocketListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveWebSocketListener.class);

    //todo 目前只server -> client ,这里暂时不太用得上,姑且只是打印一些調試信息
    public void onMessage(Message message, String session) {
        LOGGER.info("message: {} , session :{}", message.getType(), session);
        MessageType type = message.getType();
        switch (type) {
            case JOIN_IN: {
            }
        }
    }
}
