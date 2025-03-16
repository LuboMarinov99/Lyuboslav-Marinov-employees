package com.lyuboslav.employeeoverlapcalculator.service;

import com.lyuboslav.employeeoverlapcalculator.model.Employee;
import com.lyuboslav.employeeoverlapcalculator.model.Overlap;
import com.lyuboslav.employeeoverlapcalculator.model.Project;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class CalculatorService {

	/**
	 * Calculates the overlaps between all employees in a project and returns the longest ones (if more than one
	 * occurrence exists). If the project has less than two employees, an empty optional object is returned.
	 * If no overlaps are found, an empty collection is returned instead. The method does not consider overlaps between
	 * the same employee if he/she has been assigned to the project more than once.
	 *
	 * @param project - The project to calculate the overlaps for.
	 * @return - An optional object representing the result of the calculation.
	 */
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
					overlaps = new ArrayList<>();
					overlaps.add(new Overlap(employee1.getEmployeeId(), employee2.getEmployeeId(), overlapDays));
					maxOverlapDays = overlapDays;
				} else if (overlapDays == maxOverlapDays && overlapDays > 0) {
					overlaps.add(new Overlap(employee1.getEmployeeId(), employee2.getEmployeeId(), overlapDays));
				}
			}
		}

		return Optional.of(overlaps);
	}

	/**
	 * Returns an integer representing the number of days the two employees overlap. Regardless of the order of the
	 * start/finish date of the pair, the latest start date and the earliest end date are used to calculate the overlap.
	 * If the two employees do not overlap, the method returns 0. The result is inclusive of the start and end date.
	 *
	 * @param employee1 - The first employee.
	 * @param employee2 - The second employee.
	 * @return - An integer representing the number of days the two employees overlap.
	 */
	private int calculatePairOverlap(Employee employee1, Employee employee2) {
		LocalDate maxStartDate = employee1.getDateFrom().isAfter(employee2.getDateFrom()) ? employee1.getDateFrom() : employee2.getDateFrom();
		LocalDate minEndDate = employee1.getDateTo().isBefore(employee2.getDateTo()) ? employee1.getDateTo() : employee2.getDateTo();

		if (maxStartDate.isAfter(minEndDate)) {
			return 0;
		}

		return (int) ChronoUnit.DAYS.between(maxStartDate, minEndDate) + 1;
	}
}
