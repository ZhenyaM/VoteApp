package com.vote.controller;

import com.vote.entity.Polling;
import com.vote.entity.Vote;
import com.vote.service.PollingService;
import com.vote.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * {@author Evgeniy}
 */
@Controller
public class PollingController {

	@Autowired
	@Qualifier("pollingService")
	private PollingService service;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index() {
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/polling", method = RequestMethod.GET)
	public String getPolling() {
		return "polling";
	}

	@RequestMapping(value = "/polling/list", method = RequestMethod.GET)
	public @ResponseBody List<Polling> getPollingList(@RequestParam("startIndex") Integer startIndex,
								 					  @RequestParam("count") Integer count) {
		return this.service.getPollingList(startIndex, count);
	}

	@RequestMapping(value = "/polling/form", method = RequestMethod.GET)
	public String getPollingForm() {
		return "createPolling";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String createPolling(@ModelAttribute("polling") Polling polling) {
		//this.service.createPolling(polling);
		System.out.println(polling);
		return "redirect:/polling/" + 1;
	}

	@RequestMapping(value = "/polling/{id}", method = RequestMethod.GET)
	public ModelAndView getPolling(@PathVariable("id") Integer id) {
		Polling polling = this.service.getPolling(id);
		ModelAndView view = new ModelAndView("polling");
		view.addObject("polling", polling);
		return view;
	}

	@RequestMapping(value = "/polling/{id}/start", method = RequestMethod.POST)
	public @ResponseBody Result startPolling(@PathVariable("id") Integer id) {
		return this.service.startPolling(id);
	}

	@RequestMapping(value = "/polling/{id}/stop", method = RequestMethod.POST)
	public @ResponseBody Result stopPolling(@PathVariable("id") Integer id) {
		return this.service.startPolling(id);
	}

	@RequestMapping(value = "/polling/{id}/statistic", method = RequestMethod.GET)
	public @ResponseBody Map<String, Long> getVoteStatistic(@PathVariable("id") Integer id) {
		return this.service.getVotesStatistic(id);
	}

	@RequestMapping(value = "/polling/{id}/data", method = RequestMethod.GET)
	public @ResponseBody
	List<Vote> getPollingData(@PathVariable("id") Integer id) {
		return this.service.getVotes(id);
	}
}
