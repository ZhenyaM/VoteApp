package com.vote.dao;

import com.vote.entity.Polling;
import com.vote.entity.PollingSchedule;
import com.vote.entity.Vote;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * {@author Evgeniy}
 */
@Repository("dataRepository")
public class PollingRepository implements DataRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public void createPolling(Polling polling) {
		this.manager.persist(polling);
	}

	@Override
	public List<Polling> getPolling(Integer id) {
		return this.manager.createQuery("select p from com.vote.entity.Polling p where p.id=:id")
				.setParameter("id", id).getResultList();
	}

	@Override
	public void createPollingSchedule(List<PollingSchedule> variants) {
		variants.forEach(this.manager::persist);
	}

	@Override
	public void updatePolling(Polling polling) {
		this.manager.merge(polling);
	}

	@Override
	public List<Vote> getVotesOfPolling(Polling polling) {
		String queryString = "select v from com.vote.entity.Vote v where v.polling=:polling";
		return this.manager
				.createQuery(queryString)
				.setParameter("polling", polling).getResultList();
	}

	@Override
	public void registerVote(Vote vote) {
		this.manager.persist(vote);
	}

}
