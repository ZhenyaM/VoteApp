package com.vote.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * {@author Evgeniy}
 */
@Entity
@Table(name = "account")
public class Account extends DomainIdObject implements Account {

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "role")
	private String role;

	@Override
	public String getEmail() {
		return null;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getRole() {
		return null;
	}
}
