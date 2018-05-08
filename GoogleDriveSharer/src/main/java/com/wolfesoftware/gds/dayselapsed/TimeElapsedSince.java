package com.wolfesoftware.gds.dayselapsed;

import java.time.LocalDateTime;
import java.time.Period;

import com.wolfesoftware.gds.configuration.Configuration;
public class TimeElapsedSince {

	public static String timeBetween(LocalDateTime begin, LocalDateTime end) {
		Period period = Period.between(begin.toLocalDate(), end.toLocalDate());
		String returnPeriod = "";
		if (period.getYears()!=0) {
			returnPeriod+=period.getYears() + " year(s)";
		}
		if (period.getMonths()!=0) {
			returnPeriod+=period.getMonths() + " month(s)";
		}
		if (period.getDays()!=0) {
			returnPeriod+=period.getDays() + " day(s)";
		}
		return returnPeriod;
	}

	public static String timeTillNow(LocalDateTime begin) {
		return timeBetween(begin, LocalDateTime.now());
	}

	public static String timeSinceJagosBirth(LocalDateTime begin) {
		return timeBetween(LocalDateTime.parse(Configuration.get("jagos.date.of.birth")), LocalDateTime.now());
	}

}
