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
import com.microej.example.hello.Util;
import com.microej.example.hello.style.ClassSelectors;

import ej.widget.basic.Label;
import ej.widget.container.Dock;

public class HourlyDetail extends Dock {

	private final int offset;
	private final Label hour;
	private final Label temperature;

	public HourlyDetail(int offset, String temperatureClassSelector) {
		this.offset = offset;

		temperature = new Label();
		temperature.addClassSelector(ClassSelectors.DAY);
		temperature.addClassSelector(temperatureClassSelector);
		hour = new Label();
		hour.addClassSelector(ClassSelectors.DATE_DETAILS);
		addBottom(Util.addWrapper(hour));
		setCenter(Util.addWrapper(temperature));
		update(offset);
	}

	public void update(long hour) {
		Date date = new Date(hour + offset);
		Util.update(temperature,
				Util.addPadding(String.valueOf(Model.getTemperature(date)) + Model.getTemperatureSymbol()));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Util.update(this.hour,
				String.valueOf(Model.getHourFormat().format(date)) + Model.getHourSymbol(calendar.get(Calendar.AM_PM)));
	}
}
