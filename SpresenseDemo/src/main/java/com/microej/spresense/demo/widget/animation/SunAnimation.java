/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.widget.animation;

import java.util.ArrayList;
import java.util.List;

import com.microej.spresense.demo.Weather;
import com.microej.spresense.demo.style.StylePopulator;

import ej.bon.Util;
import ej.microui.display.GraphicsContext;

/**
 * A sun animation.
 */
public class SunAnimation implements WeatherAnimation {

	private static final double MAX_SIZE_RATIO = 1.2;
	private static final int[] BEAMS_DURATION = { 1750, 1300, 1000 };
	private static final int[] BEAMS_SIZE = { 20, 30, 50 };
	private static final double[][] ANGLES = {
			{ Math.PI / 2, Math.PI / 5, 4 * Math.PI / 5 },
			{ Math.PI / 4, 3 * Math.PI / 4 },
			{ 3 * Math.PI / 8, 5 * Math.PI / 8, Math.PI/7, 6*Math.PI/7 }
	};

	private final SunBeam[] beams;
	private boolean run;

	/**
	 * Instantiates a {@link SunAnimation}.
	 */
	public SunAnimation() {
		run = true;
		int centerY = StylePopulator.getDisplayHeight() >> 1;
		int centerX = StylePopulator.getDisplayWidth() >> 1;

		List<SunBeam> list = new ArrayList<>();
		int maxSize = centerY + BEAMS_SIZE[BEAMS_SIZE.length - 1];
		for (int i = 0; i < BEAMS_SIZE.length; i++) {
			for (int j = 0; j < ANGLES[i].length; j++) {
				double angle = ANGLES[i][j];
				if(angle!=0) {
					list.add(new SunBeam((float) Math.cos(angle), (float) Math.sin(angle), BEAMS_SIZE[i], maxSize,
							centerX, centerY, BEAMS_DURATION[i]));
				}
			}
			maxSize *= MAX_SIZE_RATIO;
		}
		beams = list.toArray(new SunBeam[list.size()]);
	}

	@Override
	public boolean render(GraphicsContext g) {
		long currentTimeMillis = Util.currentTimeMillis();
		int backgroundColor = g.getBackgroundColor();
		boolean isRunning = false;
		for (SunBeam sunBeam : beams) {
			if (!sunBeam.render(g, backgroundColor, currentTimeMillis)) {
				if (run) {
					sunBeam.restart();
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
		return Weather.SUN;
	}
}
