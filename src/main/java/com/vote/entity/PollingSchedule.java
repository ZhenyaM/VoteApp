package com.vote.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * {@author Evgeniy}
 */
@Entity
@Table(name = "polling_schedule")
public class PollingSchedule extends DomainIdObject {

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "polling_id")
	private Polling polling;

	@NotNull
	@NotEmpty
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
