/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.model;

import java.io.IOException;
import java.util.logging.Level;

import com.microej.spresense.demo.SpresenseDemo;
import com.microej.spresense.demo.fake.FakeDataProvider;
import com.microej.spresense.demo.style.Colors;

import ej.color.GradientHelper;
import ej.gnss.GnssManager;

/**
 * Represents a implementation of type {@link Model} for this demo.
 *
 * <p>
 * New weather data is generated pseudo-randomly every 2 seconds and the GNSS position is updated every 10 seconds.
 */
public class DemoModel extends Model {

	private static final int START_OF_DAWN = 20;
	private static final int START_OF_DAY = 16;
	private static final int GNSS_POLLING_RATE = 10_000;
	private static final long DATA_POLLING_RATE = 2_000;
	private static final int HOUR_IN_DAY = 24;
	private static final int DAY_IN_WEEK = 7;
	private static final int MIN_IN_HOUR = 60;

	private static final float DEFAULT_LATITUDE = 35.628f;
	private static final float DEFAULT_LONGITUDE = 139.74f;

	private int temperature;
	private int wind;
	private int humidity;
	private Time sunrise;
	private float latitude;
	private float longitude;
	private int weather;
	private Thread pollingThread;
	private Thread gnssThread;
	private final FastForwardTime time;

	/**
	 * Constructs a model that will provide pseudo-random data for demonstration purposes.
	 */
	public DemoModel() {
		this.time = new FastForwardTime();
		this.gnssThread = new Thread(new Runnable() {
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

		this.pollingThread = new Thread(new Runnable() {

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
		start();
	}

	@Override
	public FastForwardTime getTime() {
		return this.time;
	}

	@Override
	public int getTemperature() {
		return this.temperature;
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
		return GradientHelper.blendColors(Colors.WEEK[colorPosition], Colors.WEEK[nextColorPosition], fullFilment);
	}

	@Override
	public int getTemperature(int day, int hour) {
		return FakeDataProvider.getTemperature(day, hour);
	}

	@Override
	public int getWindSpeed() {
		return this.wind;
	}

	private void setWindSpeed(int wind) {
		if (wind != this.wind) {
			this.wind = wind;
			setChanged();
		}
	}

	@Override
	public int getHumidity() {
		return this.humidity;
	}

	private void setHumidity(int humidity) {
		if (humidity != this.humidity) {
			this.humidity = humidity;
			setChanged();
		}
	}

	@Override
	public Time getSunriseTime() {
		return this.sunrise;
	}

	private void setSunriseTime(Time sunrise) {
		if (!sunrise.equals(this.sunrise)) {
			this.sunrise = sunrise;
			setChanged();
		}
	}

	@Override
	public float getLatitude() {
		return this.latitude;
	}

	private void setLatitude(float latitude) {
		if (latitude != this.latitude) {
			this.latitude = latitude;
			setChanged();
		}
	}

	@Override
	public float getLongitude() {
		return this.longitude;
	}

	private void setLongitude(float longitude) {
		if (longitude != this.longitude) {
			this.longitude = longitude;
			setChanged();
		}
	}

	@Override
	public int getWeather() {
		return this.weather;
	}

	private void setWeather(int weather) {
		if (weather != this.weather) {
			this.weather = weather;
			setChanged();
		}
	}

	private void start() {
		this.gnssThread.start();
		this.pollingThread.start();
	}

	private void updateValues() {
		setTemperature(FakeDataProvider.getTemperature(this.time.getDayOfWeek(), this.time.getHour()));
		setWindSpeed(FakeDataProvider.getWind(this.time.getDayOfWeek(), this.time.getHour()));
		setHumidity(FakeDataProvider.getHumidity(this.time.getDayOfWeek(), this.time.getHour()));
		setSunriseTime(FakeDataProvider.getSunrise(this.time.getDayOfWeek()));
		GnssManager gnssManager = ej.gnss.GnssManager.getInstance();
		setLatitude((gnssManager == null) ? DEFAULT_LATITUDE : gnssManager.getLatitude());
		setLongitude((gnssManager == null) ? DEFAULT_LONGITUDE : gnssManager.getLongitude());
		setWeather(FakeDataProvider.getType(this.time.getDayOfWeek(), this.time.getHour()));
	}
}
