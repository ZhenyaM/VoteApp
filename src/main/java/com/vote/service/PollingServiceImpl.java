package com.vote.service;

import com.vote.dao.DataRepository;
import com.vote.entity.Polling;
import com.vote.entity.PollingSchedule;
import com.vote.entity.Vote;
import com.vote.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * {@author Evgeniy}
 */
@Service("pollingService")
public class PollingServiceImpl implements PollingService {

	@Autowired
	@Qualifier("pollingRepository")
	private DataRepository dao;

	@Transactional
	@Override
	public void createPolling(Polling polling, List<PollingSchedule> schedule) {
		this.dao.createPolling(polling);
		this.dao.createPollingSchedule(schedule);
	}

	@Transactional
	@Override
	public Polling getPolling(Integer id) {
		List<Polling> polling = this.dao.getPolling(id);
		if (polling.size() == 1) {
			return polling.get(0);
		} else {
			//TODO:handle this case
			return null;
		}
	}

	@Transactional
	@Override
	public Result startPolling(Integer id) {
		return this.getResult(id, Polling::setStartTime);
	}

	@Transactional
	@Override
	public Result endPolling(Integer id) {
		return this.getResult(id, Polling::setEndTime);
	}

	@Transactional
	@Override
	public List<Vote> getVotes(Integer id) {
		List<Polling> pollingList = this.dao.getPolling(id);
		if (pollingList.isEmpty()) {
			return Collections.emptyList();
		}
		Polling polling = pollingList.get(0);
		return polling != null ? this.dao.getVotesOfPolling(polling) : Collections.emptyList();
	}

	@Transactional
	@Override
	public Map<String, Long> getVotesStatistic(Integer id) {
		return this.getVotes(id).stream().collect(groupingBy(
				(Vote e) -> e.getPollingChoose().getPollingVariant(),
				Collectors.counting()));
	}

	@Transactional
	@Override
	public void registerVote(Integer id, Vote vote) {
		//TODO: check if vote already present
		boolean isVotePresent = this.getVotes(id).stream().anyMatch((v) ->
				v.getVoter().equals(vote.getVoter()) && v.getPolling().equals(vote.getPolling()));
		if (!isVotePresent) {
			this.dao.registerVote(vote);
		} else {
			//TODO: throw exception for this case
		}
	}

	private Result getResult(Integer id, BiConsumer<Polling, LocalDateTime> time) {
		List<Polling> pollingList = this.dao.getPolling(id);
		if (pollingList.isEmpty() ) {
			return Result.NOT_FOUND;
		}
		Polling polling = pollingList.get(0);
		if (polling.getStartTime() != null) {
			return Result.FAILED;
		} else {
			time.andThen((p, t) -> this.dao.updatePolling(polling)).accept(polling, LocalDateTime.now());
			return Result.SUCCESS;
		}
	}

}
