package com.lyuboslav.employeeoverlapcalculator.parse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LocalDateParserTest {
	private LocalDateParser parser;

	@BeforeEach void setUp() {
		parser = new LocalDateParser();
	}

	@ParameterizedTest
	@CsvSource({
			"2020-01-01, 2020-01-01",
			"2020/01/01, 2020-01-01",
			"2020.01.01, 2020-01-01",
			"01/01/2020, 2020-01-01",
			"01-01-2020, 2020-01-01",
			"01.01.2020, 2020-01-01",
			"01/01/2020, 2020-01-01",
			"01-01-2020, 2020-01-01",
			"01.01.2020, 2020-01-01",
			"01 Jan 2020, 2020-01-01",
			"01 January 2020, 2020-01-01",
			"20200101, 2020-01-01",
			"01/01/20, 2020-01-01"
	})
	void testConvert(String input, String expected) {
		//given
		LocalDate expectedDate = LocalDate.parse(expected);

		//when
		LocalDate result = (LocalDate) parser.convert(input);

		//then
		assertEquals(expectedDate, result);
	}

	@Test
	void testConvertNull() {
		//given
		LocalDate expectedDate = LocalDate.now();

		//when
		LocalDate result = (LocalDate) parser.convert(null);

		//then
		assertEquals(expectedDate, result);
	}

	@Test
	void testConvertNullString() {
		//given
		LocalDate expectedDate = LocalDate.now();

		//when
		LocalDate result = (LocalDate) parser.convert("null");

		//then
		assertEquals(expectedDate, result);
	}

	@Test
	void testConvertBlank() {
		//given
		LocalDate expectedDate = LocalDate.now();

		//when
		LocalDate result = (LocalDate) parser.convert("");

		//then
		assertEquals(expectedDate, result);
	}
}