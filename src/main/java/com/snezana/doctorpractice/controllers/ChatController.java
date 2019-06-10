package com.snezana.doctorpractice.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.snezana.doctorpractice.models.ChatWSMessage;
import com.snezana.doctorpractice.models.ChatWSOutputMessage;

/**
 * Controller class for Chat activities.
 */
@Controller
public class ChatController {

	/**
	 * Sends message with the date notification. <br>
	 * Message is sent to the WS channel that is stated in {@code @SendTo} notification.
	 * @param message a Web Socket message.
	 * @return
	 */
	@MessageMapping("/chatFunc/sendMessage")
	@SendTo("/channel/publicChat")
	public ChatWSOutputMessage sendMessage(@Payload ChatWSMessage message) {
		String time = new SimpleDateFormat("HH:mm:ss aaa").format(new Date());
		return new ChatWSOutputMessage(message.getType(), message.getContent(), message.getSender(), time);
	}

	/**
	 *  Adds username in web socket session.
	 * @param chatWSMessage
	 * @param headerAccessor
	 * @return
	 */
	@MessageMapping("/chatFunc/addUser")
	@SendTo("/channel/publicChat")
	public ChatWSMessage addUser(@Payload ChatWSMessage chatWSMessage, SimpMessageHeaderAccessor headerAccessor) {		
		headerAccessor.getSessionAttributes().put("username", chatWSMessage.getSender());
		return chatWSMessage;
	}

	/**
	 * Notifies that particular user is already present in chat group.
	 * @param chatWSMessage
	 * @param headerAccessor
	 * @return
	 */
	@MessageMapping("/chatFunc/alreadyHere")
	@SendTo("/channel/publicChat")
	public ChatWSMessage alreadyHere(@Payload ChatWSMessage chatWSMessage, SimpMessageHeaderAccessor headerAccessor) {
		return chatWSMessage;
	}

}
