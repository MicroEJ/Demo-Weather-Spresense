/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello.widget;

import com.microej.example.hello.style.ClassSelectors;

import ej.widget.basic.Label;
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
		WeatherWidget weatherWidget = new WeatherWidget();
		weatherWidget.addClassSelector(ClassSelectors.TOPBACKGROUND);
		addTop(weatherWidget);

		Wrapper bottom = new Wrapper();
		Dock dock = new Dock();
		dock.addClassSelector(ClassSelectors.DATA_FRAME);
		CenterContainer container = new CenterContainer();
		Label date = new Label("Wednesday");
		container.setFirst(date);
		container.setLast(new Label("82 76 70"));
		Label center = new Label("88F");
		center.addClassSelector(ClassSelectors.CIRCLE);
		container.setCenter(center);
		dock.addTop(container);
		Grid grid = new Grid(true, 4);
		grid.add(new Label("Lat"));
		grid.add(new Label("Wind"));
		grid.add(new Label("Humidity"));
		grid.add(new Label("Sunrise"));
		grid.addClassSelector(ClassSelectors.DETAILS);
		dock.setCenter(grid);
		bottom.setWidget(dock);
		bottom.addClassSelector(ClassSelectors.MAINBACKGROUND);
		setCenter(bottom);
	}

}
