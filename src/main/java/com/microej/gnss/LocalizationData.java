/*
 * Java
 *
 * Copyright 2019 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microej.gnss;

/**
 * Localization Data
 */
public class LocalizationData {
	public enum CoordinateType {
		LAT, LONG, INVALID;
	}
	private CoordinateType type;


	/**
	 * Constructor
	 *
	 * @param None
	 */
	public LocalizationData() {
		type = CoordinateType.INVALID;
	}

	/**
	 * Return the type of the localization
	 *
	 * @param None
	 */
	public CoordinateType getType() {
		return type;
	}

	/**
	 * Return the sign of the localization
	 *
	 * @param None
	 */
	public int getLocalizationSign() {
		return position_sign;
	}

	/**
	 * Return the Degree of the localization
	 *
	 * @param None
	 */
	public int getLocalizationDegree() {
		return postion_degree;
	}

	/**
	 * Return the Minutes of the localization
	 *
	 * @param None
	 */
	public int getLocalizationMinute() {
		return position_minute;
	}

	/**
	 * Return the Fraction of the localization
	 *
	 * @param None
	 */
	public int getLocalizationFrac() {
		return position_frac;
	}

	/**
	 * Return the Raw data (raw latitude or raw longitude) of the localization
	 *
	 * @param None
	 */
	public double getRawLocalizationData() {
		return position_raw_data;
	}

	/**
	 * Return the hours of time data from the NMEA format
	 *
	 * @param None
	 */
	public static int getTimeHours() {
		return time_hours;
	}

	/**
	 * Return the minutes of time data from the NMEA format
	 *
	 * @param None
	 */
	public static int getTimeMinutes() {
		return time_minutes;
	}

	/**
	 * Return the seconds of time data from the NMEA format
	 *
	 * @param None
	 */
	public static int getTimeSeconds() {
		return time_seconds;
	}

	/**
	 * Return the microseconds of time data from the NMEA format
	 *
	 * @param None
	 */
	public static int getTimeMicroSeconds() {
		return time_microseconds;
	}

	/**
	 * Set localization data
	 *
	 * @param rawPositionData
	 * 			a double representing raw data
	 *
	 * @param coordinateType
	 * 			Type of the data (Latitude, Longitude, Invalid )
	 */
	public void setPositioningData(double rawPositionData, CoordinateType coordinateType) {
		position_raw_data = rawPositionData;
		type = coordinateType;
		convertDoubleCoordinateToDmf(rawPositionData);
	}
	/**
	 * Set the static timing data
	 *
	 * @param int hours
	 * 				hours data to set
	 * @param int minutes
	 * 				minutes data to set
	 * @param int seconds
	 * 				seconds data to set
	 * @param int microseconds
	 * 				microseconds data to set
	 *
	 */
	public static void setTimingData(int hours, int minutes, int seconds, int microseconds) {
		time_hours = hours;
		time_minutes = minutes;
		time_seconds = seconds;
		time_microseconds = microseconds;
	}
	/**
	 * Convert raw position data to degree minute fraction format
	 *
	 * @param x
	 * 				Raw position data
	 */
	private void convertDoubleCoordinateToDmf(double x) {
		int    b;
		int    d;
		int    m;
		double f;
		double t;

		if (x < 0)
		{
			b = 1;
			x = -x;
		}
		else
		{
			b = 0;
		}
		d = (int)x; /* = floor(x), x is always positive */
		t = (x - d) * 60;
		m = (int)t; /* = floor(t), t is always positive */
		f = (t - m) * 10000;

		position_sign   = b;
		postion_degree = d;
		position_minute = m;
		position_frac   = (int)f;
	}

	private static int time_hours;
	private static int time_minutes;
	private static int time_seconds;
	private static int time_microseconds;

	private int position_sign;
	private int postion_degree;
	private int position_minute;
	private int position_frac;
	private double position_raw_data;

}
