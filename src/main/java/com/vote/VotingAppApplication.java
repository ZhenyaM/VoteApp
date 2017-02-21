package com.vote;

import com.vote.config.ApplicationBeans;
import com.vote.config.JpaConfig;
import com.vote.config.WebSecurityConfig;
import com.vote.utils.ScriptRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
public class VotingAppApplication {

	public static void main(String[] args) {
		Class<?>[] context = {JpaConfig.class, VotingAppApplication.class,
				ApplicationBeans.class, WebSecurityConfig.class};
		ConfigurableApplicationContext run = SpringApplication.run(context, args);
		// start initializing of database if arguments passed
		if (args.length == 3) {
			LocalContainerEntityManagerFactoryBean bean =
					run.getBean(LocalContainerEntityManagerFactoryBean.class);
			initializeDatabase(bean, args);
		}
	}

	private static void initializeDatabase(LocalContainerEntityManagerFactoryBean bean, String[] args) {
		System.out.println("Start database initializing.......");
		try {
			Connection connection = bean.getDataSource().getConnection(args[0], args[1]);
			ScriptRunner runner = new ScriptRunner(connection, false, true);
			runner.setErrorLogWriter(new PrintWriter(System.err));
			runner.setLogWriter(new PrintWriter(System.out));
			InputStream resource = VotingAppApplication.class.getResourceAsStream(args[3]);
			runner.runScript(new BufferedReader(new InputStreamReader(resource)));
		} catch (SQLException e) {
			System.out.println("Problems with start initialize of database");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Problems with sql script obtain");
			e.printStackTrace();
		}
	}
}
