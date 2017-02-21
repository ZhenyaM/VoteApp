package com.vote.dao;

import com.vote.entity.Vote;

import java.util.List;

/**
 * {@author Evgeniy}
 */
public interface VoteRepository {

	List<Vote> getVoteByVoterAndPolling(Integer voterId, Integer pollingId);

	List<Object[]> getVoteStatistic(Integer id);

	List<Vote> getVotesOfPolling(Integer id, Integer startIndex, Integer count);

	void registerVote(Vote vote);
}
