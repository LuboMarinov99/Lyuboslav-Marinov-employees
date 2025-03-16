package com.lyuboslav.employeeoverlapcalculator;

import com.lyuboslav.employeeoverlapcalculator.model.Employee;
import com.lyuboslav.employeeoverlapcalculator.model.Overlap;
import com.lyuboslav.employeeoverlapcalculator.model.Project;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class CalculatorService {

	public Map<Integer, Project> getProjects(List<Employee> employees) {
		Map<Integer, Project> projects = new HashMap<>();
		employees.forEach(employee -> {
			if (projects.containsKey(employee.getProjectId())) {
				projects.get(employee.getProjectId()).addEmployee(employee);
			} else {
				projects.put(employee.getProjectId(), new Project(employee.getProjectId(), employee));
			}
		});
		return projects;
	}

	public Optional<List<Overlap>> getLongestOverlapsForProject(Project project) {
		if (project.getEmployees().size() < 2) {
			return Optional.empty();
		}

		List<Overlap> overlaps = new ArrayList<>();
		int maxOverlapDays = 0;
		int numberOfEmployees = project.getEmployees().size();

		for (int i = 0; i < numberOfEmployees; i++) {
			for (int j = i + 1; j < numberOfEmployees; j++) {
				Employee employee1 = project.getEmployees().get(i);
				Employee employee2 = project.getEmployees().get(j);
				if (employee1.getEmployeeId() == employee2.getEmployeeId()) {
					continue;
				}
				int overlapDays = calculatePairOverlap(employee1, employee2);
				if (overlapDays > maxOverlapDays) {
					overlaps.clear();
					overlaps.add(new Overlap(employee1.getEmployeeId(), employee2.getEmployeeId(), overlapDays));
					maxOverlapDays = overlapDays;
				} else if (overlapDays == maxOverlapDays && overlapDays > 0) {
					overlaps.add(new Overlap(employee1.getEmployeeId(), employee2.getEmployeeId(), overlapDays));
				}
			}
		}

		return Optional.of(overlaps);
	}

	private int calculatePairOverlap(Employee employee1, Employee employee2) {
		LocalDate maxStartDate = employee1.getDateFrom().isAfter(employee2.getDateFrom()) ? employee1.getDateFrom() : employee2.getDateFrom();
		LocalDate minEndDate = employee1.getDateTo().isBefore(employee2.getDateTo()) ? employee1.getDateTo() : employee2.getDateTo();

		if (maxStartDate.isAfter(minEndDate)) {
			return 0;
		}

		return (int) ChronoUnit.DAYS.between(maxStartDate, minEndDate);
	}
}
