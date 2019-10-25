/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.widget;

import com.microej.spresense.demo.style.ClassSelectors;
import com.microej.spresense.demo.widget.animation.WeatherAnimationWidget;
import com.microej.spresense.demo.widget.details.GPSWidget;
import com.microej.spresense.demo.widget.details.HumidityWidget;
import com.microej.spresense.demo.widget.details.SunriseWidget;
import com.microej.spresense.demo.widget.details.WindWidget;

import ej.widget.composed.Wrapper;
import ej.widget.container.Dock;
import ej.widget.container.Grid;

/**
 * Main frame.
 */
public class MainFrame extends Dock {

	private static final int SENSORS_COUNT = 4;

	/**
	 * Instantiates a main frame.
	 */
	public MainFrame() {
		WeatherAnimationWidget weatherWidget = new WeatherAnimationWidget();
		weatherWidget.addClassSelector(ClassSelectors.TOP_BACKGROUND);
		addTop(weatherWidget);

		Wrapper bottom = new Wrapper();
		Dock dock = new Dock();
		dock.addClassSelector(ClassSelectors.DATA_FRAME);
		dock.addTop(new DateDetails());
		Grid grid = new Grid(true, SENSORS_COUNT);
		grid.add(new GPSWidget());
		grid.add(new WindWidget());
		grid.add(new HumidityWidget());
		grid.add(new SunriseWidget());
		grid.addClassSelector(ClassSelectors.WEATHER_DETAILS);
		dock.setCenter(grid);
		bottom.setWidget(dock);
		bottom.addClassSelector(ClassSelectors.MAIN_BACKGROUND);
		setCenter(bottom);
	}
}
