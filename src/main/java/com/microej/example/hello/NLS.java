/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello;

import java.text.DateFormatSymbols;

import ej.util.text.EnglishDateFormatSymbols;

/**
 *
 */
public class NLS {

	private static final int HALF_DAY = 12;
	private static final char AM = 0x1;
	private static final char PM = 0x2;
	private static final char H = 0x3;

	private static final char FAR = 0x5;
	private static final char CEL = 0x6;

	// private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM.dd.yyyy", getLocalSymbols());
	// //$NON-NLS-1$
	// private static final SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("hh", getLocalSymbols()); //$NON-NLS-1$
	// private static final SimpleDateFormat FULL_HOUR_FORMAT = new SimpleDateFormat("hh:mm", getLocalSymbols());
	// //$NON-NLS-1$

	private static int lastUpdate;
	private static String date;
	private static String day;

	private NLS() {
	}

	public static DateFormatSymbols getLocalSymbols() {
		return EnglishDateFormatSymbols.getInstance();
	}

	public static String getDate(Time time) {
		cache(time);
		return date;
	}

	public static String getDay(Time time) {
		cache(time);
		return day;
	}

	public static String getFullHourFormat(Time time) {
		return addPadding(get12Hour(time.getHour())) + ':' + addPadding(time.getMinute());
	}

	public static String getHourFormat(int hour) {
		return addPadding(get12Hour(hour));
	}

	public static char getTemperatureSymbol() {
		return FAR;
	}

	private static void cache(Time time) {
		int timeId = (((time.getYear() << 4) | time.getMonth()) << 5) | time.getDay();
		if (timeId != lastUpdate) {
			lastUpdate = timeId;
			day = getLocalSymbols().getWeekday(time.getDayOfWeek() - 1);
			date = addPadding(time.getDay()) + '.' + addPadding(time.getMonth() + 1) + '.' + time.getYear();
		}
	}

	private static String addPadding(int day) {
		String value = String.valueOf(day);
		return (value.length() == 1) ? "0" + value : value;
	}

	public static char getHourSymbol(int hour) {
		if (hour < 12) {
			return AM;
		}
		return PM;
	}

	public static String getSpeedSymbol() {
		return " mph"; //$NON-NLS-1$
	}

	public static String getWind() {
		return "Wind"; //$NON-NLS-1$
	}

	public static String getSunrise() {
		return "Sunrise"; //$NON-NLS-1$
	}

	public static String getHumidity() {
		return "Humidity"; //$NON-NLS-1$
	}

	public static String getLat() {
		return "Lat"; //$NON-NLS-1$
	}

	public static String getLon() {
		return "Lon"; //$NON-NLS-1$
	}

	private static int get12Hour(int hour) {
		if (hour > 12) {
			hour -= HALF_DAY;
		}
		if (hour == 0) {
			hour = 12;
		}
		return hour;
	}

}
