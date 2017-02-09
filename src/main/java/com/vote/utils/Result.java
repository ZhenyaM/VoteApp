package com.vote.utils;

/**
 * {@author Evgeniy}
 */
public enum Result {

	SUCCESS("Success"),

	FAILED("Failed"),

	NOT_FOUND("Failed. Current polling not exist");

	private String result;

	Result(String result) {
		this.result = result;
	}

	public String getResult() {
		return this.result;
	}
}
