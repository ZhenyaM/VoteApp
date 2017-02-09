package com.vote.entity;

import javax.persistence.*;

/**
 * {@author Evgeniy}
 */
@Entity
@Table(name = "polling_schedule")
public class PollingSchedule extends DomainIdObject {

	@JoinColumn(name = "polling_id")
	private Polling polling;

	@Column(name = "poll_var")
	private String pollingVariant;

	public Polling getPolling() {
		return polling;
	}

	public void setPolling(Polling polling) {
		this.polling = polling;
	}

	public String getPollingVariant() {
		return pollingVariant;
	}

	public void setPollingVariant(String pollingVariant) {
		this.pollingVariant = pollingVariant;
	}
}
