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
import com.microej.spresense.demo.style.Images;

/**
 * Widget displaying the wind.
 */
public class WindWidget extends DefaultWeatherDetails {

	/**
	 * Instantiates a {@link WindWidget}.
	 */
	public WindWidget() {
		super(Images.WIND, NLS.getWind());
	}

	@Override
	protected String getValue() {
		return Model.getWind() + NLS.getSpeedSymbol();
	}
}
