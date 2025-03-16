package com.lyuboslav.employeeoverlapcalculator.model;

public record Overlap(int employeeId1, int employeeId2, int days) {

	@Override
	public String toString() {
		return "employee 1: %s, employee 2: %s, overlap: %s days".formatted(employeeId1, employeeId2, days);
	}
}
