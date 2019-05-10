/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.microej.example.hello.style.Colors;

import ej.bon.Util;
import ej.util.text.EnglishDateFormatSymbols;

/**
 *
 */
public class Model {

	private static final char AM = 0x1;
	private static final char PM = 0x2;
	private static final char H = 0x3;

	private static final char FAR = 0x5;
	private static final char CEL = 0x6;

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM.dd.yyyy", getLocalSymbols());
	private static final SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("hh", getLocalSymbols());
	private static final SimpleDateFormat FULL_HOUR_FORMAT = new SimpleDateFormat("hh:mm", getLocalSymbols());

	private static long currentTime = Util.currentTimeMillis();

	private static long offset = 0;

	private static final long SPEED = 59 * 50;

	public static long getCurrentTime() {
		long currentTimeMillis = Util.currentTimeMillis();
		offset += (currentTimeMillis - currentTime) * SPEED;
		currentTime = currentTimeMillis;
		return currentTime + offset;
	}

	public static int getTemperature() {
		return 72;
	}

	public static int getCurrentColor() {
		return Colors.CORAL;
	}

	public static DateFormatSymbols getLocalSymbols() {
		return EnglishDateFormatSymbols.getInstance();
	}

	public static DateFormat getDateFormat() {
		return DATE_FORMAT;
	}

	public static DateFormat getFullHourFormat() {
		return FULL_HOUR_FORMAT;
	}

	public static DateFormat getHourFormat() {
		return HOUR_FORMAT;
	}

	public static char getTemperatureSymbol() {
		return FAR;
	}

	public static char getHourSymbol(int AM_PM) {
		if (AM_PM == Calendar.AM) {
			return AM;
		}
		return PM;
	}

}
