package com.apsi.generic;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;

public class AbstractEntityRepositoryImpl<T extends AbstractIdEntity> extends SimpleJpaRepository<T, Long> implements AbstractEntityRepository<T, Long> {

	private EntityManager em;

	public AbstractEntityRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		em = entityManager;
	}
}
