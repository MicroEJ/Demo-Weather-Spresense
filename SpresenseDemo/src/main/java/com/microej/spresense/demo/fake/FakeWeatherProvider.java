/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.fake;

import java.util.Calendar;

import com.microej.spresense.demo.Model;
import com.microej.spresense.demo.Time;

/**
 *
 */
public class FakeWeatherProvider {

	private static final int HOUR_IN_HALF = 12;
	private static int MIN_TEMPERATURE = 52;
	private static int DIFF_TEMPERATURE = 75 - MIN_TEMPERATURE;

	private static int MIN_WIND = 1;
	private static int DIFF_WIND = 65 - MIN_WIND;

	private static int MIN_SUNRISE = 48;
	private static int DIFF_SUNRISE = 108 - MIN_SUNRISE;

	private static int MIN_HUMIDITY = 5;
	private static int DIFF_HUMIDITY = 95 - MIN_HUMIDITY;

	private FakeWeatherProvider() {
	}

	/**
	 * @param day
	 * @param hour
	 * @return
	 */
	public static int getTemperature(int dayOfWeek, int hour) {
		return getValue(dayOfWeek, hour, DIFF_TEMPERATURE, MIN_TEMPERATURE);
	}

	/**
	 * @param dayOfWeek
	 * @param hour
	 * @return
	 */
	public static int getWind(int dayOfWeek, int hour) {
		return getValue(dayOfWeek, hour, DIFF_WIND, MIN_WIND);
	}

	/**
	 * @param dayOfWeek
	 * @param hour
	 * @return
	 */
	public static Time getSunrise(int dayOfWeek, int hour) {
		if (dayOfWeek > Calendar.WEDNESDAY) {
			dayOfWeek = Calendar.SATURDAY - dayOfWeek;
		}
		float dayUsage = dayOfWeek / (float) Calendar.WEDNESDAY;
		int sunHour = 5;
		int sunMin = (int) (MIN_SUNRISE - dayUsage * DIFF_SUNRISE);
		while (sunMin >= 60) {
			sunHour++;
			sunMin -= 60;
		}
		return new Time(0, 0, dayOfWeek, sunHour, sunMin);
	}

	public static int getType(int dayOfWeek, int hour) {
		int weather;
		switch (dayOfWeek) {
		case Calendar.SUNDAY:
			if (hour <= 12) {
				weather = Model.SUN;
			} else {
				weather = Model.RAIN;
			}
			break;
		case Calendar.MONDAY:
			if (hour <= 12) {
				weather = Model.RAIN;
			} else {
				weather = Model.CLOUD;
			}
			break;
		case Calendar.TUESDAY:
			if (hour <= 8) {
				weather = Model.RAIN;
			} else {
				if (hour <= 15) {
					weather = Model.CLOUD;
				} else {
					weather = Model.SUN;
				}
			}
			break;
		case Calendar.WEDNESDAY:
			if (hour <= 12) {
				weather = Model.CLOUD;
			} else {
				weather = Model.SUN;
			}
			break;
		case Calendar.THURSDAY:
			if (hour <= 8) {
				weather = Model.RAIN;
			} else {
				if (hour <= 15) {
					weather = Model.CLOUD;
				} else {
					weather = Model.SUN;
				}
			}
			break;
		case Calendar.FRIDAY:
			if (hour <= 12) {
				weather = Model.CLOUD;
			} else {
				weather = Model.RAIN;
			}
			break;
		case Calendar.SATURDAY:
		default:
			if (hour <= 12) {
				weather = Model.RAIN;
			} else {
				weather = Model.CLOUD;
			}
			break;
		}
		return weather;
	}

	/**
	 * @param dayOfWeek
	 * @param hour
	 * @return
	 */
	public static int getHumidity(int dayOfWeek, int hour) {
		if (hour > HOUR_IN_HALF) {
			hour = HOUR_IN_HALF * 2 - hour;
		}
		float hourUsage = 1 - ((hour + 1) / (float) HOUR_IN_HALF);
		int humidity = MIN_HUMIDITY;
		switch (getType(dayOfWeek, hour)) {
		case Model.SUN:
			humidity += hourUsage * (DIFF_HUMIDITY/4);
			break;
		case Model.RAIN:
			humidity += (3 * DIFF_HUMIDITY) / 4 + hourUsage * (DIFF_HUMIDITY / 4);
			break;
		case Model.CLOUD:
		default:
			humidity += (2 * DIFF_HUMIDITY) / 4 + hourUsage * (DIFF_HUMIDITY / 4);
			break;
		}
		return humidity;
	}

	private static int getValue(int dayOfWeek, int hour, int diff, int min) {
		if (dayOfWeek > Calendar.WEDNESDAY - 1) {
			dayOfWeek = Calendar.SATURDAY - dayOfWeek;
		}
		float dayUsage = dayOfWeek / (float) Calendar.WEDNESDAY;
		if (hour > HOUR_IN_HALF) {
			hour = HOUR_IN_HALF * 2 - hour;
		}
		float hourUsage = ((hour + 1) / (float) (HOUR_IN_HALF * Calendar.WEDNESDAY));
		return (int) (min + (dayUsage + hourUsage) * diff);
	}
}
