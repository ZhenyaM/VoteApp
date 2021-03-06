package com.vote.entity;

import com.vote.utils.Roles;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * {@author Evgeniy}
 */
@Entity
@Table(name = "account")
public class Account extends DomainIdObject {

	@NotNull
	@Email
	@Length(min = 4, max = 50)
	@Column(name = "email")
	private String email;

	@NotNull
	@NotEmpty
	@Length(min = 4, max = 50)
	@Column(name = "password")
	private String password;

	@Column(name = "role")
	private Roles role;

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Roles getRole() {
		return this.role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}
}
