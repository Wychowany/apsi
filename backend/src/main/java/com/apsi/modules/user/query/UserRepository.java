package com.apsi.modules.user.query;

import com.apsi.generic.AbstractEntityRepository;
import com.apsi.modules.user.domain.User;

public interface UserRepository extends AbstractEntityRepository<User, Long> {
	User findByEmailIgnoreCase(String email);
}
