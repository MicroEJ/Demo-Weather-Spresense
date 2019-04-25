/*
 * Java
 *
 * Copyright 2019 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microej.gnss;

import com.microej.gnss.LocalizationData.CoordinateType;

/**
 * Implementation of the GnssManager function that calls the native function interface
 */
public class GnssManagerImpl {

	public enum GnssManagerState{
		IDLE, INITIALIZED, STARTED, STOPPED, CLOSED;
	}
	public GnssManagerState state;
	public LocalizationData longitude;
	public LocalizationData latitude;

	/**
	 * Initialize the manager instance
	 *
	 * @param None
	 */
	public GnssManagerImpl() {
		state = GnssManagerImpl.GnssManagerState.IDLE;
		latitude = new LocalizationData();
		longitude = new LocalizationData();
		try {
			GnssMicroEJNative.GNSS_initialize();
			state = GnssManagerState.INITIALIZED;
		} catch(ej.sni.NativeException e) {
			throw e;
		}
	}

	/**
	 * Fetch the latest position received from the GNSS
	 * This function will suspend the current Java thread until a new position arrive
	 *
	 * @param None
	 */
	private void fetchPosition() {
		GnssMicroEJNative.GNSS_fetchData();
	}

	/**
	 * Write the native timing data received to the LocalizationData attributes
	 *
	 * @param None
	 */
	private void updateTimeDatas() {
		int hours = GnssMicroEJNative.GNSS_getTimeHours();
		int minutes = GnssMicroEJNative.GNSS_getTimeMinutes();
		int seconds = GnssMicroEJNative.GNSS_getTimeSeconds();
		int microseconds = GnssMicroEJNative.GNSS_getTimeMicroSeconds();
		LocalizationData.setTimingData(hours, minutes, seconds, microseconds);
	}
	/**
	 * Write the native position data received to the LocalizationData attributes
	 * If data is invalid set the CoordinateType to INVALID and data to 0.0
	 *
	 * @param isValid
	 *		boolean indicating if the localization data is valid or not
	 */
	private void updatePositionDatas(boolean isValid) {
		if(isValid) {
			double lati = GnssMicroEJNative.GNSS_getLattitude();
			latitude.setPositioningData(lati, CoordinateType.LAT);

			double longi = GnssMicroEJNative.GNSS_getLongitude();
			longitude.setPositioningData(longi, CoordinateType.LONG);
		} else {
			// Indicate that data read are invalid
			longitude.setPositioningData(0, CoordinateType.INVALID);
			latitude.setPositioningData(0, CoordinateType.INVALID);
		}
	}

	/**
	 * Read the position
	 * If no data can be read throw a native exception
	 * Set the data to invalid if no signal is found
	 * See fetchPosition
	 *
	 * @param None
	 */
	public boolean readPosition() {
		assert(state == GnssManagerState.STARTED);
		boolean ret = false;
		try {
			fetchPosition();
			ret = true;
		} catch(ej.sni.NativeException e) {
			ret = false;
			// We may still update timing data
			if(e.getErrorCode() == GnssMicroEJNative.GNSS_READ_INVALID_DATA_ERR_CODE) {
				updateTimeDatas();
				updatePositionDatas(false);
			} else {
				throw e;
			}
		}
		updateTimeDatas();
		updatePositionDatas(true);
		return ret;
	}

	/**
	 * SwitchOn Positioning
	 *
	 * @param None
	 */
	public void switchOn() {
		if(state == GnssManagerState.STARTED) {
			return;
		}
		assert(state == GnssManagerState.INITIALIZED);

		try {
			GnssMicroEJNative.GNSS_start();
			state = GnssManagerState.STARTED;
		} catch(ej.sni.NativeException e) {
			throw e;
		}
	}
	/**
	 * SwitchOff Positioning
	 *
	 * @param None
	 */
	public void switchOff() {
		if(state == GnssManagerState.STOPPED) {
			return;
		}
		assert(state == GnssManagerState.STARTED);

		try {
			GnssMicroEJNative.GNSS_stop();
			state = GnssManagerState.STOPPED;
		} catch(ej.sni.NativeException e) {
			throw e;
		}
	}

	/**
	 * Close the manager instance
	 *
	 * @param None
	 */
	public void closeManager() {
		if(state == GnssManagerState.CLOSED) {
			return;
		}
		assert(state == GnssManagerState.STOPPED);

		try {
			GnssMicroEJNative.GNSS_close();
			state = GnssManagerState.CLOSED;
		} catch(ej.sni.NativeException e) {
			throw e;
		}
	}


}
