package com.vote.dao;

import com.vote.entity.Polling;
import com.vote.entity.Vote;

import java.util.List;

/**
 * {@author Evgeniy}
 */
public interface PollingRepository {

	List<Polling> getPolling(Integer id);

	List<Polling> getPollingList(Integer startIndex, Integer count);

	void createPolling(Polling polling);

	void updatePolling(Polling polling);
}
