package com.vote.dao;

import com.vote.entity.Vote;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * {@author Evgeniy}
 */
@Repository("voteRepository")
public class VoteRepositoryImpl implements VoteRepository {

	private static final String SQL_BY_VARIANT =
			"SELECT * FROM vote v WHERE v.voter_id=:voter AND v.polling_id=:polling";

	private static final String SQL_STATISTIC =
			"SELECT p.poll_var AS var, COUNT(*) AS `count` FROM vote v, polling_schedule p " +
					"WHERE v.polling_id=:id AND p.id = v.poll_var_id GROUP BY poll_var_id";

	private static final String SQL_GET_VOTES = "SELECT * FROM vote v WHERE v.polling_id=:id";

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Vote> getVoteByVoterAndPolling(Integer voterId, Integer pollingId) {
		return this.manager.createNativeQuery(SQL_BY_VARIANT)
				.setParameter("voter", voterId)
				.setParameter("polling", pollingId)
				.getResultList();
	}

	@Override
	public List<Object[]> getVoteStatistic(Integer id) {
		return this.manager.createNativeQuery(SQL_STATISTIC).setParameter("id", id).getResultList();
	}

	@Override
	public List<Vote> getVotesOfPolling(Integer id, Integer startIndex, Integer count) {
		return this.manager.createNativeQuery(SQL_GET_VOTES)
				.setFirstResult(startIndex)
				.setMaxResults(count)
				.getResultList();
	}

	@Override
	public void registerVote(Vote vote) {
		this.manager.persist(vote);
	}
}
