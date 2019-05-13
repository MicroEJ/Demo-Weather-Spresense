/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello.widget;

import java.util.Calendar;
import java.util.Date;

import com.microej.example.hello.Model;
import com.microej.example.hello.NLS;
import com.microej.example.hello.style.Images;

public class SunriseWidget extends DefaultWeatherDetails {

	public SunriseWidget() {
		super(Images.SUNRISE, NLS.getSunrise());
	}

	@Override
	protected String getValue() {
		Calendar calendar = Calendar.getInstance();
		Date sunrise = Model.getSunrise();
		calendar.setTime(sunrise);
		return NLS.getFullHourFormat().format(sunrise);// + NLS.getHourSymbol(calendar.get(Calendar.AM_PM));
	}

}
