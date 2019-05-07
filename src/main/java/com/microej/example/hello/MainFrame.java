/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello;

import ej.widget.StyledWidget;
import ej.widget.container.Dock;
import ej.widget.container.Split;

/**
 *
 */
public class MainFrame extends Dock {

	/**
	 *
	 */
	public MainFrame() {
		Split split = new Split();

		StyledWidget widget = new Sun();
		widget.addClassSelector(StylePopulator.CS_TRIANGLE);
		widget.addClassSelector(StylePopulator.CS_SUN);
		split.setFirst(widget);

		TextWidget textWidget = new TextWidget();
		textWidget.addClassSelector(StylePopulator.CS_DATE);
		split.setLast(textWidget);
		setCenter(split);

		addBottom(new WeekWidget());
	}

}
