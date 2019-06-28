/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.widget;


import com.microej.spresense.demo.Model;
import com.microej.spresense.demo.NLS;
import com.microej.spresense.demo.Util;
import com.microej.spresense.demo.style.ClassSelectors;

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
	}

	public void update(int day, int hour) {
		hour += offset;
		if (hour >= 24) {
			hour -= 24;
			day = (day % 6) + 1;
		}
		Util.update(temperature,
				Util.addPadding(String.valueOf(Model.getTemperature(day, hour)) + NLS.getTemperatureSymbol()));
		Util.update(this.hour,
				String.valueOf(NLS.getHourFormat(hour)) + " " + NLS.getHourSymbol(hour));
	}
}
