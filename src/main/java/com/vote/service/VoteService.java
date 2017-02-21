package com.vote.service;

import com.vote.entity.Person;
import com.vote.entity.Vote;

import java.util.List;
import java.util.Map;

/**
 * {@author Evgeniy}
 */
public interface VoteService {

	void registerVote(Vote vote, Person person);

	Map<String,Long> getVotesStatistic(Integer id);

	List<Vote> getVotes(Integer id, Integer startIndex, Integer count);
}
