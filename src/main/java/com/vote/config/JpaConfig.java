package com.vote.config;

import com.vote.VotingAppApplication;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * {@author Evgeniy}
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = VotingAppApplication.class)
public class JpaConfig implements TransactionManagementConfigurer {

	@Value("${app.dataSource.driverClassName}")
	private String driver;
	@Value("${app.dataSource.databaseurl}")
	private String url;
	@Value("${app.dataSource.username}")
	private String username;
	@Value("${app.dataSource.password}")
	private String password;
	@Value("${app.hibernate.dialect}")
	private String dialect;
	@Value("${app.hibernate.hbm2ddl.auto}")
	private String hbm2ddlAuto;

	private LocalContainerEntityManagerFactoryBean entityManagerFactoryBean;

	@Bean(name = "dataSource")
	@ConfigurationProperties("dataSource")
	public DataSource configureDataSource() {
		return DataSourceBuilder.create()
				.driverClassName(driver)
				.url(url)
				.username(username)
				.password(password)
				.build();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean configureEntityManagerFactory() {
		if (this.entityManagerFactoryBean == null) {
			this.entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
			this.entityManagerFactoryBean.setDataSource(configureDataSource());
			this.entityManagerFactoryBean.setPackagesToScan("com.vote");
			this.entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

			Properties props = new Properties();
			props.put(Environment.DIALECT, this.dialect);
			props.put(Environment.HBM2DDL_AUTO, this.hbm2ddlAuto);
			this.entityManagerFactoryBean.setJpaProperties(props);
		}

		return this.entityManagerFactoryBean;
	}

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		JpaTransactionManager jpa = new JpaTransactionManager();
		jpa.setEntityManagerFactory(this.configureEntityManagerFactory().getObject());
		return jpa;
	}
}
