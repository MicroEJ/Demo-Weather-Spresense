/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello.widget.animation;

import ej.microui.display.GraphicsContext;

/**
 *
 */
public class RainDrop {

	private int x;
	private int y;
	private long lastRender;
	private final int length;
	private final int color;
	private final int speed;

	public RainDrop(int x, int y, int length, int color, int speed) {
		super();
		this.x = x;
		this.length = length;
		this.color = color;
		this.speed = speed;
		this.y = y;
	}

	public boolean render(GraphicsContext g, long time) {
		g.setColor(color);
		if (lastRender == 0) {
			lastRender = time;
		} else {
			g.drawLine(x, y, x + length, y - length);
			int diff = (int) ((time - lastRender) * speed / 1000) + 1;
			lastRender = time;
			x -= diff;
			y += diff;
		}
		return x > -length && y - length < g.getClipHeight();
	}

	/**
	 * Gets the color.
	 *
	 * @return the color.
	 */
	public int getColor() {
		return color;
	}

	/**
	 * Gets the speed.
	 *
	 * @return the speed.
	 */
	public int getSpeed() {
		return speed;
	}
}
