package com.lyuboslav.employeeoverlapcalculator.parse;

import com.lyuboslav.employeeoverlapcalculator.model.Employee;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.util.List;

public class ParseUtils {
	public static List<Employee> parseCsv() {
		try (FileReader reader = new FileReader("src/main/java/com/lyuboslav/employeeoverlapcalculator/employees.csv")) {
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
