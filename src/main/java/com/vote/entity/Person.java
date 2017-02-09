package com.vote.entity;

import org.hibernate.validator.constraints.Email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Past;
import java.time.LocalDate;

/**
 * {@author Evgeniy}
 */
@Entity
@Table(name = "person")
public class Person extends DomainIdObject {

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Past
	@Column(name = "birthday")
	private LocalDate birthday;

	@Email
	@Column(name = "email")
	private String email;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Person person = (Person) o;

		return firstName.equals(person.firstName) && lastName.equals(person.lastName)
				&& birthday.equals(person.birthday) && email.equals(person.email);

	}

	@Override
	public int hashCode() {
		int result = firstName.hashCode();
		result = 31 * result + lastName.hashCode();
		result = 31 * result + birthday.hashCode();
		result = 31 * result + email.hashCode();
		return result;
	}
}
