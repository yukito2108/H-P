package com.snezana.doctorpractice.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snezana.doctorpractice.models.Role;
import com.snezana.doctorpractice.models.User;

/**
 * Service responsible for providing authentication details to Authentication
 * Manager Implements Spring's
 * {@code org.springframework.security.core.userdetails.UserDetailsService}
 * interface.
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	/**
	 * A method that takes username and returns
	 * org.springframework.security.core.userdetails.User object. We populate this
	 * object using our own UserService which gets data from database using
	 * JpaRepository
	 */
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username);
		if (user == null) {
			System.out.println("User not found");
			throw new UsernameNotFoundException("Username not found");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				user.getUserStatus().equals("ACTIVE"), true, true, true, getGrantedAuthorities(user));
	}

	/**
	 * Returns a list of authorities that are assigned to a user.	
	 * @param user the user for which the list is asked for.
	 * @return
	 */
	private List<GrantedAuthority> getGrantedAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Role userProfile : user.getRoles()) {
			System.out.println("UserProfile : " + userProfile);
			authorities.add(new SimpleGrantedAuthority("ROLE_" + userProfile.getRoleType()));
		}
		System.out.print("authorities :" + authorities);
		return authorities;
	}

}
