package com.chat.websocketchat.v1;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Slf4j
public class ChatHandler extends TextWebSocketHandler {

    private static Map<String, WebSocketSession> webSocketSessions = new ConcurrentHashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();

        Message mess = Message.builder().type("content").sender(session.getId()).data(payload).build();

        log.info("message : " + mess);

        webSocketSessions.values().forEach(s -> {
            try {
                s.sendMessage(new TextMessage(mess.toString()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String sessionId = session.getId();
        webSocketSessions.put(sessionId, session);

        Message message = Message.builder().sender(sessionId).build();
        message.newConnect();

        webSocketSessions.values().forEach(s -> {
            try {
                s.sendMessage(new TextMessage(message.toString()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String sessionId = session.getId();
        webSocketSessions.remove(sessionId);

        Message message = Message.builder().sender(sessionId).build();
        message.closeConnect();

        webSocketSessions.values().forEach(s -> {
            try {
                s.sendMessage(new TextMessage(message.toString()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
