package com.snezana.doctorpractice.configurations;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listener in Spring Boot app, used for HTTP session tracking.
 */
public class SessionListener implements HttpSessionListener {

	private static final Logger LOG = LoggerFactory.getLogger(SessionListener.class);

	/* Session listener will be triggered when the session is created... */
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		event.getSession().setMaxInactiveInterval(15 * 60); // for session timeout maxInt = 15 min
		LOG.info("==== Session is created ====");
	}

	/* ...and destroyed with this method. */
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		LOG.info("==== Session is destroyed ====");
	}

}
