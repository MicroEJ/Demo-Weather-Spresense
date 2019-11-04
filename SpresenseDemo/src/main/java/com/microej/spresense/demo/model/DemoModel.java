/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.model;

import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;

import com.microej.spresense.demo.SpresenseDemo;

import ej.gnss.GnssManager;

/**
 * Represents a implementation of type {@link Model} for this demo.
 *
 * <p>
 * New weather data is generated pseudo-randomly every 2 seconds and the GNSS position is updated every 10 seconds.
 */
public class DemoModel extends Model {

	private static final int GNSS_POLLING_RATE = 10_000;
	private static final long DATA_POLLING_RATE = 2_000;

	private static final float DEFAULT_LATITUDE = 35.628f;
	private static final float DEFAULT_LONGITUDE = 139.74f;

	private static final int HUMIDITY_SPREAD = 4;
	private static final int THIRD_DAY = 8;
	private static final int HALF_DAY = 12;
	private static final int SUNRISE_HOUR_BASE = 5;
	private static final int MIN_IN_HOUR = 60;
	private static final int HOUR_IN_HALF = 12;
	private static final int MIN_TEMPERATURE = 52;
	private static final int DIFF_TEMPERATURE = 75 - MIN_TEMPERATURE;

	private static final int MIN_WIND = 1;
	private static final int DIFF_WIND = 65 - MIN_WIND;

	private static final int MIN_SUNRISE = 48;
	private static final int DIFF_SUNRISE = 108 - MIN_SUNRISE;

	private static final int MIN_HUMIDITY = 5;
	private static final int DIFF_HUMIDITY = 95 - MIN_HUMIDITY;

	private int temperature;
	private int windSpeed;
	private int humidity;
	private Time sunriseTime;
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

	@Override
	public int getTemperature(int day, int hour) {
		return generateValue(day, hour, DIFF_TEMPERATURE, MIN_TEMPERATURE);
	}

	private void updateTemperature() {
		int newTemperature = generateValue(this.time.getDayOfWeek(), this.time.getHour(), DIFF_TEMPERATURE,
				MIN_TEMPERATURE);
		if (newTemperature != this.temperature) {
			this.temperature = newTemperature;
			setChanged();
		}
	}

	@Override
	public int getWindSpeed() {
		return this.windSpeed;
	}

	private void updateWindSpeed() {
		int newWindSpeed = generateValue(this.time.getDayOfWeek(), this.time.getHour(), DIFF_WIND, MIN_WIND);
		if (newWindSpeed != this.windSpeed) {
			this.windSpeed = newWindSpeed;
			setChanged();
		}
	}

	@Override
	public int getHumidity() {
		return this.humidity;
	}

	private void updateHumidity() {
		int newHumidity = generateHumidity(this.time.getDayOfWeek(), this.time.getHour());
		if (newHumidity != this.humidity) {
			this.humidity = newHumidity;
			setChanged();
		}
	}

	@Override
	public Time getSunriseTime() {
		return this.sunriseTime;
	}

	private void updateSunriseTime() {
		Time newSunriseTime = generateSunriseTime(this.time.getDayOfWeek());
		if (!newSunriseTime.equals(this.sunriseTime)) {
			this.sunriseTime = newSunriseTime;
			setChanged();
		}
	}

	@Override
	public float getLatitude() {
		return this.latitude;
	}

	private void updateLatitude(float latitude) {
		if (latitude != this.latitude) {
			this.latitude = latitude;
			setChanged();
		}
	}

	@Override
	public float getLongitude() {
		return this.longitude;
	}

	private void updateLongitude(float longitude) {
		if (longitude != this.longitude) {
			this.longitude = longitude;
			setChanged();
		}
	}

	@Override
	public int getWeather() {
		return this.weather;
	}

	private void updateWeather() {
		int newWeather = generateType(this.time.getDayOfWeek(), this.time.getHour());
		if (newWeather != this.weather) {
			this.weather = newWeather;
			setChanged();
		}
	}

	private void start() {
		this.gnssThread.start();
		this.pollingThread.start();
	}

	private void updateValues() {
		updateTemperature();
		updateWindSpeed();
		updateHumidity();
		updateSunriseTime();
		updateWeather();

		GnssManager gnssManager = ej.gnss.GnssManager.getInstance();
		updateLatitude((gnssManager == null) ? DEFAULT_LATITUDE : gnssManager.getLatitude());
		updateLongitude((gnssManager == null) ? DEFAULT_LONGITUDE : gnssManager.getLongitude());
	}

	private int generateValue(int dayOfWeek, int hour, int diff, int min) {
		if (dayOfWeek > Calendar.WEDNESDAY - 1) {
			dayOfWeek = Calendar.SATURDAY - dayOfWeek;
		}
		float dayUsage = dayOfWeek / (float) Calendar.WEDNESDAY;
		if (hour > HOUR_IN_HALF) {
			hour = HOUR_IN_HALF * 2 - hour;
		}
		float hourUsage = ((hour + 1) / (float) (HOUR_IN_HALF * Calendar.WEDNESDAY));
		return (int) (min + (dayUsage + hourUsage) * diff);
	}

	private int generateHumidity(int dayOfWeek, int hour) {
		if (hour > HOUR_IN_HALF) {
			hour = HOUR_IN_HALF * 2 - hour;
		}
		float hourUsage = 1 - ((hour + 1) / (float) HOUR_IN_HALF);
		int humidity = MIN_HUMIDITY;
		switch (generateType(dayOfWeek, hour)) {
		case Weather.SUN:
			humidity += hourUsage * (DIFF_HUMIDITY / HUMIDITY_SPREAD);
			break;
		case Weather.RAIN:
			humidity += (3 * DIFF_HUMIDITY) / HUMIDITY_SPREAD + hourUsage * (DIFF_HUMIDITY / HUMIDITY_SPREAD);
			break;
		case Weather.CLOUD:
		default:
			humidity += (2 * DIFF_HUMIDITY) / HUMIDITY_SPREAD + hourUsage * (DIFF_HUMIDITY / HUMIDITY_SPREAD);
			break;
		}
		return humidity;
	}

	private int generateType(int dayOfWeek, int hour) {
		int weather;
		switch (dayOfWeek) {
		case Calendar.SUNDAY:
			if (hour <= HALF_DAY) {
				weather = Weather.SUN;
			} else {
				weather = Weather.RAIN;
			}
			break;
		case Calendar.MONDAY:
			if (hour <= HALF_DAY) {
				weather = Weather.RAIN;
			} else {
				weather = Weather.CLOUD;
			}
			break;
		case Calendar.TUESDAY:
			if (hour <= THIRD_DAY) {
				weather = Weather.RAIN;
			} else {
				if (hour <= THIRD_DAY * 2) {
					weather = Weather.CLOUD;
				} else {
					weather = Weather.SUN;
				}
			}
			break;
		case Calendar.WEDNESDAY:
			if (hour <= HALF_DAY) {
				weather = Weather.CLOUD;
			} else {
				weather = Weather.SUN;
			}
			break;
		case Calendar.THURSDAY:
			if (hour <= THIRD_DAY) {
				weather = Weather.RAIN;
			} else {
				if (hour < THIRD_DAY * 2) {
					weather = Weather.CLOUD;
				} else {
					weather = Weather.SUN;
				}
			}
			break;
		case Calendar.FRIDAY:
			if (hour <= HALF_DAY) {
				weather = Weather.CLOUD;
			} else {
				weather = Weather.RAIN;
			}
			break;
		case Calendar.SATURDAY:
		default:
			if (hour <= HALF_DAY) {
				weather = Weather.RAIN;
			} else {
				weather = Weather.CLOUD;
			}
			break;
		}
		return weather;
	}

	private Time generateSunriseTime(int dayOfWeek) {
		if (dayOfWeek > Calendar.WEDNESDAY) {
			dayOfWeek = Calendar.SATURDAY - dayOfWeek;
		}
		float dayUsage = dayOfWeek / (float) Calendar.WEDNESDAY;
		int sunHour = SUNRISE_HOUR_BASE;
		int sunMin = (int) (MIN_SUNRISE - dayUsage * DIFF_SUNRISE);
		while (sunMin >= MIN_IN_HOUR) {
			sunHour++;
			sunMin -= MIN_IN_HOUR;
		}
		return new Time(0, 0, dayOfWeek, sunHour, Math.abs(sunMin));
	}
}
