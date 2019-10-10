/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo;

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

	private static GnssManager gnssManager;
	public static Time getTime() {
		return time;
	}


	public static int getTemperature() {
		return FakeWeatherProvider.getTemperature(time.getDayOfWeek(), time.getHour());
	}

	public static int getColor(Time time) {
		int dayOfWeek = time.getDayOfWeek();
		int colorPosition = (dayOfWeek - 1) << 2;
		int hour = time.getHour() % 12;
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
		return (gnssManager == null) ? 0 : gnssManager.getLatitude();
	}

	public static float getLongitude() {
		return (gnssManager == null) ? 0 : gnssManager.getLongitude();
	}

	public static int getWeather() {
		return FakeWeatherProvider.getType(time.getDayOfWeek(), time.getHour());
	}
}
