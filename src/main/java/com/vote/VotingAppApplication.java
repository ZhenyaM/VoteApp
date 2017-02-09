package com.vote;

import com.vote.config.ApplicationBeans;
import com.vote.config.JpaConfig;
import com.vote.controller.PollingController;
import com.vote.dao.AdminRepository;
import com.vote.dao.DataRepository;
import com.vote.dao.PollingRepository;
import com.vote.entity.*;
import com.vote.service.PollingService;
import com.vote.service.PollingServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.persistence.Query;
import java.util.List;

@SpringBootApplication
public class VotingAppApplication {

	public static void main(String[] args) {
		Class<?>[] context = {JpaConfig.class, VotingAppApplication.class, ApplicationBeans.class};
		ConfigurableApplicationContext run = SpringApplication.run(context, args);
		AdminRepository repository = (AdminRepository) run.getBean("adminRepository");
		method(repository);
		System.out.println();
	}

	private static void method(AdminRepository repository) {
		Query query1 = repository.getEntityManager().createNativeQuery("SELECT * FROM person p WHERE p.id=1", Person.class);
		Query query2 = repository.getEntityManager().createNativeQuery("SELECT * FROM polling_schedule p WHERE p.id=1", PollingSchedule.class);
		Query query3 = repository.getEntityManager().createNativeQuery("SELECT * FROM vote p WHERE p.id=1", Vote.class);
		Query query4 = repository.getEntityManager().createNativeQuery("SELECT * FROM polling p WHERE p.id=1", Polling.class);
		List list1 = query1.getResultList();
		List list2 = query2.getResultList();
		List list3 = query3.getResultList();
		List list4 = query4.getResultList();
		List<PollingSchedule> variants = ((Polling) list4.get(0)).getVariants();
		variants.forEach(System.out::println);
		System.out.println();
	}


}
