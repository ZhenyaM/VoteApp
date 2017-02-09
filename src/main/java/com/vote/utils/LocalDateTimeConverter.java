package com.vote.utils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * {@author Evgeniy}
 */
@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Date> {

	private final static String PATTERN = "yyyy-MM-dd HH-mm-ss";

	@Override
	public Date convertToDatabaseColumn(LocalDateTime attribute) {
		return attribute != null ? Date.from(Instant.from(attribute)) : null;
	}

	@Override
	public LocalDateTime convertToEntityAttribute(Date dbData) {
		if (dbData == null) {
			return null;
		}

		SimpleDateFormat format = new SimpleDateFormat(PATTERN);
		return LocalDateTime.parse(format.format(dbData), DateTimeFormatter.ofPattern(PATTERN));

	}
}
