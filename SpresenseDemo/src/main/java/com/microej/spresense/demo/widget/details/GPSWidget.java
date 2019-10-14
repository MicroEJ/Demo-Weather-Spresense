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
import com.microej.spresense.demo.Util;
import com.microej.spresense.demo.style.ClassSelectors;
import com.microej.spresense.demo.style.Images;
import com.microej.spresense.demo.widget.MaxSizeLabel;

import ej.widget.container.Flow;
import ej.widget.container.Split;

/**
 * A widget displaying the gps coordinate.
 */
public class GPSWidget extends WeatherDetails {

	private static final String PADDING = ".000"; //$NON-NLS-1$
	private static final String[] MAX_VALUE = { "-188" + PADDING }; //$NON-NLS-1$
	private final MaxSizeLabel longitudeValue;
	private final MaxSizeLabel latitudeValue;

	/**
	 * Instantiates a {@link GPSWidget}.
	 */
	public GPSWidget() {
		super(Images.GPS);
		Flow latFlow = new Flow();
		String[] latLongText = new String[] { NLS.getLat(), NLS.getLon() };
		MaxSizeLabel latitudeLabel = new MaxSizeLabel(latLongText[0]);
		latitudeLabel.setWords(latLongText);
		latFlow.add(latitudeLabel);
		latitudeValue = new MaxSizeLabel();
		latitudeValue.setWords(MAX_VALUE);
		latitudeValue.addClassSelector(ClassSelectors.WEATHER_VALUE);
		latFlow.add(latitudeValue);
		Flow lonFlow = new Flow();
		String[] longLongText = new String[] { NLS.getLat(), NLS.getLon() };
		MaxSizeLabel longitudeLabel = new MaxSizeLabel(longLongText[1]);
		longitudeLabel.setWords(longLongText);
		lonFlow.add(longitudeLabel);
		longitudeValue = new MaxSizeLabel();
		longitudeValue.setWords(MAX_VALUE);
		longitudeValue.addClassSelector(ClassSelectors.WEATHER_VALUE);
		lonFlow.add(longitudeValue);
		Split split = new Split();
		split.setHorizontal(false);
		split.setFirst(latFlow);
		split.setLast(lonFlow);
		addBottom(Util.addWrapper(split));
	}

	@Override
	public void showNotify() {
		update();
		super.showNotify();
	}

	@Override
	protected void update() {
		Util.update(latitudeValue, paddValue(Model.getLatitude()));
		Util.update(longitudeValue, paddValue(Model.getLongitude()));
	}

	private static String paddValue(float value) {
		String endValue = String.valueOf(value);
		int dot = endValue.indexOf('.');
		int paddingWidth = endValue.length() - dot - 1;
		StringBuilder string = new StringBuilder(endValue);
		for (int i = dot; i < MAX_VALUE[0].length() - PADDING.length(); i++) {
			string.insert(0, ' ');
		}
		if(dot==-1) {
			string.append(PADDING);
		} else {
			if (paddingWidth < PADDING.length()) {
				while (string.length() < MAX_VALUE[0].length()) {
					string.append('0');
				}
			} else if (paddingWidth > PADDING.length()) {
				string.setLength(MAX_VALUE[0].length());
			}
		}
		return string.toString();
	}

}
