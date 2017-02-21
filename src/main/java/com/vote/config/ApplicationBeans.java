package com.vote.config;

import com.vote.dao.*;
import com.vote.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * {@author Evgeniy}
 */
public class ApplicationBeans {

	@Bean(name = "pollingRepository")
	public PollingRepository getPollingRepository() {
		return new PollingRepositoryImpl();
	}

	@Bean(name = "pollingService")
	public PollingService getPollingService() {
		return new PollingServiceImpl();
	}

	@Bean(name = "accountRepository")
	public AccountRepository getAccountRepository() {
		return new AccountRepositoryImpl();
	}

	@Bean(name = "accountService")
	public AccountService getAccountService() {
		return new AccountServiceImpl();
	}

	@Bean(name = "voteRepository")
	public VoteRepository getVoteRepository() {
		return new VoteRepositoryImpl();
	}

	@Bean(name = "voteService")
	public VoteService getVoteService() {
		return new VoteServiceImpl();
	}

	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		return new InternalResourceViewResolver("/templates/", ".html");
	}
}
