/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello.widget.animation;

import com.microej.example.hello.Model;
import com.microej.example.hello.Util;
import com.microej.example.hello.style.Colors;
import com.microej.example.hello.style.StylePopulator;

import ej.color.GradientHelper;
import ej.microui.display.GraphicsContext;

public class RainAnimation implements WeatherAnimation {

	private static final float COUNT_DROPS = 0.02f;
	private static final int SPEED = 7;
	private static final int SMALL = 5;
	private static final int MEDIUM = 10;
	private static final int LARGE = 15;
	private final RainDrop[] slowDrops;
	private final RainDrop[] mediumDrops;
	private final RainDrop[] fastDrops;
	private boolean run;

	public RainAnimation() {
		run = true;
		int topHeight = StylePopulator.getTopHeight();
		int dropCount = (int) (COUNT_DROPS
				* (StylePopulator.getDisplayWidth() + topHeight + LARGE));
		slowDrops = new RainDrop[dropCount];
		mediumDrops = new RainDrop[dropCount];
		fastDrops = new RainDrop[dropCount];
		for(int i=0;i<dropCount;i++) {
			int length;
			switch (Util.RANDOM.nextInt(3)) {
			case 0:
				length = SMALL;
				break;
			case 1:
				length = MEDIUM;
				break;
			default:
				length = LARGE;
				break;
			}
			slowDrops[i] = new RainDrop(computeInitalX(), -Util.RANDOM.nextInt(topHeight), length, SPEED - 2);
			mediumDrops[i] = new RainDrop(computeInitalX(), -Util.RANDOM.nextInt(topHeight), length, SPEED - 1);
			fastDrops[i] = new RainDrop(computeInitalX(), -Util.RANDOM.nextInt(topHeight), length, SPEED);
		}
	}

	@Override
	public boolean render(GraphicsContext g) {
		long currentTimeMillis = ej.bon.Util.currentTimeMillis();
		int backgroundColor = g.getBackgroundColor();
		boolean isRunning = false;
		isRunning |= renderDrops(g, slowDrops, currentTimeMillis,
				GradientHelper.blendColors(backgroundColor, Colors.WHITE, 0.5f));
		isRunning |= renderDrops(g, mediumDrops, currentTimeMillis,
				GradientHelper.blendColors(Colors.WHITE, backgroundColor, 0.3f));
		isRunning |= renderDrops(g, fastDrops, currentTimeMillis, Colors.WHITE);
		return isRunning;
	}

	private boolean renderDrops(GraphicsContext g, RainDrop[] drops, long currentTimeMillis, int color) {
		boolean isRunning = false;
		g.setColor(color);
		for (int i = 0; i < drops.length; i++) {
			RainDrop rainDrop = drops[i];
			if (!rainDrop.render(g, currentTimeMillis)) {
				if (run) {
					rainDrop.restart(computeInitalX());
					isRunning = true;
				}
			} else {
				isRunning = true;
			}
		}
		return isRunning;
	}

	private int computeInitalX() {
		return Util.RANDOM.nextInt(StylePopulator.getDisplayWidth() + StylePopulator.getTopHeight());
	}

	@Override
	public void stop() {
		run = false;
	}

	@Override
	public int getWeather() {
		return Model.RAIN;
	}
}
