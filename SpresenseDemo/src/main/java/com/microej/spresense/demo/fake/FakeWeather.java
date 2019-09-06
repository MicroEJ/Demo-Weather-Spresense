/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.fake;

import com.microej.spresense.demo.Time;

/**
 *
 */
public class FakeWeather {

	private final int type;
	private final int temperature;
	private final Time sunrise;
	private final int wind;
	private final int humidity;
	private final float latitude;
	private final float longitude;

	/**
	 * @param longitude
	 * @param latitude
	 * @param humidity
	 * @param wind
	 * @param sunrise
	 * @param temperature
	 * @param type
	 *
	 */
	public FakeWeather(int type, int temperature, Time sunrise, float wind, float humidity, float latitude,
			float longitude) {
		this.type = type;
		this.temperature = temperature;
		this.sunrise = sunrise;
		this.wind = Math.round(wind);
		this.humidity = Math.round(humidity);
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type.
	 */
	public int getType() {
		return type;
	}

	/**
	 * Gets the temperature.
	 *
	 * @return the temperature.
	 */
	public int getTemperature() {
		return temperature;
	}

	/**
	 * Gets the sunrise.
	 *
	 * @return the sunrise.
	 */
	public Time getSunrise() {
		return sunrise;
	}

	/**
	 * Gets the wind.
	 *
	 * @return the wind.
	 */
	public int getWind() {
		return wind;
	}

	/**
	 * Gets the humidity.
	 *
	 * @return the humidity.
	 */
	public int getHumidity() {
		return humidity;
	}

	/**
	 * Gets the latitude.
	 *
	 * @return the latitude.
	 */
	public float getLatitude() {
		return latitude;
	}

	/**
	 * Gets the longitude.
	 *
	 * @return the longitude.
	 */
	public float getLongitude() {
		return longitude;
	}

}
