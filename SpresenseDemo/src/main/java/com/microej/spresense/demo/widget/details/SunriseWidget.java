/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.widget.details;

import com.microej.spresense.demo.Model;
import com.microej.spresense.demo.NLS;
import com.microej.spresense.demo.Time;
import com.microej.spresense.demo.Util;
import com.microej.spresense.demo.style.Images;

public class SunriseWidget extends DefaultWeatherDetails {

	public SunriseWidget() {
		super(Images.SUNRISE, NLS.getSunrise());
	}

	@Override
	protected String getValue() {
		Time sunrise = Model.getSunrise();
		return Util.addPadding(NLS.getFullHourFormat(sunrise));
	}

}
