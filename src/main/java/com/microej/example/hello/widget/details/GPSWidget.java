/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello.widget.details;

import com.microej.example.hello.Model;
import com.microej.example.hello.NLS;
import com.microej.example.hello.Util;
import com.microej.example.hello.style.ClassSelectors;
import com.microej.example.hello.style.Images;
import com.microej.example.hello.widget.MaxSizeLabel;

import ej.widget.basic.Label;
import ej.widget.container.Flow;
import ej.widget.container.Split;

public class GPSWidget extends WeatherDetails {

	private static final String PADDING = "0000";
	private static String[] MAX_VALUE = { "-188." + PADDING };
	private final MaxSizeLabel longitudeLabel;
	private final MaxSizeLabel latitudeLabel;

	public GPSWidget() {
		super(Images.GPS);
		Flow latFlow = new Flow();
		latFlow.add(new Label(NLS.getLat()));
		latitudeLabel = new MaxSizeLabel();
		latitudeLabel.setWords(MAX_VALUE);
		latitudeLabel.addClassSelector(ClassSelectors.WEATHER_VALUE);
		latFlow.add(latitudeLabel);
		Flow lonFlow = new Flow();
		lonFlow.add(new Label(NLS.getLon()));
		longitudeLabel = new MaxSizeLabel();
		longitudeLabel.setWords(MAX_VALUE);
		longitudeLabel.addClassSelector(ClassSelectors.WEATHER_VALUE);
		lonFlow.add(longitudeLabel);
		Split split = new Split(false, 0.5f);
		split.setFirst(latFlow);
		split.setLast(lonFlow);
		addBottom(Util.addWrapper(split));
		update();
	}

	@Override
	protected void update() {
		Util.update(latitudeLabel, paddValue(Model.getLatitude()));
		Util.update(longitudeLabel, paddValue(Model.getLongitude()));
	}

	private String paddValue(float value) {
		String endValue = String.valueOf(value);
		int dot = endValue.indexOf('.');
		int paddingWidth = endValue.length() - dot - 1;
		if(dot==-1) {
			StringBuilder string = new StringBuilder(endValue);
			endValue = string.append('.').append(PADDING).toString();
		} else if (paddingWidth < PADDING.length()) {
			StringBuilder string = new StringBuilder(endValue);
			endValue = string.append(PADDING.substring(0, PADDING.length() - paddingWidth)).toString();
		} else if (paddingWidth > PADDING.length()) {
			endValue = endValue.substring(0, dot + PADDING.length() + 1);
		}
		return endValue;
	}

}
