package com.vote.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * {@author Evgeniy}
 */
@Repository("adminRepository")
public class AdminRepositoryImpl implements AdminRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public EntityManager getEntityManager() {
		return this.manager;
	}
}
