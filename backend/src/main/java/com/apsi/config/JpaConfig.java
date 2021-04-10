package com.apsi.config;

import com.apsi.generic.AbstractEntityRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EnableJpaRepositories(basePackages = {"com.apsi.*"}, repositoryBaseClass = AbstractEntityRepositoryImpl.class)
class JpaConfig {}
