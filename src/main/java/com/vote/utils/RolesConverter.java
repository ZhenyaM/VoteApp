package com.vote.utils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * {@author Evgeniy}
 */
@Converter(autoApply = true)
public class RolesConverter implements AttributeConverter<Roles, String> {
	@Override
	public String convertToDatabaseColumn(Roles attribute) {
		return attribute.toString();
	}

	@Override
	public Roles convertToEntityAttribute(String dbData) {
		return Roles.valueOf(dbData);
	}
}
