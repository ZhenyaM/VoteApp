package com.vote.controller;

import com.vote.entity.AccountDTO;
import com.vote.service.AccountService;
import com.vote.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * {@author Evgeniy}
 */
@Controller("loginController")
public class LoginController extends ExceptionHandlerController {

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	@Qualifier("accountService")
	private AccountService service;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage() {
		return "login";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String getRegisterPage() {
		return "redirect:/index";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String createNewAccount(@Valid AccountDTO account, BindingResult result, HttpServletRequest req) {
		if (result.hasErrors()) {
			throw Utils.getMultiValueException(result.getAllErrors());
		}
		this.service.saveAccount(account);
		return authenticateNewUser(account, req);
	}

	private String authenticateNewUser(AccountDTO account, HttpServletRequest req) {
		UsernamePasswordAuthenticationToken token =
				new UsernamePasswordAuthenticationToken(account.getEmail(), account.getPassword());
		req.getSession();
		token.setDetails(new WebAuthenticationDetails(req));
		Authentication authenticate = this.manager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		return "redirect:/polling/list";
	}
}
