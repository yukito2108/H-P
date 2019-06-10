package com.snezana.doctorpractice.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;

/**
 * Spring Security Java Configuration
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("customUserDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	CustomSuccessHandler customSuccessHandler;

	/**
	 * Configuring DaoAuthenticationProvider with UserDetailsService we store
	 * the credentials in database and inject PasswordEncoder	 
	 * @return
	 */
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	/**
	 * Configures AuthenticationManagerBuilder with user credentials and allowed
	 * roles.	 
	 * @param auth
	 * @throws Exception. The caller of this service method must handle all exception cases.
	 */
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());
	}

	/**
	 * Creates PasswordEncoder that uses BCrypt strong hashing function
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Configures HttpSecurity which allows configuring for request-specific web based security.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/home/**").access("hasRole('USER')")
				.antMatchers("/doctor/**").access("hasRole('DOCTOR')").antMatchers("/admin/**")
				.access("hasRole('ADMIN')").antMatchers("/resources/**").permitAll().antMatchers("/wschat/**")
				.access("hasAnyRole('ADMIN','DOCTOR','USER')").and().formLogin().loginPage("/login")
				.failureUrl("/login?error").successHandler(customSuccessHandler).usernameParameter("username")
				.passwordParameter("password").and().csrf().and().exceptionHandling().accessDeniedPage("/accessDenied")
				.and().sessionManagement().maximumSessions(1).expiredUrl("/multiConcLoginsExp");
		http.addFilterAfter(new CustomFilter(), SwitchUserFilter.class);
		// csrf
	}

}
