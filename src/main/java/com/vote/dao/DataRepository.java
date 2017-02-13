package com.vote.dao;

import com.vote.entity.Polling;
import com.vote.entity.PollingSchedule;
import com.vote.entity.Vote;

import java.time.LocalDateTime;
import java.util.List;

/**
 * {@author Evgeniy}
 */
public interface DataRepository {

	List<Polling> getPolling(Integer id);

	List<Polling> getPollingList(Integer startIndex, Integer count);

	void createPolling(Polling polling);

	void updatePolling(Polling polling);

	List<Vote> getVotesOfPolling(Polling polling);

	void registerVote(Vote vote);
}
