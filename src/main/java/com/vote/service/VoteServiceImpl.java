package com.vote.service;

import com.vote.dao.PollingRepository;
import com.vote.dao.VoteRepository;
import com.vote.entity.Person;
import com.vote.entity.Polling;
import com.vote.entity.PollingSchedule;
import com.vote.entity.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * {@author Evgeniy}
 */
@Service("voteService")
public class VoteServiceImpl implements VoteService {

	@Autowired
	@Qualifier("voteRepository")
	private VoteRepository voteDAO;

	@Autowired
	@Qualifier("pollingRepository")
	private PollingRepository pollingDAO;

	@Transactional
	@Override
	public void registerVote(Vote vote, Person person) {
		Polling polling = vote.getPolling();
		Integer pollingId = polling.getId();
		Integer voterId = person.getId();
		List<Vote> exist = this.voteDAO.getVoteByVoterAndPolling(voterId, pollingId);
		if (exist.isEmpty()) {
			List<PollingSchedule> variants = polling.getVariants();
			vote.setVoter(person);
			variants.forEach((v) -> v.setPolling(polling));
			Optional<PollingSchedule> first = variants.stream()
					.filter((v) -> v.getPollingVariant().equals(vote.getPollingChoose().getPollingVariant()))
					.findFirst();
			vote.setPollingChoose(first.get());
			vote.setDate(LocalDateTime.now());
			this.voteDAO.registerVote(vote);
		} else {
			throw new IllegalStateException("Current person already vote for this polling");
		}
	}

	@Transactional
	@Override
	public Map<String, Long> getVotesStatistic(Integer id) {
		Map<String, Long> collect = this.voteDAO.getVoteStatistic(id)
				.stream().collect(Collectors.toMap(
						val -> val[0].toString(), val -> ((BigInteger) val[1]).longValue()));
		Polling polling = this.pollingDAO.getPolling(id).get(0);
		if (polling.getVariants().size() == collect.size()) {
			return collect;
		}
		polling.getVariants().forEach((v) -> {
			if (!collect.containsKey(v.getPollingVariant())) {
				collect.put(v.getPollingVariant(), 0L);
			}
		});
		return collect;
	}

	@Transactional
	@Override
	public List<Vote> getVotes(Integer id, Integer startIndex, Integer count) {
		List<Polling> pollingList = this.pollingDAO.getPolling(id);
		if (pollingList.isEmpty()) {
			return Collections.emptyList();
		}
		Polling polling = pollingList.get(0);
		return polling == null ? Collections.emptyList() :
				this.voteDAO.getVotesOfPolling(polling.getId(), startIndex, count);
	}
}
