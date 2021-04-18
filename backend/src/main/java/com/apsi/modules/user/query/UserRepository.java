package com.apsi.modules.user.query;

import com.apsi.generic.AbstractEntityRepository;
import com.apsi.modules.user.domain.SystemRole;
import com.apsi.modules.user.domain.User;

import java.util.List;

public interface UserRepository extends AbstractEntityRepository<User, Long> {

	List<User> findAllByDeletedIsFalse();

	User findByEmailIgnoreCase(String email);

    List<User> findAllByDeletedIsFalseAndSystemRoleIsIn(List<SystemRole> roles);
}
