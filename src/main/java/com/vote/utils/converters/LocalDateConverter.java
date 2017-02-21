package com.vote.utils.converters;

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

	public static final String PATTERN = "yyyy-MM-dd";
	public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(PATTERN);
	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

	@Override
	public Date convertToDatabaseColumn(LocalDate attribute) {
		return attribute != null ? Date.from(Instant.from(attribute)) : null;
	}

	@Override
	public LocalDate convertToEntityAttribute(Date dbData) {
		return dbData == null ? null :
				LocalDate.parse(DATE_FORMATTER.format(dbData), FORMATTER);
	}
}
