package com.vote.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

/**
 * {@author Evgeniy}
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("dataSource")
	private DataSource source;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(this.source)
				.usersByUsernameQuery("select email, password from account where email=?")
				.authoritiesByUsernameQuery("select email, role from account where email=?");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests().antMatchers("/polling/**")
				.hasAnyRole("ROLE_ADMIN", "ROLE_USER").anyRequest().permitAll()
				.and()
				.formLogin().loginPage("/login").loginProcessingUrl("j_spring_security_check")
				.usernameParameter("email").passwordParameter("password")
				.and()
				.logout().logoutSuccessUrl("/login?logout")
				.and().exceptionHandling().accessDeniedPage("/error/403")
				.and()
				.csrf();
	}
}
