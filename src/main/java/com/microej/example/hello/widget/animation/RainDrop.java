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
	private final int speed;
	private final int initialY;
	private final int height;

	public RainDrop(int x, int y, int length, int speed, int height) {
		super();
		this.x = x;
		this.length = length;
		this.speed = speed;
		this.y = y;
		this.initialY = y;
		this.height = height;
	}

	public boolean render(GraphicsContext g, long time) {
		if (lastRender != 0) {
			g.drawLine(x, y, x + length, y - length);
			int diff = ((int) ((time - lastRender) << speed) >> 10) + 1;
			x -= diff;
			y += diff;
		}
		lastRender = time;
		return y - length < g.getClipHeight() && x > -length;
	}

	public int getSpeed() {
		return speed;
	}

	public int getLength() {
		return length;
	}

	public int getInitialY() {
		return initialY;
	}

	public int getHeight() {
		return height;
	}
}
