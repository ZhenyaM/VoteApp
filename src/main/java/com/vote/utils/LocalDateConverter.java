package com.vote.utils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * {@author Evgeniy}
 */
@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

	private static final String PATTERN = "yyyy-MM-dd";

	@Override
	public Date convertToDatabaseColumn(LocalDate attribute) {
		return attribute != null ? Date.from(Instant.from(attribute)) : null;
	}

	@Override
	public LocalDate convertToEntityAttribute(Date dbData) {
		if (dbData == null) {
			return null;
		}

		SimpleDateFormat format = new SimpleDateFormat(PATTERN);
		return LocalDate.parse(format.format(dbData), DateTimeFormatter.ofPattern(PATTERN));

	}
}
