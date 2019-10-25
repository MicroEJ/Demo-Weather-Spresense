/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.model;

import java.io.IOException;
import java.util.Observable;
import java.util.logging.Level;

import com.microej.spresense.demo.SpresenseDemo;
import com.microej.spresense.demo.fake.FakeDataProvider;
import com.microej.spresense.demo.style.Colors;

import ej.color.GradientHelper;
import ej.gnss.GnssManager;

/**
 * Model gathering the data of the demo.
 */
public class Model extends Observable {

	private static final int START_OF_DAWN = 20;
	private static final int START_OF_DAY = 16;
	private static final int GNSS_POLLING_RATE = 10_000;
	private static final long DATA_POLLING_RATE = 2_000;
	private static final int HOUR_IN_DAY = 24;
	private static final int DAY_IN_WEEK = 7;
	private static final int MIN_IN_HOUR = 60;

	private static final float DEFAULT_LATITUDE = 35.628f;
	private static final float DEFAULT_LONGITUDE = 139.74f;
	/**
	 * Time of the machine.
	 */
	private final Time time = new Time(0, 0, 0, 0, 0);
	private static GnssManager gnssManager;

	private static final Model INSTANCE;

	static {
		INSTANCE = new Model();
		INSTANCE.start();
	}

	private int temperature;
	private int wind;
	private int humidity;
	private Time sunrise;
	private float latitude;
	private float longitude;
	private int weather;
	private Thread pollingThread;
	private Thread gnssThread;

	private Model() {
		gnssThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Thread.currentThread().setName("GNSS poller"); //$NON-NLS-1$
				GnssManager gnssManager = ej.gnss.GnssManager.getInstance();
				try {
					gnssManager.switchOn();
				} catch (IOException e1) {
					SpresenseDemo.LOGGER.log(Level.SEVERE, "Could not switch on the GNSS.", e1); //$NON-NLS-1$
					return;
				}
				boolean run = true;
				while (run) {
					try {
						gnssManager.readPosition();
						Model.gnssManager = gnssManager;
					} catch (IOException e) {
						SpresenseDemo.LOGGER.log(Level.WARNING, "Could not read position.", e); //$NON-NLS-1$
					}
					try {
						Thread.sleep(GNSS_POLLING_RATE);
					} catch (InterruptedException e) {
						SpresenseDemo.LOGGER.log(Level.FINER, e.getMessage(), e);
						run = false;
					}
				}
			}
		});

		pollingThread = new Thread(new Runnable() {

			@Override
			public void run() {
				boolean run = true;
				while (run) {
					updateValues();
					notifyObservers();
					try {
						Thread.sleep(DATA_POLLING_RATE);
					} catch (InterruptedException e) {
						SpresenseDemo.LOGGER.log(Level.FINER, e.getMessage(), e);
						run = false;
					}
				}
			}
		});
		updateValues();
	}


	/**
	 * Gets the iNSTANCE.
	 *
	 * @return the iNSTANCE.
	 */
	public static Model getInstance() {
		return INSTANCE;
	}

	/**
	 * Gets the time.
	 *
	 * @return the time.
	 */
	public Time getTime() {
		return time;
	}


	/**
	 * Gets the temperature.
	 *
	 * @return the temperature.
	 */
	public int getTemperature() {
		return temperature;
	}

	private void setTemperature(int temperature) {
		if (temperature != this.temperature) {
			this.temperature = temperature;
			setChanged();
		}
	}

	/**
	 * Gets the color of a particular time.
	 *
	 * @param time
	 *            the time.
	 * @return the color.
	 */
	public static int getColor(Time time) {
		int dayOfWeek = time.getDayOfWeek();
		int hour = time.getHour();
		int minute = time.getMinute();
		int colorPosition = (dayOfWeek - 1) << 2;

		int colorOffset;
		int base = Colors.COLOR_COUNT_PER_DAY;
		if (hour < START_OF_DAY) {
			colorOffset = hour >> 2;
		} else if (hour < START_OF_DAWN) {
			colorOffset = 2;
		} else {
			colorOffset = ((HOUR_IN_DAY - 1 - hour) >> 1);
			base /= 2;
		}

		colorPosition += colorOffset;
		int nextColorPosition;
		if (hour < HOUR_IN_DAY / 2) {
			nextColorPosition = (colorPosition + 1) % Colors.WEEK.length;
		} else if (colorOffset != 0) {
			nextColorPosition = (colorPosition - 1);
		} else {
			nextColorPosition = (dayOfWeek % DAY_IN_WEEK) * Colors.COLOR_COUNT_PER_DAY;
		}

		float fullFilment = ((hour % base) * MIN_IN_HOUR + minute) / (base * (float) MIN_IN_HOUR);
		return GradientHelper.blendColors(Colors.WEEK[colorPosition],
				Colors.WEEK[nextColorPosition], fullFilment);
	}

	/**
	 * Gets the temperature of a specific day and hour.
	 *
	 * @param day
	 *            the day.
	 * @param hour
	 *            the hour.
	 * @return the temperature in Fahrenheit.
	 */
	public int getTemperature(int day, int hour) {
		return FakeDataProvider.getTemperature(day, hour);
	}

	/**
	 * Gets the wind in KM/H.
	 *
	 * @return the wind.
	 */
	public int getWind() {
		return wind;
	}

	private void setWind(int wind) {
		if (wind != this.wind) {
			this.wind = wind;
			setChanged();
		}
	}

	/**
	 * gets the humidity in percent.
	 *
	 * @return the humidity.
	 */
	public int getHumidity() {
		return wind;
	}

	private void setHumidity(int humidity) {
		if (humidity != this.humidity) {
			this.humidity = humidity;
			setChanged();
		}
	}

	/**
	 * Gets the sunrise time.
	 *
	 * @return the sunrise time.
	 */
	public Time getSunrise() {
		return sunrise;
	}

	private void setSunrise(Time sunrise) {
		if (!sunrise.equals(this.sunrise)) {
			this.sunrise = sunrise;
			setChanged();
		}
	}

	/**
	 * Gets the latitude.
	 *
	 * @return the latitude, a default value if none found.
	 */
	public float getLatitude() {
		return latitude;
	}

	private void setLatitude(float latitude) {
		if (latitude != this.latitude) {
			this.latitude = latitude;
			setChanged();
		}
	}

	/**
	 * Gets the longitude.
	 *
	 * @return the longitude, a default value if none found.
	 */
	public float getLongitude() {
		return longitude;
	}

	private void setLongitude(float longitude) {
		if (longitude != this.longitude) {
			this.longitude = longitude;
			setChanged();
		}
	}

	/**
	 * Gets the weather.
	 *
	 * @return the weather.
	 */
	public int getWeather() {
		return weather;
	}

	private void start() {
		gnssThread.start();
		pollingThread.start();
	}

	private void setWeather(int weather) {
		if (weather != this.weather) {
			this.weather = weather;
			setChanged();
		}
	}

	private void updateValues() {
		setTemperature(FakeDataProvider.getTemperature(time.getDayOfWeek(), time.getHour()));
		setWind(FakeDataProvider.getWind(time.getDayOfWeek(), time.getHour()));
		setHumidity(FakeDataProvider.getHumidity(time.getDayOfWeek(), time.getHour()));
		setSunrise(FakeDataProvider.getSunrise(time.getDayOfWeek()));
		setLatitude((gnssManager == null) ? DEFAULT_LATITUDE : gnssManager.getLatitude());
		setLongitude((gnssManager == null) ? DEFAULT_LONGITUDE : gnssManager.getLongitude());
		setWeather(FakeDataProvider.getType(time.getDayOfWeek(), time.getHour()));
	}
}
