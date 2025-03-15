package com.lyuboslav.employeeoverlapcalculator;

import com.lyuboslav.employeeoverlapcalculator.model.Employee;
import com.lyuboslav.employeeoverlapcalculator.model.Project;
import com.lyuboslav.employeeoverlapcalculator.parse.ParseUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		List<Employee> employees = ParseUtils.parseCsv();

		Map<Integer, Project> projects = new HashMap<>();
		employees.forEach(employee -> {
			if (projects.containsKey(employee.getProjectId())) {
				projects.get(employee.getProjectId()).addEmployee(employee);
			} else {
				projects.put(employee.getProjectId(), new Project(employee.getProjectId(), employee));
			}
		});

		System.out.println("done parsing");
	}
}