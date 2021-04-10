package com.apsi.global;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.apsi.modules.user.domain.User;
import com.apsi.modules.user.query.UserRepository;

@Service
@AllArgsConstructor
public class Identity {
	private UserRepository userRepository;

	public long getRawId() {
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		var user = (User) authentication.getPrincipal();
		return user.getId();
	}

	public UserId getId() {
		return new UserId(getRawId());
	}
}
