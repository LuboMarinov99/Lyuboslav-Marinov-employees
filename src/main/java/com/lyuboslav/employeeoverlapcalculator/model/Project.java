package com.lyuboslav.employeeoverlapcalculator.model;

import java.util.ArrayList;
import java.util.List;

public class Project {
	private final int projectId;

	private final List<Employee> employees;

	public Project(int projectId, Employee employee) {
		this.projectId = projectId;
		employees = new ArrayList<>();
		employees.add(employee);
	}

	public int getProjectId() {
		return projectId;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void addEmployee(Employee employee) {
		employees.add(employee);
	}
}
