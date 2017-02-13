package com.vote.entity;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * {@author Evgeniy}
 */
@Entity
//@FilterDef(name = "idFilter")
@Table(name = "polling")
@SecondaryTable(name = "polling_schedule")
public class Polling extends DomainIdObject {

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Person owner;

	@Column(name = "poll_name")
	private String name;

	@Column(name = "description")
	private String description;

	@OneToMany(targetEntity = PollingSchedule.class, mappedBy = "polling", fetch = FetchType.EAGER)
	private List<PollingSchedule> variants;

	@Column(name = "start_time")
	private LocalDateTime startTime;

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
