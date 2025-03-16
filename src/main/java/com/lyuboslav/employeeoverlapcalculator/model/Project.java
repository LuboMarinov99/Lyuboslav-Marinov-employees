package com.lyuboslav.employeeoverlapcalculator.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Project {
	private final int projectId;

	private final List<Employee> employees;

	public Project(int projectId, Employee employee) {
		this.projectId = projectId;
		employees = new ArrayList<>();
		employees.add(employee);
	}

	public void addEmployee(Employee employee) {
		employees.add(employee);
	}
}
