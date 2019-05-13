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
import com.microej.example.hello.style.Images;

public class WindWidget extends DefaultWeatherDetails {

	public WindWidget() {
		super(Images.WIND, NLS.getWind());
	}

	@Override
	protected String getValue() {
		return Model.getWind() + NLS.getSpeedSymbol();
	}
}
