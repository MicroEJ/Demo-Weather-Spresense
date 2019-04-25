/*
 * Java
 *
 * Copyright 2019 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microej.gnss;

/**
 *
 */
public class GnssMicroEJNative {
	// Read decoded data
	public static native double GNSS_getLattitude();
	public static native double GNSS_getLongitude();
	public static native int GNSS_getTimeHours();
	public static native int GNSS_getTimeMinutes();
	public static native int GNSS_getTimeSeconds();
	public static native int GNSS_getTimeMicroSeconds();

	// fetch new data from the file descriptor
	public static native void GNSS_fetchData();

	// State machine
	public static native void GNSS_initialize();
	public static native void GNSS_start();
	public static native void GNSS_stop();
	public static native void GNSS_close();

	public static final int GNSS_READ_INVALID_DATA_ERR_CODE = -2;
	public static final int GNSS_READ_FAILED_ERR_CODE = -1;
}
