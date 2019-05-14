/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello.widget.animation;

import java.util.Random;

import com.microej.example.hello.style.Colors;
import com.microej.example.hello.style.StylePopulator;

import ej.bon.Util;
import ej.color.GradientHelper;
import ej.microui.display.GraphicsContext;

public class RainAnimation {

	private static final float COUNT_DROPS = 0.02f;
	private static final int SPEED = 7;
	private static final int SMALL = 5;
	private static final int MEDIUM = 10;
	private static final int LARGE = 15;
	private final RainDrop[] slowDrops;
	private final RainDrop[] mediumDrops;
	private final RainDrop[] fastDrops;
	private final Random random;

	public RainAnimation() {
		int topHeight = StylePopulator.getTopHeight();
		int dropCount = (int) (COUNT_DROPS
				* (StylePopulator.getDisplayWidth() + topHeight + LARGE));
		slowDrops = new RainDrop[dropCount];
		mediumDrops = new RainDrop[dropCount];
		fastDrops = new RainDrop[dropCount];
		random = new Random();
		for(int i=0;i<dropCount;i++) {
			int length;
			switch (random.nextInt(3)) {
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
			slowDrops[i] = generateDrop(-random.nextInt(topHeight), length, SPEED - 2, topHeight);
			mediumDrops[i] = generateDrop(-random.nextInt(topHeight), length, SPEED - 1, topHeight);
			fastDrops[i] = generateDrop(-random.nextInt(topHeight), length, SPEED, topHeight);
		}
	}

	public void render(GraphicsContext g) {
		long currentTimeMillis = Util.currentTimeMillis();
		int backgroundColor = g.getBackgroundColor();
		renderDrops(g, slowDrops, currentTimeMillis, GradientHelper.blendColors(backgroundColor, Colors.WHITE, 0.5f));
		renderDrops(g, mediumDrops, currentTimeMillis, GradientHelper.blendColors(Colors.WHITE, backgroundColor, 0.3f));
		renderDrops(g, fastDrops, currentTimeMillis, Colors.WHITE);
	}

	private void renderDrops(GraphicsContext g, RainDrop[] drops, long currentTimeMillis, int color) {
		g.setColor(color);
		for (int i = 0; i < drops.length; i++) {
			RainDrop rainDrop = drops[i];
			if (!rainDrop.render(g, currentTimeMillis)) {
				drops[i] = generateDrop(rainDrop.getInitialY(), rainDrop.getLength(), rainDrop.getSpeed(),
						rainDrop.getHeight());
			}
		}
	}

	private RainDrop generateDrop(int initialY, int length, int speed, int height) {
		return new RainDrop(random.nextInt(StylePopulator.getDisplayWidth() + StylePopulator.getTopHeight() + length),
				initialY,
				length, speed, height);
	}

}
