/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.widget.details;

import com.microej.spresense.demo.model.Model;
import com.microej.spresense.demo.style.ClassSelectors;
import com.microej.spresense.demo.style.Images;
import com.microej.spresense.demo.util.NlsHelper;
import com.microej.spresense.demo.util.WidgetHelper;
import com.microej.spresense.demo.widget.MaxSizeLabel;

import ej.components.dependencyinjection.ServiceLoaderFactory;
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
		Flow latituteFlow = new Flow();
		String[] latLongText = new String[] { NlsHelper.getLat(), NlsHelper.getLon() };
		MaxSizeLabel latitudeLabel = new MaxSizeLabel(latLongText[0]);
		latitudeLabel.setWords(latLongText);
		latituteFlow.add(latitudeLabel);
		this.latitudeValue = new MaxSizeLabel();
		this.latitudeValue.setWords(MAX_VALUE);
		this.latitudeValue.addClassSelector(ClassSelectors.WEATHER_VALUE);
		latituteFlow.add(this.latitudeValue);
		Flow longitudeFlow = new Flow();
		String[] longitudeLongText = new String[] { NlsHelper.getLat(), NlsHelper.getLon() };
		MaxSizeLabel longitudeLabel = new MaxSizeLabel(longitudeLongText[1]);
		longitudeLabel.setWords(longitudeLongText);
		longitudeFlow.add(longitudeLabel);
		this.longitudeValue = new MaxSizeLabel();
		this.longitudeValue.setWords(MAX_VALUE);
		this.longitudeValue.addClassSelector(ClassSelectors.WEATHER_VALUE);
		longitudeFlow.add(this.longitudeValue);
		Split split = new Split();
		split.setHorizontal(false);
		split.setFirst(latituteFlow);
		split.setLast(longitudeFlow);
		addBottom(WidgetHelper.addWrapper(split));
	}

	@Override
	protected void update() {
		Model model = ServiceLoaderFactory.getServiceLoader().getService(Model.class);
		WidgetHelper.update(this.latitudeValue, padValue(model.getLatitude()));
		WidgetHelper.update(this.longitudeValue, padValue(model.getLongitude()));
	}

	private static String padValue(float value) {
		String endValue = String.valueOf(value);
		int dot = endValue.indexOf('.');
		int paddingWidth = endValue.length() - dot - 1;
		StringBuilder string = new StringBuilder(endValue);
		for (int i = dot; i < MAX_VALUE[0].length() - PADDING.length(); i++) {
			string.insert(0, ' ');
		}
		if (dot == -1) {
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
