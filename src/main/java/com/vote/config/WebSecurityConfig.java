package com.vote.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(this.source)
				.usersByUsernameQuery("select email, password, 1 from account where email=?")
				.authoritiesByUsernameQuery("select email, role, 1 from account where email=?");
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		SimpleUrlAuthenticationSuccessHandler loginSuccessHandler = new SimpleUrlAuthenticationSuccessHandler("/polling");
		AntPathRequestMatcher logout = new AntPathRequestMatcher("/logout");
		http
				.authorizeRequests().antMatchers("/polling/**", "/vote")
				.hasAnyAuthority("ADMIN", "USER")
				.antMatchers("/**").permitAll()
				.and()
				.formLogin().loginPage("/login").successHandler(loginSuccessHandler)
				.usernameParameter("email").passwordParameter("password")
				.and()
				.logout().logoutRequestMatcher(logout).logoutSuccessUrl("/index")
				.and()
				.csrf().disable();
	}
}
