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

/**
 * Details of the default weather.
 */
public abstract class DefaultWeatherDetails extends WeatherDetails {

	private final Label value;

	/**
	 * Instatiates a {@link DefaultWeatherDetails}.
	 *
	 * @param icon
	 *            the icons of the value.
	 * @param title
	 *            the title.
	 */
	public DefaultWeatherDetails(String icon, String title) {
		super(icon);
		Label titleLabel = new Label(title);
		value = new Label();
		addBottom(Util.addWrapper(value));
		addBottom(Util.addWrapper(titleLabel));
		value.addClassSelector(ClassSelectors.WEATHER_VALUE);
	}

	@Override
	public void showNotify() {
		update();
		super.showNotify();
	}

	@Override
	protected void update() {
		Util.update(value, Util.addPadding(getValue()));
	}

	/**
	 * Gets the value.
	 *
	 * @return the value.
	 */
	protected abstract String getValue();

}
