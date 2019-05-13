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

import ej.basictool.ArrayTools;
import ej.bon.Util;
import ej.color.GradientHelper;
import ej.microui.display.GraphicsContext;

public class RainAnimation {

	private static final float COUNT_DROPS = 0.01f;
	private static final int SPEED = 50;
	private static final int SMALL = 5;
	private static final int MEDIUM = 10;
	private static final int LARGE = 15;
	private RainDrop[] slowDrops;
	private RainDrop[] mediumDrops;
	private RainDrop[] fastDrops;
	private final Random random;

	public RainAnimation() {
		int dropCount = (int) (COUNT_DROPS
				* (StylePopulator.getDisplayWidth() + StylePopulator.getTopHeight() + LARGE));
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
			slowDrops[i] = generateDrop(-random.nextInt(StylePopulator.getTopHeight()), length, SPEED / 3);
			mediumDrops[i] = generateDrop(-random.nextInt(StylePopulator.getTopHeight()), length, SPEED / 2);
			fastDrops[i] = generateDrop(-random.nextInt(StylePopulator.getTopHeight()), length, SPEED);
		}
	}

	public void render(GraphicsContext g) {
		long currentTimeMillis = Util.currentTimeMillis();
		int backgroundColor = g.getBackgroundColor();
		g.setColor(GradientHelper.blendColors(backgroundColor, Colors.WHITE, 0.5f));
		for (RainDrop rainDrop : slowDrops) {
			if (!rainDrop.render(g, currentTimeMillis)) {
				RainDrop[] remove = ArrayTools.remove(slowDrops, rainDrop);
				slowDrops = ArrayTools.add(remove,
						generateDrop(rainDrop.getInitialY(), rainDrop.getLength(), rainDrop.getSpeed()));
			}
		}
		g.setColor(GradientHelper.blendColors(Colors.WHITE, backgroundColor, 0.3f));
		for (RainDrop rainDrop : mediumDrops) {
			if (!rainDrop.render(g, currentTimeMillis)) {
				RainDrop[] remove = ArrayTools.remove(mediumDrops, rainDrop);
				mediumDrops = ArrayTools.add(remove,
						generateDrop(rainDrop.getInitialY(), rainDrop.getLength(), rainDrop.getSpeed()));
			}
		}
		g.setColor(Colors.WHITE);
		for (RainDrop rainDrop : fastDrops) {
			if (!rainDrop.render(g, currentTimeMillis)) {
				RainDrop[] remove = ArrayTools.remove(fastDrops, rainDrop);
				fastDrops = ArrayTools.add(remove,
						generateDrop(rainDrop.getInitialY(), rainDrop.getLength(), rainDrop.getSpeed()));
			}
		}
	}

	private RainDrop generateDrop(int initialY, int length, int speed) {
		return new RainDrop(random.nextInt(StylePopulator.getDisplayWidth() + StylePopulator.getTopHeight() + length),
				initialY,
				length, speed);
	}

}
