package com.vote.utils.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.text.ParseException;
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

	public static final String PATTERN = "yyyy-MM-dd HH-mm-ss";
	public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(PATTERN);
	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

	@Override
	public Date convertToDatabaseColumn(LocalDateTime attribute) {
		try {
			return attribute == null ? null :
					DATE_FORMATTER.parse(attribute.format(FORMATTER));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public LocalDateTime convertToEntityAttribute(Date dbData) {
		return dbData == null ? null :
				LocalDateTime.parse(DATE_FORMATTER.format(dbData), FORMATTER);

	}
}
