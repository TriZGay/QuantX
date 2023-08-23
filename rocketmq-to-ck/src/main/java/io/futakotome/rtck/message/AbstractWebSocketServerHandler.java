package io.futakotome.rtck.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWebSocketServerHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractWebSocketServerHandler.class);
    public static final String NOTIFY_TAG = "notify";
//    private final ObjectMapper objectMapper;
//    private final ReactiveWebSocketListener listener;
//    private final MessageService messageService;
//
//    protected AbstractWebSocketServerHandler(ObjectMapper objectMapper, ReactiveWebSocketListener listener, MessageService messageService) {
//        this.objectMapper = objectMapper;
//        this.listener = listener;
//        this.messageService = messageService;
//    }

//    protected void onMessage(String payload, String session) {
//        try {
//            Message message = objectMapper.readValue(payload, Message.class);
//            listener.onMessage(message, session);
//        } catch (JsonProcessingException e) {
//            LOGGER.error("处理消息出错.", e);
//        }
//    }

    //用于获取url参数
    protected Map<String, String> getQueryMap(String queryStr) {
        LOGGER.info("Query String :" + queryStr);
        Map<String, String> queryMap = new HashMap<>();
        if (!StringUtils.isEmpty(queryStr)) {
            String[] queryParam = queryStr.split("&");
            Arrays.stream(queryParam).forEach(s -> {
                String[] kv = s.split("=", 2);
                String value = kv.length == 2 ? kv[1] : "";
                queryMap.put(kv[0], value);
            });
        }
        return queryMap;
    }

//    protected MessageService getMessageService() {
//        return messageService;
//    }
}
