/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.widget.details;

import com.microej.spresense.demo.style.ClassSelectors;
import com.microej.spresense.demo.util.Util;
import com.microej.spresense.demo.widget.MaxSizeLabel;

import ej.widget.basic.Label;

/**
 * Details of the default weather.
 */
public abstract class DefaultWeatherDetails extends WeatherDetails {

	private final MaxSizeLabel value;

	/**
	 * Instantiates a {@link DefaultWeatherDetails}.
	 *
	 * @param icon
	 *            the path to the icon to be displayed by this widget.
	 * @param title
	 *            a text representing the title of this widget.
	 * @param maxSizeText
	 *            a string representing the longest possible text to be displayed by the widget.
	 */
	public DefaultWeatherDetails(String icon, String title, String maxSizeText) {
		super(icon);
		Label titleLabel = new Label(title);
		this.value = new MaxSizeLabel();
		this.value.setWords(new String[] { maxSizeText });
		addBottom(Util.addWrapper(this.value));
		addBottom(Util.addWrapper(titleLabel));
		this.value.addClassSelector(ClassSelectors.WEATHER_VALUE);
	}

	@Override
	protected void update() {
		Util.update(this.value, getValue());
	}

	/**
	 * Gets the value.
	 *
	 * @return the value.
	 */
	protected abstract String getValue();

}
