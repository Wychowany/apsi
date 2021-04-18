package com.apsi.config;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.apsi.modules.user.domain.User;
import com.apsi.modules.user.query.UserRepository;

import java.util.HashSet;

@Service
@AllArgsConstructor
class UserService implements UserDetailsService {

	final private UserRepository userRepository;

	final private static Logger logger = LogManager.getLogger(UserService.class);

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		User user = userRepository.findByEmailIgnoreCase(s);
		if (user == null) {
			logger.info("User: " + s + " not found!");
		} else {
			initializeAuthorities(user);
		}
		return user;
	}

	private void initializeAuthorities(User user) {
		var authorities = new HashSet<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getSystemRole().toString()));
		user.setAuthorities(authorities);
	}
}
