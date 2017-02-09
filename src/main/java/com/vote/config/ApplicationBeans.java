package com.vote.config;

import com.vote.dao.DataRepository;
import com.vote.dao.PollingRepository;
import com.vote.service.PollingService;
import com.vote.service.PollingServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * {@author Evgeniy}
 */
public class ApplicationBeans {

	@Bean(name = "pollingRepository")
	public DataRepository getPollingRepository() {
		return new PollingRepository();
	}

	@Bean(name = "pollingService")
	public PollingService getPollingService() {
		return new PollingServiceImpl();
	}

	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		return new InternalResourceViewResolver("/templates/", ".html");
	}
}
