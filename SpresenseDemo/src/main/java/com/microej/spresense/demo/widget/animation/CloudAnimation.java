/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.widget.animation;

import com.microej.spresense.demo.Weather;
import com.microej.spresense.demo.style.StylePopulator;

import ej.bon.Util;
import ej.microui.display.GraphicsContext;

/**
 * Animation of clouds.
 */
public class CloudAnimation implements WeatherAnimation {

	private static final float DIAMETER_RATIO = 0.6f;
	private static final float MIN_DIAMETER_RATIO = 0.4f;
	private static final float HEIGHT_RATIO = 1.5f;
	private static final int CLOUD_COUNT = 4;
	private boolean run;
	private final Cloud[] clouds;


	/**
	 * Instantiates a {@link CloudAnimation}.
	 */
	public CloudAnimation() {
		run = true;
		clouds = new Cloud[CLOUD_COUNT];
		int height = (int) (StylePopulator.getTopHeight() * HEIGHT_RATIO);
		float cloudStep = clouds.length - 1f;
		for(int i=0;i<clouds.length;i++) {
			clouds[clouds.length - i - 1] = new Cloud(height,
					MIN_DIAMETER_RATIO + DIAMETER_RATIO * (cloudStep - i) / cloudStep);
			height /= 2;
		}
	}

	@Override
	public boolean render(GraphicsContext g) {
		boolean isRunning = false;
		int backgroundColor = g.getBackgroundColor();
		long currentTimeMillis = Util.currentTimeMillis();
		g.removeBackgroundColor();
		for (Cloud cloud : clouds) {
			if (!cloud.render(g, backgroundColor, currentTimeMillis)) {
				if (run) {
					cloud.restart();
					isRunning = true;
				}
			} else {
				isRunning = true;
			}
		}
		return isRunning;
	}


	@Override
	public void stop() {
		run = false;
	}

	@Override
	public int getWeather() {
		return Weather.CLOUD;
	}

}
