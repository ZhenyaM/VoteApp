package com.vote.dao;

import com.vote.entity.Polling;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * {@author Evgeniy}
 */
@Repository("pollingRepository")
public class PollingRepositoryImpl implements PollingRepository {

	private static final String SQL_BY_ID = "select p from com.vote.entity.Polling p where p.id=:id";
	private static final String SQL_PART = "select p from com.vote.entity.Polling p where p.id>=:id";

	@PersistenceContext
	private EntityManager manager;

	@Override
	public void createPolling(Polling polling) {
		this.manager.persist(polling);
	}

	@Override
	public List<Polling> getPolling(Integer id) {
		return this.manager.createQuery(SQL_BY_ID).setParameter("id", id).getResultList();
	}

	@Override
	public List<Polling> getPollingList(Integer startIndex, Integer count) {
		return this.manager.createQuery(SQL_PART)
				.setParameter("id", startIndex - 1)
				.setMaxResults(count)
				.getResultList();
	}

	@Override
	public void updatePolling(Polling polling) {
		this.manager.merge(polling);
	}

}
