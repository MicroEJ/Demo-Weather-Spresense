/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.fake;

import java.util.Calendar;

import com.microej.spresense.demo.Time;
import com.microej.spresense.demo.Weather;

/**
 * A data provider generating its data.
 */
public class FakeDataProvider {

	private static final int HUMIDITY_SPREAD = 4;
	private static final int THIRD_DAY = 8;
	private static final int HALF_DAY = 12;
	private static final int SUNRISE_HOUR_BASE = 5;
	private static final int MIN_IN_HOUR = 60;
	private static final int HOUR_IN_HALF = 12;
	private static final int MIN_TEMPERATURE = 52;
	private static final int DIFF_TEMPERATURE = 75 - MIN_TEMPERATURE;

	private static final int MIN_WIND = 1;
	private static final int DIFF_WIND = 65 - MIN_WIND;

	private static final int MIN_SUNRISE = 48;
	private static final int DIFF_SUNRISE = 108 - MIN_SUNRISE;

	private static final int MIN_HUMIDITY = 5;
	private static final int DIFF_HUMIDITY = 95 - MIN_HUMIDITY;

	private FakeDataProvider() {
	}

	/**
	 * Gets the temperature.
	 *
	 * @param dayOfWeek
	 *            the day to get the temperature from.
	 * @param hour
	 *            the hour to get the temperature from.
	 * @return the temperature.
	 */
	public static int getTemperature(int dayOfWeek, int hour) {
		return getValue(dayOfWeek, hour, DIFF_TEMPERATURE, MIN_TEMPERATURE);
	}

	/**
	 * Gets the wind.
	 *
	 * @param dayOfWeek
	 *            the day to get the wind from.
	 * @param hour
	 *            the hour to get the wind from.
	 * @return the wind.
	 */
	public static int getWind(int dayOfWeek, int hour) {
		return getValue(dayOfWeek, hour, DIFF_WIND, MIN_WIND);
	}


	/**
	 * Gets the sunrise time.
	 *
	 * @param dayOfWeek
	 *            the day to get the sunrise time from.
	 * @return the sunrise time.
	 */
	public static Time getSunrise(int dayOfWeek) {
		if (dayOfWeek > Calendar.WEDNESDAY) {
			dayOfWeek = Calendar.SATURDAY - dayOfWeek;
		}
		float dayUsage = dayOfWeek / (float) Calendar.WEDNESDAY;
		int sunHour = SUNRISE_HOUR_BASE;
		int sunMin = (int) (MIN_SUNRISE - dayUsage * DIFF_SUNRISE);
		while (sunMin >= MIN_IN_HOUR) {
			sunHour++;
			sunMin -= MIN_IN_HOUR;
		}
		return new Time(0, 0, dayOfWeek, sunHour, Math.abs(sunMin));
	}

	/**
	 * Gets the weather type.
	 *
	 * @param dayOfWeek
	 *            the day to get the weather type from.
	 * @param hour
	 *            the hour to get the weather type from.
	 * @return the weather type.
	 */
	public static int getType(int dayOfWeek, int hour) {
		int weather;
		switch (dayOfWeek) {
		case Calendar.SUNDAY:
			if (hour <= HALF_DAY) {
				weather = Weather.SUN;
			} else {
				weather = Weather.RAIN;
			}
			break;
		case Calendar.MONDAY:
			if (hour <= HALF_DAY) {
				weather = Weather.RAIN;
			} else {
				weather = Weather.CLOUD;
			}
			break;
		case Calendar.TUESDAY:
			if (hour <= THIRD_DAY) {
				weather = Weather.RAIN;
			} else {
				if (hour <= THIRD_DAY * 2) {
					weather = Weather.CLOUD;
				} else {
					weather = Weather.SUN;
				}
			}
			break;
		case Calendar.WEDNESDAY:
			if (hour <= HALF_DAY) {
				weather = Weather.CLOUD;
			} else {
				weather = Weather.SUN;
			}
			break;
		case Calendar.THURSDAY:
			if (hour <= THIRD_DAY) {
				weather = Weather.RAIN;
			} else {
				if (hour < THIRD_DAY * 2) {
					weather = Weather.CLOUD;
				} else {
					weather = Weather.SUN;
				}
			}
			break;
		case Calendar.FRIDAY:
			if (hour <= HALF_DAY) {
				weather = Weather.CLOUD;
			} else {
				weather = Weather.RAIN;
			}
			break;
		case Calendar.SATURDAY:
		default:
			if (hour <= HALF_DAY) {
				weather = Weather.RAIN;
			} else {
				weather = Weather.CLOUD;
			}
			break;
		}
		return weather;
	}

	/**
	 * Gets the humidity.
	 *
	 * @param dayOfWeek
	 *            the day to get the humidity from.
	 * @param hour
	 *            the hour to get the humidity from.
	 * @return the humidity.
	 */
	public static int getHumidity(int dayOfWeek, int hour) {
		if (hour > HOUR_IN_HALF) {
			hour = HOUR_IN_HALF * 2 - hour;
		}
		float hourUsage = 1 - ((hour + 1) / (float) HOUR_IN_HALF);
		int humidity = MIN_HUMIDITY;
		switch (getType(dayOfWeek, hour)) {
		case Weather.SUN:
			humidity += hourUsage * (DIFF_HUMIDITY / HUMIDITY_SPREAD);
			break;
		case Weather.RAIN:
			humidity += (3 * DIFF_HUMIDITY) / HUMIDITY_SPREAD + hourUsage * (DIFF_HUMIDITY / HUMIDITY_SPREAD);
			break;
		case Weather.CLOUD:
		default:
			humidity += (2 * DIFF_HUMIDITY) / HUMIDITY_SPREAD + hourUsage * (DIFF_HUMIDITY / HUMIDITY_SPREAD);
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
