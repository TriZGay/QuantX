package io.futakotome.trade.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.futakotome.trade.listener.Message;
import io.futakotome.trade.listener.ReactiveWebSocketListener;
import io.futakotome.trade.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractWebSocketServerHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractWebSocketServerHandler.class);
    private final ObjectMapper objectMapper;
    private final ReactiveWebSocketListener listener;
    private final MessageService messageService;

    protected AbstractWebSocketServerHandler(ObjectMapper objectMapper, ReactiveWebSocketListener listener, MessageService messageService) {
        this.objectMapper = objectMapper;
        this.listener = listener;
        this.messageService = messageService;
    }

    protected void onMessage(String payload, String sessionId) {
        try {
            Message message = objectMapper.readValue(payload, Message.class);
            listener.onMessage(message, sessionId);
        } catch (JsonProcessingException e) {
            LOGGER.error("处理消息出错.", e);
        }
    }

    protected MessageService getMessageService() {
        return messageService;
    }
}
