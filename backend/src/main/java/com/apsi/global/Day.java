package com.apsi.global;

import java.time.LocalDate;

public enum Day {
	SUNDAY,
	MONDAY,
	TUESDAY,
	WEDNESDAY,
	THURSDAY,
	FRIDAY,
	SATURDAY;

	public static Day getForDate(LocalDate date) {
		return Day.valueOf(date.getDayOfWeek().toString());
	}

}
