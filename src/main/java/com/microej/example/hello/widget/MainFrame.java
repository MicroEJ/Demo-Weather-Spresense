/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello.widget;

import java.util.Calendar;
import java.util.Date;

import com.microej.example.hello.Model;
import com.microej.example.hello.style.ClassSelectors;

import ej.animation.Animation;
import ej.animation.Animator;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.mwt.Widget;
import ej.widget.basic.Label;
import ej.widget.composed.Wrapper;
import ej.widget.container.Dock;
import ej.widget.container.Grid;

/**
 *
 */
public class MainFrame extends Dock implements Animation {

	private static final String STRING_PADDING = " ";
	private final MaxSizeLabel day;
	private final Label date;
	private final Label mainTemperature;
	private final Label hour;

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
		Dock dateDock = new Dock();
		day = new MaxSizeLabel();
		day.setWords(Model.getLocalSymbols().getWeekdays());
		day.addClassSelector(ClassSelectors.DAY);
		dateDock.setCenter(addWrapper(day));
		date = new Label();
		date.addClassSelector(ClassSelectors.DETAIL_SUBLINE);
		dateDock.addBottom(addWrapper(date));
		container.setFirst(dateDock);
		container.setLast(new Label("82 76 70"));
		Dock centerDock = new Dock();
		mainTemperature = new Label(Model.getTemperature() + "" + Character.valueOf(Model.getTemperatureSymbol()));
		mainTemperature.addClassSelector(ClassSelectors.MAIN_TEMPERATURE);
		centerDock.setCenter(addWrapper(mainTemperature));
		hour = new Label();
		hour.addClassSelector(ClassSelectors.DETAIL_SUBLINE);
		centerDock.addBottom(addWrapper(hour));
		centerDock.addClassSelector(ClassSelectors.CIRCLE);
		container.setCenter(centerDock);
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

		update();
	}

	private Widget addWrapper(Label label) {
		Wrapper wrapper = new Wrapper();
		wrapper.setAdjustedToChild(false);
		wrapper.setWidget(label);
		return wrapper;
	}

	private void update() {
		Calendar calendar = Calendar.getInstance();
		long currentTime = Model.getCurrentTime();
		calendar.setTimeInMillis(currentTime);

		update(this.day, Model.getLocalSymbols().getWeekday(calendar.get(Calendar.DAY_OF_WEEK) - 1));
		Date time = calendar.getTime();
		update(this.date, Model.getDateFormat().format(time));
		update(this.hour, STRING_PADDING + Model.getFullHourFormat().format(time) + STRING_PADDING);

	}

	private void update(Label label, String value) {
		if (!value.equals(label.getText())) {
			label.setText(value);
		}
	}

	@Override
	public boolean tick(long currentTimeMillis) {
		update();
		repaint();
		return true;
	}

	@Override
	public void showNotify() {
		super.showNotify();
		ServiceLoaderFactory.getServiceLoader().getService(Animator.class).startAnimation(this);
	}

	@Override
	public void hideNotify() {
		super.hideNotify();
		ServiceLoaderFactory.getServiceLoader().getService(Animator.class).stopAnimation(this);
	}
}
