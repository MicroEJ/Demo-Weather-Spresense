/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello;

import java.util.Calendar;

import ej.animation.Animation;
import ej.animation.Animator;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.widget.container.Grid;
import ej.widget.toggle.ToggleGroup;

public class WeekWidget extends Grid implements Animation {

	private final ToggleGroup toggleGroup;
	private final Calendar calendar;

	public WeekWidget() {
		super(true, 7);
		calendar = Calendar.getInstance();

		toggleGroup = new ToggleGroup();
		addClassSelector(StylePopulator.CS_WEEK);
		Calendar calendar = Calendar.getInstance();
		int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
		for (int i = Calendar.MONDAY; i <= Calendar.SATURDAY; i++) {
			DayWidget day = new DayWidget(i);
			toggleGroup.addToggle(day.getToggle());
			if (i == currentDay) {
				day.setChecked(true);
			}
			add(day);
		}
		DayWidget day = new DayWidget(Calendar.SUNDAY);
		toggleGroup.addToggle(day.getToggle());
		if (Calendar.SUNDAY == currentDay) {
			day.setChecked(true);
		}
		add(day);
	}

	public void select(int day) {
		if (day < Calendar.SUNDAY || day > Calendar.SATURDAY) {
			throw new IllegalArgumentException();
		}

		if(day==Calendar.SUNDAY) {
			toggleGroup.getToggles().get(6).setChecked(true);
		} else {
			toggleGroup.getToggles().get(day - Calendar.MONDAY).setChecked(true);
		}
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

	@Override
	public boolean tick(long currentTimeMillis) {
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.setTimeInMillis(Model.getCurrentTime());
		int newDay = calendar.get(Calendar.DAY_OF_WEEK);
		if (day != newDay) {
			select(calendar.get(Calendar.DAY_OF_WEEK));
		}
		return true;
	}
}
