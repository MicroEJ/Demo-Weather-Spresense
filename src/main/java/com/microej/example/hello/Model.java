/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello;

import com.microej.example.hello.style.Colors;

import ej.color.GradientHelper;

/**
 *
 */
public class Model {

	public static final int COUNT_OF_HOUR_VALUES = 3;
	public static final Time time = new Time();
	public static final Time sunrise = new Time(2019, 05, 01, 06, 32);

	public static Time getTime() {
		return time;
	}


	public static int getTemperature() {
		return 72;
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
		return GradientHelper.blendColors(Colors.WEEK[colorPosition],
				Colors.WEEK[nextColorPosition], getPercent(hour, minute, base));
	}


	private static float getPercent(int hour, int minute, int base) {
		return ((hour % base) * 60 + minute) / (base * 60f);
	}

	public static int getTemperature(long hour) {
		return getTemperature();
	}

	public static int getWind() {
		return 30;
	}

	public static int getHumidity() {
		return 50;
	}

	public static Time getSunrise() {
		return sunrise;
	}

	public static float getLatitude() {
		return -10.2561f;
	}

	public static float getLongitude() {
		return 80.886188f;
	}
}
