package com.vote.controller;

import com.vote.entity.DomainObject;
import com.vote.entity.Polling;
import com.vote.entity.PollingSchedule;
import com.vote.entity.Vote;
import com.vote.service.PollingService;
import com.vote.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
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
	public ModelAndView index() throws IOException {
		return new ModelAndView("index");
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String createPolling(@ModelAttribute("polling") Polling polling,
								@ModelAttribute("schedule") List<PollingSchedule> schedules) {
		this.service.createPolling(polling, schedules);
		return "redirect:/polling/" + polling.getId();
	}

	@RequestMapping(value = "/polling/{id}", method = RequestMethod.GET)
	public @ResponseBody Polling getPolling(@PathVariable("id") Integer id) {
		Map<String, DomainObject> map = new HashMap<>(2);
		Polling polling = this.service.getPolling(id);
		map.put("polling", polling);
		return polling;
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

	private List<String> getResourceFiles( String path ) {
		List<String> filenames = new ArrayList<>();

		try(
				InputStream in = getResourceAsStream( path );
				BufferedReader br = new BufferedReader( new InputStreamReader( in ) ) ) {
			String resource;

			while( (resource = br.readLine()) != null ) {
				filenames.add( resource );
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return filenames;
	}

	private InputStream getResourceAsStream( String resource ) {
		final InputStream in = Thread.currentThread()
				.getContextClassLoader().getResourceAsStream(resource);

		return in == null ? getClass().getResourceAsStream( resource ) : in;
	}
}
