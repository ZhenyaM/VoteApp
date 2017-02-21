package com.vote.entity;

import com.vote.utils.Roles;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * {@author Evgeniy}
 */
public class AccountDTO {

	@Email
	@NotEmpty
	@Length(min = 4, max = 50)
	private String email;

	@NotEmpty
	@Length(min = 4, max = 50)
	private String password;

	private Roles role;

	@NotEmpty
	@Length(min = 4, max = 50)
	private String confirmPassword;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public boolean isPasswordConfirmed() {
		return this.password != null &&
				this.password.equals(this.confirmPassword);
	}

	public Account create() {
		Account account = new Account();
		account.setEmail(this.email);
		account.setPassword(this.password);
		account.setRole(this.role);
		return account;
	}
}
