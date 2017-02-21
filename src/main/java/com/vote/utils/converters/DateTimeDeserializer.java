package com.vote.utils.converters;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * {@author Evgeniy}
 */
public class DateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
	@Override
	public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException{
		return LocalDateTime.parse(p.readValueAs(String.class), LocalDateTimeConverter.FORMATTER);
	}
}
