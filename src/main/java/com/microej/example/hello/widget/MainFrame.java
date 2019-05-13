/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello.widget;

import com.microej.example.hello.style.ClassSelectors;
import com.microej.example.hello.widget.animation.WeatherAnimationWidget;
import com.microej.example.hello.widget.details.GPSWidget;
import com.microej.example.hello.widget.details.HumidityWidget;
import com.microej.example.hello.widget.details.SunriseWidget;
import com.microej.example.hello.widget.details.WindWidget;

import ej.widget.composed.Wrapper;
import ej.widget.container.Dock;
import ej.widget.container.Grid;

/**
 *
 */
public class MainFrame extends Dock {

	/**
	 *
	 */
	public MainFrame() {
		WeatherAnimationWidget weatherWidget = new WeatherAnimationWidget();
		weatherWidget.addClassSelector(ClassSelectors.TOPBACKGROUND);
		addTop(weatherWidget);

		Wrapper bottom = new Wrapper();
		Dock dock = new Dock();
		dock.addClassSelector(ClassSelectors.DATA_FRAME);
		dock.addTop(new DateDetails());
		Grid grid = new Grid(true, 4);
		grid.add(new GPSWidget());
		grid.add(new WindWidget());
		grid.add(new HumidityWidget());
		grid.add(new SunriseWidget());
		grid.addClassSelector(ClassSelectors.WEATHER_DETAILS);
		dock.setCenter(grid);
		bottom.setWidget(dock);
		bottom.addClassSelector(ClassSelectors.MAINBACKGROUND);
		setCenter(bottom);
	}


}
