/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo;

import java.text.DateFormatSymbols;

import ej.util.text.EnglishDateFormatSymbols;

/**
 * Support for the NLS management.
 */
public class NLS {

	private static final int MONTH_OFFSET = 5;
	private static final int YEAR_OFFSET = 4;
	private static final int HALF_DAY = 12;
	private static final char AM = 0x1;
	private static final char PM = 0x2;

	private static final char FAR = 0x5;

	private static int LASTUPDATE;
	private static String DATE;
	private static String DAY;

	private NLS() {
		// Forbid instantiation.
	}

	/**
	 * Gets the local symbols.
	 *
	 * @return the local symbols.
	 */
	public static DateFormatSymbols getLocalSymbols() {
		return EnglishDateFormatSymbols.getInstance();
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
		return DATE;
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
		return DAY;
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
		return " mph"; //$NON-NLS-1$
	}

	/**
	 * Gets the wind text.
	 *
	 * @return the wind text.
	 */
	public static String getWind() {
		return "Wind"; //$NON-NLS-1$
	}

	/**
	 * Gets the Sunrise text.
	 *
	 * @return the Sunrise text.
	 */
	public static String getSunrise() {
		return "Sunrise"; //$NON-NLS-1$
	}

	/**
	 * Gets the Humidity text.
	 *
	 * @return the Humidity text.
	 */
	public static String getHumidity() {
		return "Humidity"; //$NON-NLS-1$
	}

	/**
	 * Gets the Lat text.
	 *
	 * @return the Lat text.
	 */
	public static String getLat() {
		return "Lat"; //$NON-NLS-1$
	}

	/**
	 * Gets the Lon text.
	 *
	 * @return the Lon text.
	 */
	public static String getLon() {
		return "Lon"; //$NON-NLS-1$
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
		if (timeId != LASTUPDATE) {
			LASTUPDATE = timeId;
			DAY = getLocalSymbols().getWeekday(time.getDayOfWeek() - 1);
			DATE = addPadding(time.getMonth() + 1) + '.' + addPadding(time.getDay()) + '.' + time.getYear();
		}
	}

	private static String addPadding(int day) {
		String value = String.valueOf(day);
		return (value.length() == 1) ? "0" + value : value; //$NON-NLS-1$
	}

}
