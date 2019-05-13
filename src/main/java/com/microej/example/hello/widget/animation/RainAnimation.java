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
import ej.microui.display.GraphicsContext;

public class RainAnimation {

	private static final float COUNT_DROPS = 0.021f;
	private static final int SPEED = 50;
	private static final int SMALL = 5;
	private static final int MEDIUM = 10;
	private static final int LARGE = 15;
	private RainDrop[] drops;
	private final Random random;

	public RainAnimation() {
		int dropCount = (int) (COUNT_DROPS
				* (StylePopulator.getDisplayWidth() + StylePopulator.getTopHeight() + LARGE));
		drops = new RainDrop[dropCount*3];
		random = new Random();
		for(int i=0;i<dropCount;i++) {
			drops[i] = generateDrop(SPEED, Colors.WHITE);
		}
		for (int i = dropCount; i < dropCount * 2; i++) {
			drops[i] = generateDrop(SPEED / 2, Colors.GREY);
		}
		for (int i = dropCount * 2; i < dropCount * 3; i++) {
			drops[i] = generateDrop(SPEED / 3, Colors.GREY_LIGHT);
		}
	}

	public void render(GraphicsContext g) {
		long currentTimeMillis = Util.currentTimeMillis();
		for (RainDrop rainDrop : drops) {
			if (!rainDrop.render(g, currentTimeMillis)) {
				RainDrop[] remove = ArrayTools.remove(drops, rainDrop);
				drops = ArrayTools.add(remove, generateDrop(rainDrop.getSpeed(), rainDrop.getColor()));
			}
		}
	}

	private RainDrop generateDrop(int speed, int color) {
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
		return new RainDrop(random.nextInt(StylePopulator.getDisplayWidth() + StylePopulator.getTopHeight() + length),
				-random.nextInt(StylePopulator.getTopHeight()),
				length, speed, color);
	}

}
