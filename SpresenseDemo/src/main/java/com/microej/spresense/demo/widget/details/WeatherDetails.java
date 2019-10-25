/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.widget.details;

import java.util.Observable;
import java.util.Observer;

import com.microej.spresense.demo.model.Model;
import com.microej.spresense.demo.style.ClassSelectors;
import com.microej.spresense.demo.util.Util;

import ej.widget.basic.Image;
import ej.widget.composed.Wrapper;
import ej.widget.container.Dock;

/**
 * Widget displaying the weather detail.
 */
public class WeatherDetails extends Dock implements Observer {

	/**
	 * Instantiates a {@link WeatherDetails}.
	 *
	 * @param icon
	 *            the path to the icon.
	 */
	public WeatherDetails(String icon) {
		Image image = new Image(icon);
		Wrapper wrapper = Util.addWrapper(image);
		wrapper.addClassSelector(ClassSelectors.ICON);
		setCenter(wrapper);
	}

	/**
	 * Update the displayed value.
	 */
	protected void update() {
		// Nothing to do.
	}

	@Override
	public void showNotify() {
		super.showNotify();
		update();
		Model.getInstance().addObserver(this);
	}

	@Override
	public void hideNotify() {
		super.hideNotify();
		Model.getInstance().deleteObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		update();
	}
}
