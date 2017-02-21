package com.vote.controller;

import com.vote.entity.Person;
import com.vote.entity.Vote;
import com.vote.service.AccountService;
import com.vote.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * {@author Evgeniy}
 */
@Controller
public class VotesController extends ExceptionHandlerController {

	@Autowired
	@Qualifier("voteService")
	private VoteService service;

	@Autowired
	@Qualifier("accountService")
	private AccountService accountService;

	@RequestMapping(value = "/polling/{id}/statistic", method = RequestMethod.GET)
	public @ResponseBody Map<String, Long> getVoteStatistic(@PathVariable("id") Integer id) {
		return this.service.getVotesStatistic(id);
	}

	@RequestMapping(value = "/polling/{id}/data", method = RequestMethod.GET)
	public @ResponseBody List<Vote> getPollingData(
			@PathVariable("id") Integer id,
			@RequestParam(value = "startIndex", defaultValue = "1") Integer startIndex,
			@RequestParam(value = "count", defaultValue = "10") Integer count) {
		return this.service.getVotes(id, startIndex, count);
	}

	@RequestMapping(value = "/vote", method = RequestMethod.POST)
	public @ResponseBody String registerVote(@RequestBody Vote vote, Authentication auth) {
		Person person = this.accountService.getPersonFromAuthentication(auth);
		this.service.registerVote(vote, person);
		return "Vote registered";
	}
}
