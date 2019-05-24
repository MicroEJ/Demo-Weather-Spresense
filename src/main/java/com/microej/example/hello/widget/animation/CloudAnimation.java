/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello.widget.animation;

import com.microej.example.hello.Model;
import com.microej.example.hello.style.Colors;
import com.microej.example.hello.style.StylePopulator;

import ej.microui.display.GraphicsContext;

/**
 *
 */
public class CloudAnimation implements WeatherAnimation {


	private boolean run;
	private int currentX = 0;

	/**
	 *
	 */
	public CloudAnimation() {
		run = true;
	}

	@Override
	public boolean render(GraphicsContext g) {
		boolean isRunning = false;
		if (renderCloud(g)) {
			if (run) {
				restart();
				isRunning = true;
			}
		} else {
			isRunning = true;
		}
		return isRunning;
	}

	/**
	 *
	 */
	private void restart() {
		currentX = -10;
	}

	/**
	 * @param g
	 * @return
	 */
	private boolean renderCloud(GraphicsContext g) {
		g.setColor(Colors.WHITE);
		g.fillCircle(currentX, 0, 10);
		currentX += 5;
		return currentX > StylePopulator.getDisplayWidth();
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
