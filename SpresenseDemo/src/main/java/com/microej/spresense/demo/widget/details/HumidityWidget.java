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

public class HumidityWidget extends DefaultWeatherDetails {

	public HumidityWidget() {
		super(Images.HUMIDITY, NLS.getHumidity());
	}

	@Override
	protected String getValue() {
		return Model.getHumidity() + "%";
	}

}
