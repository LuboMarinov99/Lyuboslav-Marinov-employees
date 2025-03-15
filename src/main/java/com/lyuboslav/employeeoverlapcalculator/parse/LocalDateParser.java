package com.lyuboslav.employeeoverlapcalculator.parse;

import com.opencsv.bean.AbstractBeanField;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Locale.getDefault;

public class LocalDateParser extends AbstractBeanField<LocalDate, String> {
	private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
			.appendOptional(ofPattern("yyyy-MM-dd"))
			.appendOptional(ofPattern("yyyy/MM/dd"))
			.appendOptional(ofPattern("yyyy.MM.dd"))
			.appendOptional(ofPattern("dd/MM/yyyy"))
			.appendOptional(ofPattern("dd-MM-yyyy"))
			.appendOptional(ofPattern("dd.MM.yyyy"))
			.appendOptional(ofPattern("MM/dd/yyyy"))
			.appendOptional(ofPattern("MM-dd-yyyy"))
			.appendOptional(ofPattern("MM.dd.yyyy"))
			.appendOptional(ofPattern("dd MMM yyyy"))
			.appendOptional(ofPattern("dd MMMM yyyy"))
			.appendOptional(ofPattern("yyyyMMdd"))
			.appendOptional(ofPattern("dd/MM/yy"))
			.toFormatter(getDefault());

	@Override
	protected Object convert(String value) {
		if (value == null ||
				value.isBlank() ||
				value.trim().equalsIgnoreCase("null")) {
			return LocalDate.now();
		}

		return LocalDate.parse(value, FORMATTER);
	}
}
