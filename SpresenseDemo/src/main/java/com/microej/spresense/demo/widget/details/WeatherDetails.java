/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.widget.details;

import com.microej.spresense.demo.Util;
import com.microej.spresense.demo.style.ClassSelectors;

import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.widget.basic.Image;
import ej.widget.composed.Wrapper;
import ej.widget.container.Dock;

public class WeatherDetails extends Dock {

	private static final long REFRESH_RATE = 2000;
	private TimerTask refresh;

	public WeatherDetails(String icon) {
		Image image = new Image(icon);
		Wrapper wrapper = Util.addWrapper(image);
		wrapper.addClassSelector(ClassSelectors.ICON);
		setCenter(wrapper);
	}

	protected void update() {
		// Nothing to do.
	}

	@Override
	public void showNotify() {
		super.showNotify();
		if (refresh == null) {
			refresh = new TimerTask() {

				@Override
				public void run() {
					update();
				}
			};
			ServiceLoaderFactory.getServiceLoader().getService(Timer.class).schedule(refresh, REFRESH_RATE, REFRESH_RATE);
		}
	}

	@Override
	public void hideNotify() {
		super.hideNotify();
		if (refresh != null) {
			refresh.cancel();
			refresh = null;
		}
		ServiceLoaderFactory.getServiceLoader().getService(Timer.class);
	}
}
