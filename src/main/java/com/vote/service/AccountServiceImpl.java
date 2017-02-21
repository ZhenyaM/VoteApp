package com.vote.service;

import com.vote.dao.AccountRepository;
import com.vote.entity.Account;
import com.vote.entity.AccountDTO;
import com.vote.entity.Person;
import com.vote.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;
/**
 * {@author Evgeniy}
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

	@Autowired
	@Qualifier("accountRepository")
	private AccountRepository dao;

	@Transactional
	@Override
	public void saveAccount(AccountDTO account) {
		if (!account.isPasswordConfirmed()) {
			throw new BadCredentialsException("Passed password and confirm password not the same");
		}
		if (!isEmpty(account.getEmail()) && !isEmpty(account.getPassword())) {
			List<Account> accounts = this.dao.getAccountByEmail(account.getEmail());
			if (accounts.size() == 0) {
				account.setRole(Roles.USER);
				this.dao.saveAccount(account.create());
				Person person = new Person();
				person.setEmail(account.getEmail());
				this.dao.savePerson(person);
			} else {
				throw new BadCredentialsException("User with the same email exist");
			}
		} else {
			throw new BadCredentialsException("Email and password must present (not empty strings)");
		}
	}

	@Transactional
	@Override
	public Account getAccountById(Integer id) {
		return this.dao.getAccountById(id);
	}

	@Transactional
	@Override
	public Account getAccountByEmail(String email) {
		List<Account> accounts = this.dao.getAccountByEmail(email);
		if (accounts.size() == 1) {
			return accounts.get(0);
		} else {
			throw new UsernameNotFoundException("User with passed email is not exist: " + email);
		}
	}

	@Transactional
	@Override
	public Person getPersonFromAuthentication(Authentication auth) {
		String email = ((User) auth.getPrincipal()).getUsername();
		List<Person> persons = this.dao.getPersonByEmail(email);
		if (persons.size() == 1) {
			return persons.get(0);
		} else {
			throw new BadCredentialsException("Can not find corresponding account for current user");
		}
	}

}
