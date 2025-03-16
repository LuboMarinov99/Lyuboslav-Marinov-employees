package com.lyuboslav.employeeoverlapcalculator.model;

import com.lyuboslav.employeeoverlapcalculator.parse.LocalDateParser;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import com.opencsv.bean.CsvDate;

import java.time.LocalDate;

public class Employee {
	@CsvBindByPosition(position = 0)
	private int employeeId;
	@CsvBindByPosition(position = 1)
	private int projectId;
	@CsvCustomBindByPosition(position = 2, converter = LocalDateParser.class)
	@CsvDate("yyyy-MM-dd")
	private LocalDate dateFrom;
	@CsvCustomBindByPosition(position = 3, converter = LocalDateParser.class)
	private LocalDate dateTo;

	public Employee(int employeeId, int projectId, LocalDate dateFrom, LocalDate dateTo) {
		this.employeeId = employeeId;
		this.projectId = projectId;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}

	public Employee() {
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public int getProjectId() {
		return projectId;
	}

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}
}
