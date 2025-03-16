package com.lyuboslav.employeeoverlapcalculator;

import com.lyuboslav.employeeoverlapcalculator.model.Employee;
import com.lyuboslav.employeeoverlapcalculator.model.Overlap;
import com.lyuboslav.employeeoverlapcalculator.model.Project;
import com.lyuboslav.employeeoverlapcalculator.service.CalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorServiceTest {

	private CalculatorService calculatorService;

	@BeforeEach
	void setUp() {
		calculatorService = new CalculatorService();
	}

	@Test
	void testGetOverlaps_whenOnlyOneEmployee_shouldReturnEmptyOptional() {
		//given
		Employee employee = new Employee(1, 1, LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 10));
		Project project = new Project(1, employee);

		//when
		var result = calculatorService.getLongestOverlapsForProject(project);

		//then
		assertTrue(result.isEmpty());
	}

	@Test
	void testGetOverlaps_whenNoOverlaps_shouldReturnEmptyCollection() {
		//given
		Employee employee1 = new Employee(1, 1, LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 10));
		Employee employee2 = new Employee(2, 1, LocalDate.of(2021, 1, 11), LocalDate.of(2021, 1, 20));
		Project project = new Project(1, employee1);
		project.addEmployee(employee2);

		//when
		var result = calculatorService.getLongestOverlapsForProject(project);

		//then
		assertTrue(result.isPresent());
		assertEquals(0, result.get().size());
	}

	@Test
	void testGetOverlaps_whenPartialOverlap_shouldReturnCorrectOverlap() {
		//given
		Employee employee1 = new Employee(1, 1, LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 10));
		Employee employee2 = new Employee(2, 1, LocalDate.of(2021, 1, 10), LocalDate.of(2021, 1, 20));
		Project project = new Project(1, employee1);
		project.addEmployee(employee2);

		//when
		var result = calculatorService.getLongestOverlapsForProject(project);

		//then
		assertTrue(result.isPresent());
		assertEquals(1, result.get().size());
		assertEquals(1, result.get().getFirst().days());
	}

	@Test
	void testGetOverlaps_whenFullOverlap_shouldReturnCorrectOverlap() {
		//given
		Employee employee1 = new Employee(1, 1, LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 10));
		Employee employee2 = new Employee(2, 1, LocalDate.of(2021, 1, 2), LocalDate.of(2021, 1, 9));
		Project project = new Project(1, employee1);
		project.addEmployee(employee2);

		//when
		var result = calculatorService.getLongestOverlapsForProject(project);

		//then
		assertTrue(result.isPresent());
		assertEquals(1, result.get().size());
		assertEquals(8, result.get().getFirst().days());
	}

	@Test
	void testGetOverlaps_whenFullInvertedOverlap_shouldReturnCorrectOverlap() {
		//given
		Employee employee2 = new Employee(1, 1, LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 10));
		Employee employee1 = new Employee(2, 1, LocalDate.of(2021, 1, 2), LocalDate.of(2021, 1, 9));
		Project project = new Project(1, employee1);
		project.addEmployee(employee2);

		//when
		var result = calculatorService.getLongestOverlapsForProject(project);

		//then
		assertTrue(result.isPresent());
		assertEquals(1, result.get().size());
		assertEquals(8, result.get().getFirst().days());
	}

	@Test
	void testGetOverlaps_whenStartingOnTheSameDay_shouldReturnCorrectOverlap() {
		//given
		Employee employee1 = new Employee(1, 1, LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 10));
		Employee employee2 = new Employee(2, 1, LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 9));
		Project project = new Project(1, employee1);
		project.addEmployee(employee2);

		//when
		var result = calculatorService.getLongestOverlapsForProject(project);

		//then
		assertTrue(result.isPresent());
		assertEquals(1, result.get().size());
		assertEquals(9, result.get().getFirst().days());
	}

	@Test
	void testGetOverlaps_whenEndingOnTheSameDay_shouldReturnCorrectOverlap() {
		//given
		Employee employee1 = new Employee(1, 1, LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 10));
		Employee employee2 = new Employee(2, 1, LocalDate.of(2021, 1, 2), LocalDate.of(2021, 1, 10));
		Project project = new Project(1, employee1);
		project.addEmployee(employee2);

		//when
		var result = calculatorService.getLongestOverlapsForProject(project);

		//then
		assertTrue(result.isPresent());
		assertEquals(1, result.get().size());
		assertEquals(9, result.get().getFirst().days());
	}

	@Test
	void testGetOverlaps_whenStartAndEndDateAreTheSame_shouldReturnCorrectOverlap() {
		//given
		Employee employee1 = new Employee(1, 1, LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 10));
		Employee employee2 = new Employee(2, 1, LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 10));
		Project project = new Project(1, employee1);
		project.addEmployee(employee2);

		//when
		var result = calculatorService.getLongestOverlapsForProject(project);

		//then
		assertTrue(result.isPresent());
		assertEquals(1, result.get().size());
		assertEquals(10, result.get().getFirst().days());
	}

	@Test
	void testGetOverlaps_whenSameEmployee_shouldNotReturnOverlap() {
		//given
		Employee employee1 = new Employee(1, 1, LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 10));
		Employee employee2 = new Employee(1, 1, LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 10));
		Project project = new Project(1, employee1);
		project.addEmployee(employee2);

		//when
		var result = calculatorService.getLongestOverlapsForProject(project);

		//then
		assertTrue(result.isPresent());
		assertEquals(0, result.get().size());
	}

	@Test
	void testGetOverlaps_whenThreeEmployees_shouldReturnOneLongestOverlap() {
		//given
		Employee employee1 = new Employee(1, 1, LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 3));
		Employee employee2 = new Employee(2, 1, LocalDate.of(2021, 1, 2), LocalDate.of(2021, 1, 30));
		Employee employee3 = new Employee(3, 1, LocalDate.of(2021, 1, 15), LocalDate.of(2021, 3, 30));
		Project project = new Project(1, employee1);
		project.addEmployee(employee2);
		project.addEmployee(employee3);

		//when
		var result = calculatorService.getLongestOverlapsForProject(project);

		//then
		assertTrue(result.isPresent());
		assertEquals(1, result.get().size());
		Overlap overlap = result.get().getFirst();
		assertEquals(16, overlap.days());
		assertEquals(2, overlap.employeeId1());
		assertEquals(3, overlap.employeeId2());
	}

	@Test
	void testGetOverlaps_whenThreeEmployeesAndTwoLongestOverlaps_shouldReturnTwoOverlaps() {
		//given
		Employee employee1 = new Employee(1, 1, LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 10));
		Employee employee2 = new Employee(2, 1, LocalDate.of(2021, 1, 5), LocalDate.of(2021, 1, 14));
		Employee employee3 = new Employee(3, 1, LocalDate.of(2021, 1, 9), LocalDate.of(2021, 1, 29));
		Project project = new Project(1, employee1);
		project.addEmployee(employee2);
		project.addEmployee(employee3);

		//when
		var result = calculatorService.getLongestOverlapsForProject(project);

		//then
		assertTrue(result.isPresent());
		assertEquals(2, result.get().size());
		Overlap overlap1 = result.get().getFirst();
		Overlap overlap2 = result.get().getLast();

		assertEquals(6, overlap1.days());
		assertEquals(1, overlap1.employeeId1());
		assertEquals(2, overlap1.employeeId2());

		assertEquals(6, overlap2.days());
		assertEquals(2, overlap2.employeeId1());
		assertEquals(3, overlap2.employeeId2());
	}

	@Test
	void testGetOverlaps_whenOneEmployeeHasTwoSpans_shouldReturnCorrectOverlap() {
		//given
		Employee employee1 = new Employee(1, 1, LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 10));
		Employee employee2 = new Employee(2, 1, LocalDate.of(2021, 1, 5), LocalDate.of(2021, 1, 14));
		Employee employee3 = new Employee(1, 1, LocalDate.of(2021, 1, 14), LocalDate.of(2024, 1, 29));
		Project project = new Project(1, employee1);
		project.addEmployee(employee2);
		project.addEmployee(employee3);

		//when
		var result = calculatorService.getLongestOverlapsForProject(project);

		//then
		assertTrue(result.isPresent());
		assertEquals(1, result.get().size());
		Overlap overlap1 = result.get().getFirst();

		assertEquals(6, overlap1.days());
		assertEquals(1, overlap1.employeeId1());
		assertEquals(2, overlap1.employeeId2());
	}

	@Test
	void testGetOverlaps_whenOneEmployeeHasTwoMaxSpans_shouldReturnTwoOverlaps() {
		//given
		Employee employee1 = new Employee(1, 1, LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 10));
		Employee employee2 = new Employee(2, 1, LocalDate.of(2021, 1, 5), LocalDate.of(2021, 1, 14));
		Employee employee3 = new Employee(1, 1, LocalDate.of(2021, 1, 9), LocalDate.of(2021, 1, 29));
		Project project = new Project(1, employee1);
		project.addEmployee(employee2);
		project.addEmployee(employee3);

		//when
		var result = calculatorService.getLongestOverlapsForProject(project);

		//then
		assertTrue(result.isPresent());
		assertEquals(2, result.get().size());
		Overlap overlap1 = result.get().getFirst();
		Overlap overlap2 = result.get().getLast();

		assertEquals(6, overlap1.days());
		assertEquals(1, overlap1.employeeId1());
		assertEquals(2, overlap1.employeeId2());

		assertEquals(6, overlap2.days());
		assertEquals(2, overlap2.employeeId1());
		assertEquals(1, overlap2.employeeId2());
	}
}