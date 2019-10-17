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
import com.microej.spresense.demo.util.NLSUtil;

/**
 * Widget displaying the wind.
 */
public class WindWidget extends DefaultWeatherDetails {

	private static final String MAX_SIZE = "88";

	/**
	 * Instantiates a {@link WindWidget}.
	 */
	public WindWidget() {
		super(Images.WIND, NLSUtil.getWind(), MAX_SIZE + NLSUtil.getSpeedSymbol());
	}

	@Override
	protected String getValue() {
		return Model.getInstance().getWind() + NLSUtil.getSpeedSymbol();
	}
}
