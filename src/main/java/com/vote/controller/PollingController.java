package com.vote.controller;

import com.vote.entity.Person;
import com.vote.entity.Polling;
import com.vote.service.AccountService;
import com.vote.service.PollingService;
import com.vote.utils.Utils;
import com.vote.utils.exeception.MultiValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * {@author Evgeniy}
 */
@Controller
public class PollingController extends ExceptionHandlerController {

	@Autowired
	@Qualifier("pollingService")
	private PollingService pollingService;

	@Autowired
	@Qualifier("accountService")
	private AccountService accountService;

	@RequestMapping(value = "/polling", method = RequestMethod.GET)
	public String getPolling() {
		return "polling_list";
	}

	@RequestMapping(value = "/polling/list", method = RequestMethod.GET)
	public @ResponseBody List<Polling> getPollingList(@RequestParam(value = "startIndex", defaultValue = "1") Integer startIndex,
								 					  @RequestParam(value = "count", defaultValue = "10") Integer count) {
		return this.pollingService.getPollingList(startIndex, count);
	}

	@RequestMapping(value = "/polling/form", method = RequestMethod.GET)
	public String getPollingForm() {
		return "createPolling";
	}

	@RequestMapping(value = "/polling/form", method = RequestMethod.POST)
	public String createPolling(@RequestBody @Valid Polling polling, BindingResult result, Authentication auth) {
		if (result.hasErrors()) {
			throw Utils.getMultiValueException(result.getAllErrors());
		}
		this.pollingService.createPolling(polling, this.accountService.getPersonFromAuthentication(auth));
		return "redirect:/polling/" + polling.getId();
	}

	@RequestMapping(value = "/polling/{id}", method = RequestMethod.GET)
	public String getPolling(@PathVariable("id") Integer id) {
		return "polling";
	}

	@RequestMapping(value = "/polling/{id}/value", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getPollingValue(@PathVariable("id") Integer id, Authentication auth) {
		Polling polling = this.pollingService.getPolling(id);
		if (polling == null) {
			throw new IllegalArgumentException("Current polling not exist: " + id);
		} else {
			Map<String, Object> map = new HashMap<>();
			map.put("data", polling);
			String email = ((User) auth.getPrincipal()).getUsername();
			map.put("isOwner", polling.getOwner().getEmail().equals(email));
			return map;
		}
	}

	@RequestMapping(value = "/polling/{id}/start", method = RequestMethod.POST)
	public @ResponseBody String startPolling(@PathVariable("id") Integer id, Authentication auth) {
		Person person = this.accountService.getPersonFromAuthentication(auth);
		this.pollingService.startPolling(id, person);
		return "Polling start successful";
	}

	@RequestMapping(value = "/polling/{id}/stop", method = RequestMethod.POST)
	public @ResponseBody String stopPolling(@PathVariable("id") Integer id, Authentication auth) {
		Person person = this.accountService.getPersonFromAuthentication(auth);
		this.pollingService.endPolling(id, person);
		return "Polling end successful";
	}
}
