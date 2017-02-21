package com.vote.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vote.utils.converters.DateTimeDeserializer;
import com.vote.utils.converters.DateTimeSerializer;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * {@author Evgeniy}
 */
@Entity
@Table(name = "vote")
public class Vote extends DomainIdObject {

	@ManyToOne
	@JoinColumn(name = "voter_id")
	private Person voter;

	@NotNull
	@NotEmpty
	@ManyToOne
	@JoinColumn(name = "polling_id")
	private Polling polling;

	@NotNull
	@NotEmpty
	@Column(name = "poll_var")
	private PollingSchedule pollingChoose;

	@JsonSerialize(using = DateTimeSerializer.class)
	@JsonDeserialize(using = DateTimeDeserializer.class)
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
