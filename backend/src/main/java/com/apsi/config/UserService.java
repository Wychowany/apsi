package com.apsi.config;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.apsi.modules.user.domain.User;
import com.apsi.modules.user.query.UserRepository;

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
		}
		return user;
	}
}
