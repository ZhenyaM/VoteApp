package com.vote.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * {@author Evgeniy}
 */
@Entity
@Table(name = "vote")
public class Vote extends DomainIdObject {

	@ManyToOne
	@JoinColumn(name = "voter_id")
	private Person voter;

	@ManyToOne
	@JoinColumn(name = "polling_id")
	private Polling polling;

	@Column(name = "poll_var")
	private PollingSchedule pollingChoose;

	@Column(name = "date_time")
	private LocalDateTime date;

	public Person getVoter() {
		return voter;
	}

	public void setVoter(Person voter) {
		this.voter = voter;
	}

	public Polling getPolling() {
		return polling;
	}

	public void setPolling(Polling polling) {
		this.polling = polling;
	}

	public PollingSchedule getPollingChoose() {
		return pollingChoose;
	}

	public void setPollingChoose(PollingSchedule pollingChoose) {
		this.pollingChoose = pollingChoose;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

}
