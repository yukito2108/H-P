package com.snezana.doctorpractice.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Support for mapping the WebSocket handler to a specific URL (used in
 * scheduler)
 */
@Configuration
@EnableWebSocket
public class EventWSConfig implements WebSocketConfigurer {
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(eventWSHandler(), "/wsevents").withSockJS();
	}

	@Bean
	public WebSocketHandler eventWSHandler() {
		return new EventWSHandler();
	}
}
