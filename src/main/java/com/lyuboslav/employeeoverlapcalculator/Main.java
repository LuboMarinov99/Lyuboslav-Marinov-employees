package com.lyuboslav.employeeoverlapcalculator;

import com.lyuboslav.employeeoverlapcalculator.model.Employee;
import com.lyuboslav.employeeoverlapcalculator.model.Overlap;
import com.lyuboslav.employeeoverlapcalculator.model.Project;
import com.lyuboslav.employeeoverlapcalculator.parse.ParseUtils;
import com.lyuboslav.employeeoverlapcalculator.service.CalculatorService;
import com.lyuboslav.employeeoverlapcalculator.service.ProjectService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Main {

	private static final Scanner scanner = new Scanner(System.in);
	private static final CalculatorService calculatorService = new CalculatorService();
	private static final ProjectService projectService = new ProjectService();

	public static void main(String[] args) {
		boolean exit = false;

		do {
			System.out.print("Enter the CSV file name: ");
			String fileName = scanner.nextLine();

			runFlow(fileName);

			System.out.println("done parsing");
			String input;
			do {
				System.out.println("Do you want to exit? (y/n)");
				input = scanner.nextLine().trim().toLowerCase();
			} while (!input.equals("y") && !input.equals("n"));

			if (input.equals("y")) {
				exit = true;
			}
		} while (!exit);
	}

	public static void runFlow(String fileName) {
		List<Employee> employees = ParseUtils.parseCsv(fileName);
		Map<Integer, Project> projects = projectService.getProjects(employees);
		projects.values().forEach(project -> {
			Optional<List<Overlap>> overlapsResult = calculatorService.getLongestOverlapsForProject(project);

			if (overlapsResult.isEmpty()) {
				System.out.println("Overlap impossible. Less than 2 employees found in project ID: " + project.getProjectId());
			} else if (overlapsResult.get().isEmpty()) {
				System.out.println("No overlaps found in project ID: " + project.getProjectId());
			} else {
				System.out.println("Project ID: " + project.getProjectId());
				overlapsResult.get().forEach(System.out::println);
			}
		});
	}
}