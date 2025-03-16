package com.lyuboslav.employeeoverlapcalculator;

import com.lyuboslav.employeeoverlapcalculator.model.Employee;
import com.lyuboslav.employeeoverlapcalculator.model.Overlap;
import com.lyuboslav.employeeoverlapcalculator.model.Project;
import com.lyuboslav.employeeoverlapcalculator.parse.ParseUtils;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
	private static final Scanner scanner = new Scanner(System.in);
	private static final CalculatorService calculator = new CalculatorService();

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
		Map<Integer, Project> projects = calculator.getProjects(employees);
		projects.values().forEach(project -> {
			List<Overlap> overlaps = calculator.getLongestOverlapsForProject(project);
			if (!overlaps.isEmpty()) {
				System.out.println("Project ID: " + project.getProjectId());
				overlaps.forEach(System.out::println);
			} else {
				System.out.println("No overlaps found for project ID: " + project.getProjectId());
			}
		});
	}
}