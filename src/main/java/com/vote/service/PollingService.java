package com.vote.service;

import com.vote.entity.Person;
import com.vote.entity.Polling;
import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * {@author Evgeniy}
 */
public interface PollingService {

	Polling getPolling(Integer id);

	List<Polling> getPollingList(Integer startIndex, Integer count);

	void createPolling(Polling polling, Person person);

	void startPolling(Integer id, Person person);

	void endPolling(Integer id, Person person);

}
