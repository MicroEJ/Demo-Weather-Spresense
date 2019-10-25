/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.style;

/**
 * Images used for the demo.
 */
public final class Images {

	private static final String ICON = "icon-"; //$NON-NLS-1$
	private static final String PNG = ".png"; //$NON-NLS-1$
	private static final String IMAGES = "/images/"; //$NON-NLS-1$

	/**
	 * Path to the LOGO.
	 */
	public static final String LOGO = IMAGES + "microej" + PNG; //$NON-NLS-1$

	/**
	 * Path to the GPS.
	 */
	public static final String GPS = IMAGES + ICON + "gps" + PNG; //$NON-NLS-1$

	/**
	 * Path to the HUMIDITY.
	 */
	public static final String HUMIDITY = IMAGES + ICON + "humidity" + PNG; //$NON-NLS-1$

	/**
	 * Path to the SUNRISE.
	 */
	public static final String SUNRISE = IMAGES + ICON + "sunrise" + PNG; //$NON-NLS-1$

	/**
	 * Path to the WIND.
	 */
	public static final String WIND = IMAGES + ICON + "wind" + PNG; //$NON-NLS-1$

	private Images() {
		// Forbid instantiation.
	}

}
