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
import com.microej.example.hello.Util;
import com.microej.example.hello.style.ClassSelectors;

import ej.animation.Animation;
import ej.animation.Animator;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.widget.basic.Label;
import ej.widget.container.Dock;
import ej.widget.container.Grid;

/**
 *
 */
public class DateDetails extends CenterContainer implements Animation {

	private final MaxSizeLabel day;
	private final Label date;
	private final Label mainTemperature;
	private final Label hour;
	private final HourlyDetail hourDetail1;
	private final HourlyDetail hourDetail2;
	private final HourlyDetail hourDetail3;

	public DateDetails() {
		Dock dateDock = new Dock();
		day = new MaxSizeLabel();
		day.setWords(Model.getLocalSymbols().getWeekdays());
		day.addClassSelector(ClassSelectors.DAY);
		dateDock.setCenter(Util.addWrapper(day));
		date = new Label();
		date.addClassSelector(ClassSelectors.DATE_DETAILS);
		dateDock.addBottom(Util.addWrapper(date));
		setFirst(dateDock);

		Grid grid = new Grid(true, 3);
		hourDetail1 = new HourlyDetail(2 * 3600_000, ClassSelectors.NEXT_HOUR);
		hourDetail2 = new HourlyDetail(5 * 3600_000, ClassSelectors.MEDIUM_HOUR);
		hourDetail3 = new HourlyDetail(8 * 3600_000, ClassSelectors.LAST_HOUR_HOUR);
		grid.add(hourDetail1);
		grid.add(hourDetail2);
		grid.add(hourDetail3);
		grid.addClassSelector(ClassSelectors.HOURLY_TEMPERATURE);

		setLast(grid);
		Dock centerDock = new Dock();
		mainTemperature = new Label(
				String.valueOf(Model.getTemperature()) + Model.getTemperatureSymbol());
		mainTemperature.addClassSelector(ClassSelectors.MAIN_TEMPERATURE);
		centerDock.setCenter(Util.addWrapper(mainTemperature));
		hour = new Label();
		hour.addClassSelector(ClassSelectors.DATE_DETAILS);
		centerDock.addBottom(Util.addWrapper(hour));
		centerDock.addClassSelector(ClassSelectors.CIRCLE);
		setCenter(centerDock);

		update();
	}

	private void update() {
		Calendar calendar = Calendar.getInstance();
		long currentTime = Model.getCurrentTime();
		calendar.setTimeInMillis(currentTime);

		Util.update(this.day, Model.getLocalSymbols().getWeekday(calendar.get(Calendar.DAY_OF_WEEK) - 1));
		Date time = calendar.getTime();
		Util.update(this.date, Model.getDateFormat().format(time));
		Util.update(this.hour, Util.addPadding(Model.getFullHourFormat().format(time)));

		hourDetail1.update(currentTime);
		hourDetail2.update(currentTime);
		hourDetail3.update(currentTime);
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
