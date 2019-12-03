/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.model;

/**
 * Represents a time with minute-precision.
 */
public class Time {

	/**
	 * A field representing the year.
	 */
	protected int year;

	/**
	 * A field representing the month.
	 */
	protected int month;

	/**
	 * A field representing the day.
	 */
	protected int day;

	/**
	 * A field representing the minute.
	 */
	protected int minute;

	/**
	 * A field representing the hour.
	 */
	protected int hour;

	/**
	 * A field representing the day of week.
	 */
	protected int dayOfWeek;

	/**
	 * Constructs an instance of this class given the main date fields.
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
	 * Gets the hour.
	 *
	 * @return the hour.
	 */
	public int getHour() {
		return this.hour;
	}

	/**
	 * Gets the minute.
	 *
	 * @return the minute.
	 */
	public int getMinute() {
		return this.minute;
	}

	/**
	 * Gets the day.
	 *
	 * @return the day.
	 */
	public int getDay() {
		return this.day;
	}

	/**
	 * Gets the month.
	 *
	 * @return the month.
	 */
	public int getMonth() {
		return this.month;
	}

	/**
	 * Gets the year.
	 *
	 * @return the year.
	 */
	public int getYear() {
		return this.year;
	}

	/**
	 * Gets the day of week.
	 *
	 * @return the day of week.
	 */
	public int getDayOfWeek() {
		return this.dayOfWeek;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.day;
		result = prime * result + this.dayOfWeek;
		result = prime * result + this.hour;
		result = prime * result + this.minute;
		result = prime * result + this.month;
		result = prime * result + this.year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Time other = (Time) obj;
		if (this.day != other.day) {
			return false;
		}
		if (this.dayOfWeek != other.dayOfWeek) {
			return false;
		}
		if (this.hour != other.hour) {
			return false;
		}
		if (this.minute != other.minute) {
			return false;
		}
		if (this.month != other.month) {
			return false;
		}
		if (this.year != other.year) {
			return false;
		}
		return true;
	}

}
