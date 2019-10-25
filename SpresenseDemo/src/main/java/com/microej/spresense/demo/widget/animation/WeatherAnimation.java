/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.widget.animation;

import ej.microui.display.GraphicsContext;

/**
 * An animation of the weather.
 */
public interface WeatherAnimation {

	/**
	 * Render the animation.
	 *
	 * @param g
	 *            the graphic context.
	 * @return <code>true</code> if the animation is still running.
	 */
	boolean render(GraphicsContext g);

	/**
	 * Stops the animation.
	 */
	void stop();

	/**
	 * Get the weather.
	 *
	 * @return the weather.
	 */
	int getWeather();
}
