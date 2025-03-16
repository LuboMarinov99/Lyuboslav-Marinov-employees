package com.lyuboslav.employeeoverlapcalculator.service;

import com.lyuboslav.employeeoverlapcalculator.model.Employee;
import com.lyuboslav.employeeoverlapcalculator.model.Project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectService {
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
}
