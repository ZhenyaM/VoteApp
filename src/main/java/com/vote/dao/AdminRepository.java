package com.vote.dao;

import com.vote.entity.DomainIdObject;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * {@author Evgeniy}
 */
public interface AdminRepository {

	EntityManager getEntityManager();

	default <T extends DomainIdObject> List<T> findByQuery(String query) {
		return findByQuery(query, Collections.emptyMap());
	}

	default <T extends DomainIdObject> List<T> findByQuery(String queryString, Map<String, Object> params) {
		javax.persistence.Query query = getEntityManager().createQuery(queryString);
		params.forEach(query::setParameter);
		return query.getResultList();
	}
}
