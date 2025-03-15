package com.lyuboslav.employeeoverlapcalculator;

import com.lyuboslav.employeeoverlapcalculator.model.Employee;
import com.lyuboslav.employeeoverlapcalculator.parse.ParseUtils;

import java.util.List;

public class Main {
	public static void main(String[] args) {
		List<Employee> employees = ParseUtils.parseCsv();
		System.out.println(employees);
	}
}