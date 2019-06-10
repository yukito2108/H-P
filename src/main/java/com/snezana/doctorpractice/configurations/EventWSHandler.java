package com.snezana.doctorpractice.configurations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.snezana.doctorpractice.models.EvtWSMessage;

/**
 * WebSocket session handler class for WebSocket events in scheduler. 
 */
@Component
public class EventWSHandler extends TextWebSocketHandler {

	public List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	private static final ObjectMapper JSON_MAPPER;
	private static final Logger LOG = LoggerFactory.getLogger(EventWSHandler.class);
	
	static {
		JSON_MAPPER = new ObjectMapper();
	}

	public EventWSHandler() {
	}

	/**
	 * @see AbstractWebSocketHandler#afterConnectionEstablished(WebSocketSession)
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
	}	

	/**
	 * Wraps a WebSocket message event to JSON form and sends it as WS message to all active sessions. 
	 * @param event WebSocket message event that needs to be transmitted.
	 * @throws JsonProcessingException if messaging event cannot be formatted as JSON.
	 * @throws IOException if something goes wrong when sending message, or something is wrong with a session.
	 */
	public void sendEventWS(EvtWSMessage event) throws JsonProcessingException, IOException {
		String jsonObj = JSON_MAPPER.writeValueAsString(event);
		LOG.info("jsonObj in sendEventWS() is:" + jsonObj);
		for (WebSocketSession webSocketSession : sessions) {
			webSocketSession.sendMessage(new TextMessage(jsonObj));
		}
	}

	/**
	 * @see AbstractWebSocketHandler#afterConnectionEstablished(WebSocketSession, CloseStatus)
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session);
	}
}
