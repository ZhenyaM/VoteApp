package com.vote.service;

import com.vote.entity.Polling;
import com.vote.entity.PollingSchedule;
import com.vote.entity.Vote;
import com.vote.utils.Result;

import java.util.List;
import java.util.Map;

/**
 * {@author Evgeniy}
 */
public interface PollingService {

	Polling getPolling(Integer id);

	List<Polling> getPollingList(Integer startIndex, Integer count);

	void createPolling(Polling polling);

	Result startPolling(Integer id);

	Result endPolling(Integer id);

	List<Vote> getVotes(Integer id);

	Map<String, Long> getVotesStatistic(Integer id);

	void registerVote(Integer id, Vote vote);
}
