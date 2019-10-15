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
import com.microej.spresense.demo.util.Util;

/**
 * Widget displaying the sunrise time.
 */
public class SunriseWidget extends DefaultWeatherDetails {

	/**
	 * Instantiates a {@link SunriseWidget}.
	 */
	public SunriseWidget() {
		super(Images.SUNRISE, NLSUtil.getSunrise());
	}

	@Override
	protected String getValue() {
		Time sunrise = Model.getInstance().getSunrise();
		return Util.addPadding(NLSUtil.getFullHourFormat(sunrise));
	}

}
