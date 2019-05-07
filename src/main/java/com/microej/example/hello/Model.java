/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello;

import ej.bon.Util;

/**
 *
 */
public class Model {

	private static long currentTime = Util.currentTimeMillis();

	private static final long THRESHOLD = 1557230873442L;
	private static long offset = 0;

	private static final long SPEED = 59 * 50;

	public static long getCurrentTime() {
		long currentTimeMillis = Util.currentTimeMillis();
		offset += (currentTimeMillis - currentTime) * SPEED;
		currentTime = currentTimeMillis;
		return currentTime + offset;
	}

	/**
	 * @return
	 */
	public static String getLocation() {
		return "Californie";
	}

	/**
	 * @return
	 */
	public static int getTemperature() {
		return 72;
	}

}
