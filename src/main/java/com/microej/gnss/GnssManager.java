/*
 * Java
 *
 * Copyright 2019 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microej.gnss;

/**
 * GnssManager
 *	Contains a static instance of GnssManagerImpl
 */
public class GnssManager {
	/**
	 *	Instantiate a static instance of GnssManagerImpl
	 */
	public GnssManager() {
		gnssManagerInstance = new GnssManagerImpl();
	}
	/**
	 *	Access to static instance of GnssManagerImpl
	 */
	public static GnssManagerImpl getManager() {
		return gnssManagerInstance;
	}

	/**
	 *	Call switchOn methods using static instance of GnssManagerImpl
	 *  Switch positioning on
	 */
	public static void switchOn() {
		gnssManagerInstance.switchOn();
	}

	/**
	 *	Call switchOff methods using static instance of GnssManagerImpl
	 * Switch positioning off
	 */
	public static void switchOff() {
		gnssManagerInstance.switchOff();
	}

	/**
	 *	Call readPosition methods using static instance of GnssManagerImpl
	 *  Read positioning data. This will suspend the calling thread until data is available.
	 */
	public static void readPosition() {
		gnssManagerInstance.readPosition();
	}

	/**
	 *	Call closeManager methods using static instance of GnssManagerImpl
	 *  No other call will be possible after this function is called
	 */
	public static void closeManager() {
		gnssManagerInstance.closeManager();
	}

	/**
	 *  Return the latitude localization data
	 */
	public static LocalizationData getLatitude() {
		return gnssManagerInstance.latitude;
	}
	/**
	 *  Return the longitude localization data
	 */
	public static LocalizationData getLongitude() {
		return gnssManagerInstance.longitude;
	}

	private static GnssManagerImpl gnssManagerInstance;

}
