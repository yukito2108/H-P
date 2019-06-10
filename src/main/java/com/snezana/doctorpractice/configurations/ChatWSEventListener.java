package com.snezana.doctorpractice.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.messaging.simp.broker.SimpleBrokerMessageHandler;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.snezana.doctorpractice.models.ChatWSMessage;

/**
 * Listeners for socket connect and disconnect events (used for chatting).
 */
@Component
public class ChatWSEventListener {

	private static final Logger logger = LoggerFactory.getLogger(ChatWSEventListener.class);

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	@EventListener
	public void handleWebSocketConnectListener(SessionConnectedEvent event) {
		logger.info("Received a new web socket connection");
	}

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

		String username = (String) headerAccessor.getSessionAttributes().get("username");
		if (username != null) {
			logger.info("User Disconnected : " + username);

			ChatWSMessage chatWSMessage = new ChatWSMessage();
			chatWSMessage.setType(ChatWSMessage.MessageType.LEAVE);
			chatWSMessage.setSender(username);
			messagingTemplate.convertAndSend("/channel/publicChat", chatWSMessage);
		}
	}
}
