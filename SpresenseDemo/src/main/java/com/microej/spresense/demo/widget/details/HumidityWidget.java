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
 * A widget displaying the humidity.
 */
public class HumidityWidget extends DefaultWeatherDetails {

	private static final String MAX_SIZE = "100%"; //$NON-NLS-1$

	/**
	 * Instantiates a {@link HumidityWidget}.
	 */
	public HumidityWidget() {
		super(Images.HUMIDITY, NlsHelper.getHumidity(), MAX_SIZE);
	}

	@Override
	protected String getValue() {
		Model model = ServiceLoaderFactory.getServiceLoader().getService(Model.class);
		return String.valueOf(model.getHumidity()) + '%';
	}

}
