package com.lyuboslav.employeeoverlapcalculator.parse;

import com.lyuboslav.employeeoverlapcalculator.model.Employee;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class ParseUtils {
	public static List<Employee> parseCsv(String fileName) {
		try (InputStreamReader reader = new InputStreamReader(
				Objects.requireNonNull(ParseUtils.class.getClassLoader().getResourceAsStream(fileName)))
		) {
			CsvToBean<Employee> csvToBean = new CsvToBeanBuilder<Employee>(reader)
					.withType(Employee.class)
					.withIgnoreLeadingWhiteSpace(true)
					.withIgnoreEmptyLine(true)
					.build();
			return List.copyOf(csvToBean.parse());
		} catch (Exception e) {
			System.out.println("Error reading file: " + e.getMessage());
			return List.of();
		}
	}
}
