package com.vote.service;

import com.vote.entity.Account;
import com.vote.entity.AccountDTO;
import com.vote.entity.Person;
import org.springframework.security.core.Authentication;

/**
 * {@author Evgeniy}
 */
public interface AccountService {

	void saveAccount(AccountDTO account);

	Account getAccountById(Integer id);

	Account getAccountByEmail(String email);

	Person getPersonFromAuthentication(Authentication auth);

}
