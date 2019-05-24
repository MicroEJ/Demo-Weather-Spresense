/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello;

import com.microej.example.hello.fake.FakeWeatherProvider;
import com.microej.example.hello.style.Colors;

import ej.color.GradientHelper;

/**
 *
 */
public class Model {

	public static final int COUNT_OF_HOUR_VALUES = 3;
	public static final Time time = new Time();

	public static final int SUN = 1;
	public static final int RAIN = 2;
	public static final int CLOUD = 3;

	public static Time getTime() {
		return time;
	}


	public static int getTemperature() {
		return FakeWeatherProvider.getWeather(time.getDayOfWeek() - 1, time.getHour()).getTemperature();
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
		return FakeWeatherProvider.getWeather(day - 1, hour).getTemperature();
	}

	public static int getWind() {
		return FakeWeatherProvider.getWeather(time.getDayOfWeek() - 1, time.getHour()).getWind();
	}

	public static int getHumidity() {
		return FakeWeatherProvider.getWeather(time.getDayOfWeek() - 1, time.getHour()).getHumidity();
	}

	public static Time getSunrise() {
		return FakeWeatherProvider.getWeather(time.getDayOfWeek() - 1, time.getHour()).getSunrise();
	}

	public static float getLatitude() {
		return FakeWeatherProvider.getWeather(time.getDayOfWeek() - 1, time.getHour()).getLatitude();
	}

	public static float getLongitude() {
		return FakeWeatherProvider.getWeather(time.getDayOfWeek() - 1, time.getHour()).getLongitude();
	}

	public static int getWeather() {
		return FakeWeatherProvider.getWeather(time.getDayOfWeek() - 1, time.getHour()).getType();
	}
}
