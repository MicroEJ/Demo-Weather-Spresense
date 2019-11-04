/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.model;

import java.util.Observable;

/**
 * Represents the data model used by the application to retrieve the information to display.
 */
public abstract class Model extends Observable {

	/**
	 * Gets the current time as a {@link FastForwardTime} instance. Instances of this class can be used to make the time
	 * flowing faster than normally for demonstration purposes.
	 *
	 * @return the current time.
	 */
	public abstract FastForwardTime getTime();

	/**
	 * Gets the temperature.
	 *
	 * @return the temperature in degrees Fahrenheit..
	 */
	public abstract int getTemperature();

	/**
	 * Gets the temperature of a specific day and hour.
	 *
	 * @param day
	 *            the day.
	 * @param hour
	 *            the hour.
	 * @return the temperature in degrees Fahrenheit.
	 */
	public abstract int getTemperature(int day, int hour);

	/**
	 * Gets the wind speed in km/h.
	 *
	 * @return the wind speed.
	 */
	public abstract int getWindSpeed();

	/**
	 * Gets the humidity in percent.
	 *
	 * @return the humidity.
	 */
	public abstract int getHumidity();

	/**
	 * Gets the sunrise time.
	 *
	 * @return the sunrise time.
	 */
	public abstract Time getSunriseTime();

	/**
	 * Gets the latitude in degrees.
	 *
	 * @return the latitude, a default value if none found.
	 */
	public abstract float getLatitude();

	/**
	 * Gets the longitude in degrees.
	 *
	 * @return the longitude, a default value if none found.
	 */
	public abstract float getLongitude();

	/**
	 * Gets the weather as a value taken from {{@link Weather#CLOUD}, {@link Weather#SUN}, {@link Weather#RAIN}}.
	 *
	 * @return the weather.
	 */
	public abstract int getWeather();
}
