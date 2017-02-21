package com.vote.utils.exeception;

import java.util.Map;

/**
 * {@author Evgeniy}
 */
public class MultiValueException extends RuntimeException {

	private final Map<String, String> messages;

	public MultiValueException(Map<String, String> messages) {
		super();
		this.messages = messages;
	}

	public Map<String, String> getMessages() {
		return this.messages;
	}

}
