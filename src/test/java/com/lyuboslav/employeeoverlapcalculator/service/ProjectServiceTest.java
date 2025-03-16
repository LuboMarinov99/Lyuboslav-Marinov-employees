package com.lyuboslav.employeeoverlapcalculator.service;

import com.lyuboslav.employeeoverlapcalculator.model.Employee;
import com.lyuboslav.employeeoverlapcalculator.model.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectServiceTest {

	private ProjectService projectService;

	@BeforeEach
	void setUp() {
		projectService = new ProjectService();
	}

	@Test
	void testGetProjects_whenEmptyList_shouldReturnEmptyMap() {
		//given
		List<Employee> employees = List.of();

		//when
		var result = projectService.getProjects(employees);

		//then
		assertTrue(result.isEmpty());
	}

	@Test
	void testGetProjects_whenGivenValidEmployeeCollection_shouldCorrect() {
		//given
		List<Employee> employees = getEmployees();

		//when
		var result = projectService.getProjects(employees);

		//then
		assertFalse(result.isEmpty());
		assertEquals(3, result.size());
		List<Project> projects = List.copyOf(result.values());

		Project project1 = projects.get(0);
		Project project2 = projects.get(1);
		Project project3 = projects.get(2);

		assertEquals(2, project1.getEmployees().size());
		assertEquals(3, project2.getEmployees().size());
		assertEquals(4, project3.getEmployees().size());

		assertEquals(1, project1.getEmployees().getFirst().getEmployeeId());
		assertEquals(3, project2.getEmployees().getFirst().getEmployeeId());
		assertEquals(6, project3.getEmployees().getFirst().getEmployeeId());
	}

	private static List<Employee> getEmployees() {
		Employee employee1 = new Employee(1, 1, null, null);
		Employee employee2 = new Employee(2, 1, null, null);
		Employee employee3 = new Employee(3, 2, null, null);
		Employee employee4 = new Employee(4, 2, null, null);
		Employee employee5 = new Employee(5, 2, null, null);
		Employee employee6 = new Employee(6, 3, null, null);
		Employee employee7 = new Employee(7, 3, null, null);
		Employee employee8 = new Employee(8, 3, null, null);
		Employee employee9 = new Employee(8, 3, null, null);

		return List.of(employee1, employee2, employee3, employee4, employee5, employee6, employee7, employee8, employee9);
	}
}