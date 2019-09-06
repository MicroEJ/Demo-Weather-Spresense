/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.widget.details;

import com.microej.spresense.demo.Util;
import com.microej.spresense.demo.style.ClassSelectors;

import ej.widget.basic.Label;

public abstract class DefaultWeatherDetails extends WeatherDetails {

	private final Label value;

	public DefaultWeatherDetails(String icon, String title) {
		super(icon);
		Label titleLabel = new Label(title);
		value = new Label();
		addBottom(Util.addWrapper(value));
		addBottom(Util.addWrapper(titleLabel));
		value.addClassSelector(ClassSelectors.WEATHER_VALUE);
		update();
	}

	@Override
	protected void update() {
		Util.update(value, Util.addPadding(getValue()));
	}

	protected abstract String getValue();

}
