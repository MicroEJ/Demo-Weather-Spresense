/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.microej.example.hello.style.Colors;

import ej.bon.Util;

/**
 *
 */
public class Model {

	public static final int COUNT_OF_HOUR_VALUES = 3;

	private static long currentTime = Util.currentTimeMillis();

	private static long offset = 0;

	private static final long SPEED = 59 * 50;

	public static long getCurrentTime() {
		long currentTimeMillis = Util.currentTimeMillis();
		offset += (currentTimeMillis - currentTime) * SPEED;
		currentTime = currentTimeMillis;
		return currentTime + offset;
	}

	public static int getTemperature() {
		return 72;
	}

	public static int getCurrentColor() {
		return Colors.CORAL;
	}

	public static int getTemperature(Date date) {
		return getTemperature() + new Random().nextInt(10);
	}

	public static int getWind() {
		return 30;
	}

	public static int getHumidity() {
		return 50;
	}

	public static Date getSunrise() {
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(getCurrentTime());
		instance.set(Calendar.HOUR_OF_DAY, 06);
		instance.set(Calendar.MINUTE, 32);
		return instance.getTime();
	}

	static float change = 0;
	public static float getLatitude() {
		change += 0.1f;
		return -10.2561f + change;
	}

	public static float getLongitude() {
		return 80.886188f + change;
	}
}
