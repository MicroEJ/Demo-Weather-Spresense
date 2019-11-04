/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.widget;

import com.microej.spresense.demo.model.Model;
import com.microej.spresense.demo.style.ClassSelectors;
import com.microej.spresense.demo.util.NLSUtil;
import com.microej.spresense.demo.util.Util;

import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.widget.basic.Label;
import ej.widget.container.Dock;

/**
 * Display the hour detail.
 */
public class HourlyDetail extends Dock {

	private static final int DAY_IN_WEEK = 7;
	private static final int HOUR_IN_DAY = 24;
	private final int offset;
	private final Label hour;
	private final Label temperature;

	/**
	 * Instantiates a {@link HourlyDetail}.
	 *
	 * @param offset
	 *            the offset to set.
	 */
	public HourlyDetail(int offset) {
		this.offset = offset;

		this.temperature = new Label();
		this.hour = new Label();
		this.hour.addClassSelector(ClassSelectors.DATE_DETAILS);
		addBottom(Util.addWrapper(this.hour));
		setCenter(Util.addWrapper(this.temperature));
	}

	/**
	 * update the detail.
	 *
	 * @param day
	 *            the day.
	 * @param hour
	 *            the hour.
	 */
	public void update(int day, int hour) {
		hour += this.offset;
		if (hour >= HOUR_IN_DAY) {
			hour -= HOUR_IN_DAY;
			day = (day % (DAY_IN_WEEK - 1)) + 1;
		}
		Model model = ServiceLoaderFactory.getServiceLoader().getService(Model.class);
		Util.update(this.temperature, String.valueOf(model.getTemperature(day, hour)) + NLSUtil.getTemperatureSymbol());
		Util.update(this.hour, String.valueOf(NLSUtil.getHourFormat(hour)) + ' ' + NLSUtil.getHourSymbol(hour));
	}
}
