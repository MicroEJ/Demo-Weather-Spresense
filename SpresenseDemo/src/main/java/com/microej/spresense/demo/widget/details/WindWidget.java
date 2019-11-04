/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.widget.details;

import com.microej.spresense.demo.model.Model;
import com.microej.spresense.demo.style.Images;
import com.microej.spresense.demo.util.NlsHelper;

import ej.components.dependencyinjection.ServiceLoaderFactory;

/**
 * Widget displaying the wind speed.
 */
public class WindWidget extends DefaultWeatherDetails {

	private static final String MAX_SIZE = "88"; //$NON-NLS-1$

	/**
	 * Instantiates a {@link WindWidget}.
	 */
	public WindWidget() {
		super(Images.WIND, NlsHelper.getWind(), MAX_SIZE + NlsHelper.getSpeedSymbol());
	}

	@Override
	protected String getValue() {
		Model model = ServiceLoaderFactory.getServiceLoader().getService(Model.class);
		return model.getWindSpeed() + NlsHelper.getSpeedSymbol();
	}
}
