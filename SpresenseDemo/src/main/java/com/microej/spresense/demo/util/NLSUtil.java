/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.util;

import com.microej.spresense.demo.generated.nls.Spresense;
import com.microej.spresense.demo.model.Time;

/**
 * Support for the NLS management.
 */
public class NLSUtil {

	private static final int MONTH_OFFSET = 5;
	private static final int YEAR_OFFSET = 4;
	private static final int HALF_DAY = 12;
	private static final char AM = 0x1;
	private static final char PM = 0x2;

	private static final char FAR = 0x5;

	private static int lastUpdate;
	private static String date;
	private static String day;
	private static String[] weekdays;
	private static String weekdayLocale;

	private NLSUtil() {
		// Forbid instantiation.
	}

	/**
	 * Gets the String value of a time for the current locale.
	 *
	 * @param time
	 *            the time.
	 * @return the time printed for the current locale.
	 */
	public static String getDate(Time time) {
		cache(time);
		return date;
	}

	/**
	 * Gets the day name of a time for the current locale.
	 *
	 * @param time
	 *            the time.
	 * @return the day name.
	 */
	public static String getDay(Time time) {
		cache(time);
		return day;
	}

	/**
	 * Gets the full hour as a string.
	 *
	 * @param time
	 *            the time to get the hour from.
	 * @return the full hour as a string.
	 */
	public static String getFullHourFormat(Time time) {
		return addPadding(get12Hour(time.getHour())) + ':' + addPadding(time.getMinute());
	}

	/**
	 * Gets the hour as a string.
	 *
	 * @param hour
	 *            the hour to get the String value.
	 * @return the hour as a string.
	 */
	public static String getHourFormat(int hour) {
		return addPadding(get12Hour(hour));
	}

	/**
	 * Gets the temperature picto value.
	 *
	 * @return the temperature picto value.
	 */
	public static char getTemperatureSymbol() {
		return FAR;
	}

	/**
	 * Gets the hour symbol picto.
	 *
	 * @param hour
	 *            the hour.
	 * @return the picto for the current hour.
	 */
	public static char getHourSymbol(int hour) {
		if (hour < HALF_DAY) {
			return AM;
		}
		return PM;
	}

	/**
	 * Gets the speed text.
	 *
	 * @return the speed text.
	 */
	public static String getSpeedSymbol() {
		return Spresense.NLS.getMessage(Spresense.Mph);
	}

	/**
	 * Gets the wind text.
	 *
	 * @return the wind text.
	 */
	public static String getWind() {
		return Spresense.NLS.getMessage(Spresense.Wind);
	}

	/**
	 * Gets the Sunrise text.
	 *
	 * @return the Sunrise text.
	 */
	public static String getSunrise() {
		return Spresense.NLS.getMessage(Spresense.Sunrise);
	}

	/**
	 * Gets the Humidity text.
	 *
	 * @return the Humidity text.
	 */
	public static String getHumidity() {
		return Spresense.NLS.getMessage(Spresense.Humidity);
	}

	/**
	 * Gets the Lat text.
	 *
	 * @return the Lat text.
	 */
	public static String getLat() {
		return Spresense.NLS.getMessage(Spresense.Lat);
	}

	/**
	 * Gets the Lon text.
	 *
	 * @return the Lon text.
	 */
	public static String getLon() {
		return Spresense.NLS.getMessage(Spresense.Lon);
	}

	private static int get12Hour(int hour) {
		if (hour > HALF_DAY) {
			hour -= HALF_DAY;
		}
		if (hour == 0) {
			hour = HALF_DAY;
		}
		return hour;
	}

	private static void cache(Time time) {
		int timeId = (((time.getYear() << YEAR_OFFSET) | time.getMonth()) << MONTH_OFFSET) | time.getDay();
		if (timeId != lastUpdate) {
			lastUpdate = timeId;
			initWeekDays();
			day = weekdays[time.getDayOfWeek() - 1];
			date = addPadding(time.getMonth() + 1) + '.' + addPadding(time.getDay()) + '.' + time.getYear();
		}
	}

	private static String addPadding(int day) {
		String value = String.valueOf(day);
		return (value.length() == 1) ? '0' + value : value;
	}

	/**
	 * Gets the weekdays.
	 *
	 * @return the weekdays.
	 */
	public static String[] getWeekdays() {
		initWeekDays();
		return weekdays.clone();
	}

	private static void initWeekDays() {
		if (weekdays == null || !Spresense.NLS.getCurrentLocale().equals(weekdayLocale)) {
			weekdays = new String[] { Spresense.NLS.getMessage(Spresense.Sunday),
					Spresense.NLS.getMessage(Spresense.Monday), Spresense.NLS.getMessage(Spresense.Tuesday),
					Spresense.NLS.getMessage(Spresense.Wednesday), Spresense.NLS.getMessage(Spresense.Thursday),
					Spresense.NLS.getMessage(Spresense.Friday), Spresense.NLS.getMessage(Spresense.Saturday) };
		}
	}
}
