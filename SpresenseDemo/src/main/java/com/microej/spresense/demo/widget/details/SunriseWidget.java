/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.widget.details;

import com.microej.spresense.demo.model.Model;
import com.microej.spresense.demo.model.Time;
import com.microej.spresense.demo.style.Images;
import com.microej.spresense.demo.util.NLSUtil;

/**
 * Widget displaying the sunrise time.
 */
public class SunriseWidget extends DefaultWeatherDetails {

	private static final String MAX_SIZE = "00:00"; //$NON-NLS-1$

	/**
	 * Instantiates a {@link SunriseWidget}.
	 */
	public SunriseWidget() {
		super(Images.SUNRISE, NLSUtil.getSunrise(), MAX_SIZE);
	}

	@Override
	protected String getValue() {
		Time sunrise = Model.getInstance().getSunrise();
		return NLSUtil.getFullHourFormat(sunrise);
	}

}
