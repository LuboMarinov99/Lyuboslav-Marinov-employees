package com.lyuboslav.employeeoverlapcalculator.parse;

import com.opencsv.bean.AbstractBeanField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class LocalDateParser extends AbstractBeanField<LocalDate, String> {
	private final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			.appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
			.appendOptional(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
			.appendOptional(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
			.appendOptional(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
			.appendOptional(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
			.appendOptional(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
			.appendOptional(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
			.appendOptional(DateTimeFormatter.ofPattern("MM-dd-yyyy"))
			.appendOptional(DateTimeFormatter.ofPattern("MM.dd.yyyy"))
			.appendOptional(DateTimeFormatter.ofPattern("dd MMM yyyy"))
			.appendOptional(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
			.appendOptional(DateTimeFormatter.ofPattern("yyyyMMdd"))
			.appendOptional(DateTimeFormatter.ofPattern("dd/MM/yy"))
			.toFormatter(Locale.getDefault());

	@Override
	protected Object convert(String value) {
		if (value == null || value.isEmpty() || value.isBlank() || value.equalsIgnoreCase("null")) {
			return LocalDate.now();
		}

		return LocalDate.parse(value, formatter);
	}
}
