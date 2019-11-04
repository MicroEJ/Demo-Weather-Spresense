/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.util;

import java.util.Random;

import com.microej.spresense.demo.model.Time;
import com.microej.spresense.demo.style.Colors;

import ej.color.GradientHelper;
import ej.mwt.Widget;
import ej.widget.basic.Label;
import ej.widget.composed.Wrapper;

/**
 * Convenient class for widget operations.
 */
public final class WidgetHelper {

	private static final int START_OF_DAWN = 20;
	private static final int START_OF_DAY = 16;
	private static final int HOUR_IN_DAY = 24;
	private static final int DAY_IN_WEEK = 7;
	private static final int MIN_IN_HOUR = 60;

	/**
	 * A shared random.
	 */
	public static final Random RANDOM = new Random();

	private WidgetHelper() {
		// Forbid instantiation.
	}

	/**
	 * Add a wrapper to a widget.
	 *
	 * @param widget
	 *            the widget to wrap.
	 * @return the wrapper containing the widget.
	 */
	public static Wrapper addWrapper(Widget widget) {
		Wrapper wrapper = new Wrapper();
		wrapper.setAdjustedToChild(false);
		wrapper.setWidget(widget);
		return wrapper;
	}

	/**
	 * Update a label only if its text have changed.
	 *
	 * @param label
	 *            the label to update.
	 * @param value
	 *            the value to set the label to.
	 */
	public static void update(Label label, String value) {
		if (!value.equals(label.getText())) {
			label.setText(value);
		}
	}

	/**
	 * Gets the color of a particular time.
	 *
	 * @param time
	 *            the time.
	 * @return the color.
	 */
	public static int getColor(Time time) {
		int dayOfWeek = time.getDayOfWeek();
		int hour = time.getHour();
		int minute = time.getMinute();
		int colorPosition = (dayOfWeek - 1) << 2;

		int colorOffset;
		int base = Colors.COLOR_COUNT_PER_DAY;
		if (hour < START_OF_DAY) {
			colorOffset = hour >> 2;
		} else if (hour < START_OF_DAWN) {
			colorOffset = 2;
		} else {
			colorOffset = ((HOUR_IN_DAY - 1 - hour) >> 1);
			base /= 2;
		}

		colorPosition += colorOffset;
		int nextColorPosition;
		if (hour < HOUR_IN_DAY / 2) {
			nextColorPosition = (colorPosition + 1) % Colors.WEEK.length;
		} else if (colorOffset != 0) {
			nextColorPosition = (colorPosition - 1);
		} else {
			nextColorPosition = (dayOfWeek % DAY_IN_WEEK) * Colors.COLOR_COUNT_PER_DAY;
		}

		float fullFilment = ((hour % base) * MIN_IN_HOUR + minute) / (base * (float) MIN_IN_HOUR);
		return GradientHelper.blendColors(Colors.WEEK[colorPosition], Colors.WEEK[nextColorPosition], fullFilment);
	}
}
