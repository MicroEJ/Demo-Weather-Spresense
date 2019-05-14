/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello.widget;

import com.microej.example.hello.Model;
import com.microej.example.hello.NLS;
import com.microej.example.hello.Util;
import com.microej.example.hello.style.ClassSelectors;

import ej.widget.basic.Label;
import ej.widget.container.Dock;

public class HourlyDetail extends Dock {

	private final int offset;
	private final Label hour;
	private final Label temperature;

	public HourlyDetail(int offset) {
		this.offset = offset;

		temperature = new Label();
		hour = new Label();
		hour.addClassSelector(ClassSelectors.DATE_DETAILS);
		addBottom(Util.addWrapper(hour));
		setCenter(Util.addWrapper(temperature));
		update(offset);
	}

	public void update(int hour) {
		hour += offset;
		Util.update(temperature,
				Util.addPadding(String.valueOf(Model.getTemperature(hour)) + NLS.getTemperatureSymbol()));
		Util.update(this.hour,
				String.valueOf(NLS.getHourFormat(hour)) + NLS.getHourSymbol(hour));
	}
}
