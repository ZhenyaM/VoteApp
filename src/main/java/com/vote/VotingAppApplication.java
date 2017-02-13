package com.vote;

import com.vote.config.ApplicationBeans;
import com.vote.config.JpaConfig;
import com.vote.config.MvcConfig;
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
				ApplicationBeans.class, WebSecurityConfig.class, MvcConfig.class};
		ConfigurableApplicationContext run = SpringApplication.run(context, args);
		LocalContainerEntityManagerFactoryBean bean =
				run.getBean(LocalContainerEntityManagerFactoryBean.class);
		initializeDatabase(bean);
	}

	private static void initializeDatabase(LocalContainerEntityManagerFactoryBean bean) {
		System.out.println("Start database initializing.......");
		try {
			Connection connection = bean.getDataSource().getConnection("admin", "root");
			ScriptRunner runner = new ScriptRunner(connection, false, true);
			runner.setErrorLogWriter(new PrintWriter(System.err));
			runner.setLogWriter(new PrintWriter(System.out));
			InputStream resource = VotingAppApplication.class.getResourceAsStream("/sql/sql.sql");
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
