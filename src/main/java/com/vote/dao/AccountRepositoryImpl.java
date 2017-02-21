package com.vote.dao;

import com.vote.entity.Account;
import com.vote.entity.Person;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * {@author Evgeniy}
 */
@Repository("accountRepository")
public class AccountRepositoryImpl implements AccountRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public void saveAccount(Account account) {
		this.manager.persist(account);
	}

	@Override
	public Account getAccountById(Integer id) {
		return (Account) this.manager
				.createQuery("select acc from com.vote.entity.Account acc where acc.id=:id")
				.setParameter("id", id).getSingleResult();
	}

	@Override
	public List<Account> getAccountByEmail(String email) {
		return this.manager
				.createQuery("select acc from com.vote.entity.Account acc where acc.email=:email")
				.setParameter("email", email).getResultList();
	}

	@Override
	public void savePerson(Person person) {
		this.manager.persist(person);
	}

	@Override
	public Person getPersonById(Integer id) {
		return (Person) this.manager
				.createQuery("select acc from com.vote.entity.Person acc where acc.id=:id")
				.setParameter("id", id).getSingleResult();
	}

	@Override
	public List<Person> getPersonByEmail(String email) {
		return this.manager
				.createQuery("select acc from com.vote.entity.Person acc where acc.email=:email")
				.setParameter("email", email).getResultList();
	}
}
