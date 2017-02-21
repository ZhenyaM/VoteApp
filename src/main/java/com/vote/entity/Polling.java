package com.vote.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vote.utils.converters.DateTimeDeserializer;
import com.vote.utils.converters.DateTimeSerializer;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * {@author Evgeniy}
 */
@Entity
@Table(name = "polling")
@SecondaryTable(name = "polling_schedule")
public class Polling extends DomainIdObject {

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Person owner;

	@NotNull
	@Length(min = 4, max = 50)
	@Column(name = "poll_name")
	private String name;

	@Column(name = "description")
	private String description;

	@Valid
	@NotNull
	@NotEmpty
	@OneToMany(targetEntity = PollingSchedule.class, mappedBy = "polling",
			fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<PollingSchedule> variants;

	@JsonSerialize(using = DateTimeSerializer.class)
	@JsonDeserialize(using = DateTimeDeserializer.class)
	@Column(name = "start_time")
	private LocalDateTime startTime;

	@JsonSerialize(using = DateTimeSerializer.class)
	@JsonDeserialize(using = DateTimeDeserializer.class)
	@Column(name = "end_time")
	private LocalDateTime endTime;

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<PollingSchedule> getVariants() {
		return variants;
	}

	public void setVariants(List<PollingSchedule> variants) {
		this.variants = variants;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
}
