/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.widget;

import com.microej.spresense.demo.model.Model;
import com.microej.spresense.demo.model.Time;
import com.microej.spresense.demo.style.ClassSelectors;
import com.microej.spresense.demo.style.StylePopulator;
import com.microej.spresense.demo.util.NlsHelper;
import com.microej.spresense.demo.util.WidgetHelper;

import ej.animation.Animation;
import ej.animation.Animator;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.mwt.Widget;
import ej.widget.basic.Label;
import ej.widget.container.Dock;
import ej.widget.container.Grid;

/**
 * Display the details of the date.
 */
public class DateDetails extends CenterContainer implements Animation {

	private static final int HOUR_SEPERATION = 3;
	private static final String MAX_TEMPERATURE = "88"; //$NON-NLS-1$
	private final MaxSizeLabel day;
	private final Label date;
	private final MaxSizeLabel mainTemperature;
	private final Label hour;
	private final Grid nextHours;

	/**
	 * Instantiates a date.
	 */
	public DateDetails() {
		Dock dateDock = new Dock();
		this.day = new MaxSizeLabel();
		this.day.setWords(NlsHelper.getWeekdays());
		this.day.addClassSelector(ClassSelectors.DAY);
		dateDock.setCenter(WidgetHelper.addWrapper(this.day));
		this.date = new Label();
		this.date.addClassSelector(ClassSelectors.DATE_DETAILS);
		dateDock.addBottom(WidgetHelper.addWrapper(this.date));
		setFirst(dateDock);

		this.nextHours = new Grid(true, StylePopulator.COUNT_OF_HOUR_VALUES);
		for (int i = 1; i <= StylePopulator.COUNT_OF_HOUR_VALUES; i++) {
			this.nextHours.add(new HourlyDetail(HOUR_SEPERATION * i));
		}
		this.nextHours.addClassSelector(ClassSelectors.HOURLY_TEMPERATURE);

		setLast(this.nextHours);
		Dock centerDock = new Dock();
		this.mainTemperature = new MaxSizeLabel();
		String[] str = new String[1];
		str[0] = MAX_TEMPERATURE + NlsHelper.getTemperatureSymbol();
		this.mainTemperature.setWords(str);
		this.mainTemperature.addClassSelector(ClassSelectors.MAIN_TEMPERATURE);
		centerDock.setCenter(WidgetHelper.addWrapper(this.mainTemperature));
		this.hour = new Label();
		this.hour.addClassSelector(ClassSelectors.DATE_DETAILS);
		centerDock.addBottom(WidgetHelper.addWrapper(this.hour));
		centerDock.addClassSelector(ClassSelectors.CIRCLE);
		setCenter(centerDock);

		update();
	}

	private void update() {
		Model model = ServiceLoaderFactory.getServiceLoader().getService(Model.class);
		Time time = model.getTime();
		WidgetHelper.update(this.day, NlsHelper.getDay(time));
		WidgetHelper.update(this.date, NlsHelper.getDate(time));
		int hour = time.getHour();
		int day = time.getDayOfWeek();
		WidgetHelper.update(this.hour, NlsHelper.getFullHourFormat(time) + NlsHelper.getHourSymbol(hour));
		WidgetHelper.update(this.mainTemperature,
				String.valueOf(model.getTemperature()) + NlsHelper.getTemperatureSymbol());

		Widget[] widgets = this.nextHours.getWidgets();
		int widgetCount = widgets.length;
		for (int i = 0; i < widgetCount; i++) {
			HourlyDetail widget = (HourlyDetail) widgets[i];
			widget.update(day, hour);
		}
	}

	@Override
	public boolean tick(long currentTimeMillis) {
		update();
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
