package com.snezana.doctorpractice.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.snezana.doctorpractice.configurations.EventWSHandler;
import com.snezana.doctorpractice.models.EvtWSMessage;

/**
 * Service for WebSocket events in scheduler 
 */
@Service
public class EventWSService {	
	
		@Autowired
		private EventWSHandler eventWSHandler;

		public void sendEventWS(EvtWSMessage event) {

			try {
				eventWSHandler.sendEventWS(event);
			} catch (JsonProcessingException e) {	
				e.printStackTrace();
			} catch (IOException e) {				
				e.printStackTrace();
			}
		}
}
