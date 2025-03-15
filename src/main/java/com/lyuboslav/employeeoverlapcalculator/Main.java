package com.lyuboslav.employeeoverlapcalculator;

import com.lyuboslav.employeeoverlapcalculator.model.Employee;
import com.lyuboslav.employeeoverlapcalculator.model.Project;
import com.lyuboslav.employeeoverlapcalculator.parse.ParseUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

		projects.values().forEach(Main::calculateLongestOverlap);

		System.out.println("done parsing");
	}

	public static void calculateLongestOverlap(Project project) {
		int employeeId1 = 0;
		int employeeId2 = 0;
		int maxOverlapDays = 0;

		if (project.getEmployees().size() < 2) {
			System.out.println("Overlap not possible as there are less than 2 employees in the project");
			return;
		}

		int numberOfEmployees = project.getEmployees().size();
		for (int i = 0; i < numberOfEmployees; i++) {
			for (int j = i + 1; j < numberOfEmployees; j++) {
				Employee employee1 = project.getEmployees().get(i);
				Employee employee2 = project.getEmployees().get(j);
				int overlapDays = calculatePairOverlap(employee1, employee2);
				if (overlapDays >= maxOverlapDays) {
					employeeId1 = employee1.getEmployeeId();
					employeeId2 = employee2.getEmployeeId();
					maxOverlapDays = overlapDays;
				}
			}
		}

		if (maxOverlapDays > 0) {
			System.out.println("The employees with the longest overlap in project " + project.getProjectId() + " are: " + employeeId1 + " and " + employeeId2 + " with " + maxOverlapDays + " days");
		} else {
			System.out.println("There is no overlap between any of the employees");
		}
	}

	public static int calculatePairOverlap(Employee employee1, Employee employee2) {
		LocalDate maxStartDate = employee1.getDateFrom().isAfter(employee2.getDateFrom()) ? employee1.getDateFrom() : employee2.getDateFrom();
		LocalDate minEndDate = employee1.getDateTo().isBefore(employee2.getDateTo()) ? employee1.getDateTo() : employee2.getDateTo();

		if (maxStartDate.isAfter(minEndDate)) {
			return 0;
		}

		return (int) ChronoUnit.DAYS.between(maxStartDate, minEndDate);
	}
}