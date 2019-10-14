/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo;

import java.util.Calendar;

/**
 * A time.
 */
public class Time {
	private static final int MILLIS = 1000;
	private static final int MILLI_IN_MINUTES = 60 * MILLIS;
	private static final int MILLI_IN_HOUR = 60 * MILLI_IN_MINUTES;
	private static final long THRESHOLD = MILLI_IN_HOUR * 24;

	private static final long SPEED = 60 * 60;

	private final Calendar calendar = Calendar.getInstance();
	private int year;
	private int month;
	private int day;
	private int minute;
	private int hour;
	private long currentTime = -1;
	private long lastUpdate = THRESHOLD + 1;
	private long offset = 0;
	private int dayOfWeek;

	/**
	 * Instantiates a time.
	 *
	 * @param year
	 *            the current year.
	 * @param month
	 *            the current month.
	 * @param day
	 *            the current day.
	 * @param hour
	 *            the current hour.
	 * @param minute
	 *            the current minute.
	 */
	public Time(int year, int month, int day, int hour, int minute) {
		this.month = month;
		this.year = year;
		this.day = day;
		this.minute = minute;
		this.hour = hour;
	}

	/**
	 * Update the time using its overspeed.
	 */
	public void updateCurrentTime() {
		long currentTimeMillis = System.currentTimeMillis();
		if (currentTime == -1) {
			currentTime = currentTimeMillis;
			offset = 0;
		}
		long elapsed = (currentTimeMillis - currentTime) * SPEED;
		currentTime = currentTimeMillis;
		offset += elapsed;
		lastUpdate += elapsed;
		if (lastUpdate > THRESHOLD) {
			long newTime = currentTime + offset;
			calendar.setTimeInMillis(newTime);
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH);
			day = calendar.get(Calendar.DAY_OF_MONTH);
			dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			hour = calendar.get(Calendar.HOUR_OF_DAY);
			minute = calendar.get(Calendar.MINUTE);
			lastUpdate = hour * MILLI_IN_HOUR + minute * MILLI_IN_MINUTES + calendar.get(Calendar.SECOND) * MILLIS
					+ newTime % MILLIS;
		} else {
			hour = (int) (lastUpdate / MILLI_IN_HOUR);
			minute = (int) ((lastUpdate % MILLI_IN_HOUR) / MILLI_IN_MINUTES);
		}
	}

	/**
	 * Gets the hour.
	 * 
	 * @return the hour.
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * Gets the minute.
	 * 
	 * @return the minute.
	 */
	public int getMinute() {
		return minute;
	}

	/**
	 * Gets the day.
	 * 
	 * @return the day.
	 */
	public int getDay() {
		return day;
	}

	/**
	 * Gets the month.
	 * 
	 * @return the month.
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * Gets the year.
	 * 
	 * @return the year.
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Gets the day of week.
	 * 
	 * @return the day of week.
	 */
	public int getDayOfWeek() {
		return dayOfWeek;
	}

}
