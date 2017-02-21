package com.vote.dao;

import com.vote.entity.Account;
import com.vote.entity.Person;

import java.util.List;

/**
 * {@author Evgeniy}
 */
public interface AccountRepository {

	void saveAccount(Account account);

	Account getAccountById(Integer id);

	List<Account> getAccountByEmail(String email);

	void savePerson(Person person);

	Person getPersonById(Integer id);

	List<Person> getPersonByEmail(String email);
}
