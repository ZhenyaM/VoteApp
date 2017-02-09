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

	void createPolling(Polling polling);

	List<Polling> getPolling(Integer id);

	void createPollingSchedule(List<PollingSchedule> variants);

	void updatePolling(Polling polling);

	List<Vote> getVotesOfPolling(Polling polling);

	void registerVote(Vote vote);
}
