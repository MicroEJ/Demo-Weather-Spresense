/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello.widget.animation;

import com.microej.example.hello.Model;
import com.microej.example.hello.style.StylePopulator;

import ej.bon.Util;
import ej.microui.display.GraphicsContext;

/**
 *
 */
public class CloudAnimation implements WeatherAnimation {


	private static final int CLOUD_COUNT = 4;
	private boolean run;
	private final Cloud[] clouds;


	/**
	 *
	 */
	public CloudAnimation() {
		run = true;
		clouds = new Cloud[CLOUD_COUNT];
		int height = (int) (StylePopulator.getTopHeight() * 1.5f);
		float cloudStep = clouds.length - 1f;
		for(int i=0;i<clouds.length;i++) {
			clouds[clouds.length - i - 1] = new Cloud(height, 0.4f + 0.6f * (cloudStep - i) / cloudStep);
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
		return Model.CLOUD;
	}

}
