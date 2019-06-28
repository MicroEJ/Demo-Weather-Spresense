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
 *
 */
public interface WeatherAnimation {

	boolean render(GraphicsContext g);

	void stop();

	int getWeather();
}
