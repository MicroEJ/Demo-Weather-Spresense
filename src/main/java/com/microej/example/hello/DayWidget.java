/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello;

import ej.util.text.EnglishDateFormatSymbols;
import ej.widget.basic.Label;
import ej.widget.composed.ToggleWrapper;

/**
 *
 */
public class DayWidget extends ToggleWrapper {

	private final int day;

	/**
	 *
	 */
	public DayWidget(int day) {
		super();
		this.day = day;
		Label widget = new Label(EnglishDateFormatSymbols.getInstance().getShortWeekday(day - 1));
		addClassSelector(StylePopulator.CS_DAY);
		setWidget(widget);
	}

}
