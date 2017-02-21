package com.vote.service;

import com.vote.dao.PollingRepository;
import com.vote.entity.Person;
import com.vote.entity.Polling;
import com.vote.utils.exeception.PollingNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * {@author Evgeniy}
 */
@Service("pollingService")
public class PollingServiceImpl implements PollingService {

	@Autowired
	@Qualifier("pollingRepository")
	private PollingRepository pollingDAO;

	@Transactional
	@Override
	public void createPolling(Polling polling, Person person) {
		polling.getVariants().forEach((v) -> v.setPolling(polling));
		polling.setOwner(person);
		this.pollingDAO.createPolling(polling);
	}

	@Transactional
	@Override
	public Polling getPolling(Integer id) {
		List<Polling> polling = this.pollingDAO.getPolling(id);
		if (polling.size() == 1) {
			return polling.get(0);
		} else {
			throw new PollingNotFoundException("Polling with current id not present: " + id);
		}
	}

	@Transactional
	@Override
	public List<Polling> getPollingList(Integer startIndex, Integer count) {
		return this.pollingDAO.getPollingList(startIndex, count);
	}

	@Transactional
	@Override
	public void startPolling(Integer id, Person person) {
		Polling polling = this.getPolling(id);
		if (person.equals(polling.getOwner())) {
			if (polling.getStartTime() == null) {
				polling.setStartTime(LocalDateTime.now());
				this.pollingDAO.updatePolling(polling);
			} else {
				throw new IllegalStateException("Polling already start");
			}
		} else {
			throw new SessionAuthenticationException("Only owner may start polling");
		}
	}

	@Transactional
	@Override
	public void endPolling(Integer id, Person person) {
		Polling polling = this.getPolling(id);
		if (person.equals(polling.getOwner())) {
			if (polling.getStartTime() == null) {
				throw new IllegalStateException("Polling not started yet");
			}
			if (polling.getEndTime() == null) {
				polling.setEndTime(LocalDateTime.now());
				this.pollingDAO.updatePolling(polling);
			} else {
				throw new IllegalStateException("Polling already end");
			}
		} else {
			throw new SessionAuthenticationException("Only owner may finish polling");
		}
	}

}
