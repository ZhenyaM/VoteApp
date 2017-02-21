package com.vote.controller;

import com.vote.utils.exeception.MultiValueException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Map;

/**
 * {@author Evgeniy}
 */
@Controller
public class ExceptionHandlerController {

	@ExceptionHandler(MultiValueException.class)
	public @ResponseBody Map<String, String> multiValueHandler(MultiValueException ex) {
		ex.printStackTrace();
		return ex.getMessages();
	}

	@ExceptionHandler(Exception.class)
	public @ResponseBody Map<String, String> getException(Exception ex) {
		ex.printStackTrace();
		return Collections.singletonMap("exception", ex.getMessage());
	}
}
