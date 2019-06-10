package com.snezana.doctorpractice.configurations;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import com.snezana.doctorpractice.utils.TimeOut;

/**
 * Servlet filter to intercept session timeout events.
 */
public class CustomFilter extends GenericFilterBean {

	// private static final Logger LOG = LoggerFactory.getLogger(CustomFilter.class);	

	/* custom filter for session timeout detection */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		TimeOut l = TimeOut.getInstance();
		// LOG.info("l.paramPage = " + l.paramPage);

		try {
			if (req.getRequestURI().equals(req.getContextPath() + "/login") && l.paramPage == TimeOut.INAPPL) {
				l.paramPage = TimeOut.LOGOUT;
				resp.sendRedirect(req.getContextPath() + "/login?timeout");
			}
			chain.doFilter(request, response);
		} catch (Exception e) {
			// Log Exception
			e.printStackTrace();
		}
	}

}
