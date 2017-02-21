package com.vote.utils;

import com.vote.utils.exeception.MultiValueException;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * {@author Evgeniy}
 */
public class Utils {

	public static MultiValueException getMultiValueException(List<ObjectError> errors) {
		Collector<ObjectError, ?, Map<String, String>> collector =
				Collectors.toMap(ObjectError::getObjectName, ObjectError::getDefaultMessage,
						(val1, val2) -> val1);
		Map<String, String> collect = errors.stream().collect(collector);
		return new MultiValueException(collect);
	}
}
