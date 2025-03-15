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
}
