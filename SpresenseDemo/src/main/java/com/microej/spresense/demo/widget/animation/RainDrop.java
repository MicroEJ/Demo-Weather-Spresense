/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.widget.animation;

import ej.microui.display.GraphicsContext;

/**
 * A rain drop.
 */
public class RainDrop {

	private static final int DROP_SPEED = 10;
	private int x;
	private int y;
	private long lastRender;
	private final int length;
	private final int speed;
	private final int initialY;

	/**
	 * Instantiates a {@link RainDrop}.
	 *
	 * @param x
	 *            the x coordinate of the start of the drop.
	 * @param y
	 *            the y coordinate of the start of the drop.
	 * @param length
	 *            the length of the drop.
	 * @param speed
	 *            the speed of the drop.
	 */
	public RainDrop(int x, int y, int length, int speed) {
		super();
		this.x = x;
		this.length = length;
		this.speed = speed;
		this.y = y;
		this.initialY = y;
	}

	/**
	 * Render the drop.
	 *
	 * @param g
	 *            the graphic context to render to.
	 * @param time
	 *            the time.
	 * @return <code>true</code> if the animation is still running.
	 */
	public boolean render(GraphicsContext g, long time) {
		if (lastRender != 0) {
			g.drawLine(x, y, x + length, y - length);
			int diff = ((int) ((time - lastRender) << speed) >> DROP_SPEED) + 1;
			x -= diff;
			y += diff;
		}
		lastRender = time;
		return y - length < g.getClipHeight() && x > -length;
	}

	/**
	 * Gets the drop length.
	 *
	 * @return the drop length.
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Restart the drop.
	 *
	 * @param x
	 *            the initial X.
	 */
	public void restart(int x) {
		lastRender = 0;
		this.x = x;
		this.y = initialY;
	}
}
