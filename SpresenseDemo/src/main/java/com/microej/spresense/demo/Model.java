/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo;

import java.io.IOException;

import com.microej.spresense.demo.fake.FakeWeatherProvider;
import com.microej.spresense.demo.style.Colors;

import ej.color.GradientHelper;
import ej.gnss.GnssManager;

/**
 *
 */
public class Model {

	public static final int COUNT_OF_HOUR_VALUES = 3;
	public static final Time time = new Time();

	public static final int SUN = 1;
	public static final int RAIN = 2;
	public static final int CLOUD = 3;
	private static final float DEFAULT_LATITUDE = 35.628f;
	private static final float DEFAULT_LONGITUDE = 139.74f;

	private static GnssManager gnssManager;

	static {

		new Thread(new Runnable() {
			@Override
			public void run() {
				Thread.currentThread().setName("GNSS poller");
				GnssManager gnssManager = GnssManager.getInstance();
				try {
					gnssManager.switchOn();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				boolean run = true;
				while (run) {
					try {
						gnssManager.readPosition();
						Model.gnssManager = gnssManager;
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						Thread.sleep(10_000);
					} catch (InterruptedException e) {
						run = false;
					}
				}
			}
		}).start();
	}

	public static Time getTime() {
		return time;
	}


	public static int getTemperature() {
		return FakeWeatherProvider.getTemperature(time.getDayOfWeek(), time.getHour());
	}

	public static int getColor(Time time) {
		int dayOfWeek = time.getDayOfWeek();
		int colorPosition = (dayOfWeek - 1) << 2;
		int hour = time.getHour();
		int minute = time.getMinute();

		int colorOffset;
		int base = 4;
		if(hour<16) {
			colorOffset = hour >> 2;
		} else if (hour<20) {
			colorOffset = 2;
		} else {
			colorOffset = ((23 - hour) >> 1);
			base = 2;
		}

		colorPosition += colorOffset;
		int nextColorPosition;
		if (hour < 12) {
			nextColorPosition = (colorPosition + 1) % Colors.WEEK.length;
		} else if (colorOffset != 0) {
			nextColorPosition = (colorPosition - 1);
		} else {
			nextColorPosition = (dayOfWeek % 7) * 4;
		}

		float fullFilment = ((hour % base) * 60 + minute) / (base * 60f);
		return GradientHelper.blendColors(Colors.WEEK[colorPosition],
				Colors.WEEK[nextColorPosition], fullFilment);
	}

	public static int getTemperature(int day, int hour) {
		return FakeWeatherProvider.getTemperature(day, hour);
	}

	public static int getWind() {
		return FakeWeatherProvider.getWind(time.getDayOfWeek(), time.getHour());
	}

	public static int getHumidity() {
		return FakeWeatherProvider.getHumidity(time.getDayOfWeek(), time.getHour());
	}

	public static Time getSunrise() {
		return FakeWeatherProvider.getSunrise(time.getDayOfWeek(), time.getHour());
	}

	public static float getLatitude() {
		return (gnssManager == null) ? DEFAULT_LATITUDE : gnssManager.getLatitude();
	}

	public static float getLongitude() {
		return (gnssManager == null) ? DEFAULT_LONGITUDE : gnssManager.getLongitude();
	}

	public static int getWeather() {
		return FakeWeatherProvider.getType(time.getDayOfWeek(), time.getHour());
	}
}
