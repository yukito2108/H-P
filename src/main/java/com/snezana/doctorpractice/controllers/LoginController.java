package com.snezana.doctorpractice.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.snezana.doctorpractice.models.User;
import com.snezana.doctorpractice.services.UserService;
import com.snezana.doctorpractice.utils.TimeOut;
import com.snezana.doctorpractice.utils.SomeUtils;

/**
 * Controller class for the Login activities.
 */
@Controller
public class LoginController {

	// private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	UserService userService;

	public static final String ATTR_USERNAME = "username";

	/**
	 * Welcome page.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcomePage(ModelMap model) {
		return "welcome";
	}

	/**
	 * Patient home page.
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String homePage(ModelMap model) {
		User user = userService.findByUsername(getPrincipal());
		model.addAttribute("user", user);
		model.addAttribute("birthDate", SomeUtils.dateView(user.getPatient().getBirthDate()));
		TimeOut to = TimeOut.getInstance();
		to.paramPage = TimeOut.INAPPL;
		return "home";
	}

	/**
	 * Doctor home page.
	 */
	@RequestMapping(value = "/doctor", method = RequestMethod.GET)
	public String doctorPage(ModelMap model) {
		User user = userService.findByUsername(getPrincipal());
		model.addAttribute("user", user);
		model.addAttribute("birthDate", SomeUtils.dateView(user.getDoctor().getBirthDate()));
		TimeOut to = TimeOut.getInstance();
		to.paramPage = TimeOut.INAPPL;	
		return "doctor";
	}

	/**
	 * Admin home page.
	 */
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPage(ModelMap model) {
		model.addAttribute("admin", userService.findByUsername(getPrincipal()));
		TimeOut to = TimeOut.getInstance();
		to.paramPage = TimeOut.INAPPL;		
		return "admin";
	}

	/**
	 * 'Access Denied' page. Shows every time when user attempts to access a page
	 * that (s)he is not authorized for.
	 */
	@RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		User user = userService.findByUsername(getPrincipal());
		model.addAttribute("user", user);
		return "accessDenied";
	}

	/**
	 * Login page.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(ModelMap model) {
		model.addAttribute("pageTitle", "Login Page");
		return "login";
	}

	/**
	 * Logout page - successfully logout
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		TimeOut to = TimeOut.getInstance();
		to.paramPage = TimeOut.LOGOUT;		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}

	/**
	 * Expired-url, every time when attempt multiple logins
	 */
	@RequestMapping(value = "/multiConcLoginsExp", method = RequestMethod.GET)
	public String alreadyLogin() {
		return "multiConcLoginsExp";
	}

	/**
	 * Get User information in controller from Spring Security
	 */
	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

}
