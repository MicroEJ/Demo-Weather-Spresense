/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello;

import ej.microui.display.GraphicsContext;
import ej.microui.display.shape.AntiAliasedShapes;
import ej.style.background.Background;
import ej.style.container.Rectangle;

/**
 *
 */
public class TriangleBackground implements Background {


	private static final int OFFSET = -4;
	private final int defaultColor;

	/**
	 * @param cOLOR_WHITE
	 */
	public TriangleBackground(int defaultColor) {
		this.defaultColor = defaultColor;
	}

	@Override
	public boolean isTransparent() {
		return false;
	}

	@Override
	public void apply(GraphicsContext g, Rectangle bounds, int color) {
		g.setColor(defaultColor);
		g.fillRect(0, 0, bounds.getWidth(), bounds.getHeight());
		int[] xy = new int[6];
		xy[0] = OFFSET;
		xy[1] = OFFSET;
		xy[2] = bounds.getWidth() + OFFSET;
		xy[3] = OFFSET;
		xy[4] = OFFSET;
		xy[5] = bounds.getHeight() + OFFSET;

		g.setColor(color);
		g.fillPolygon(xy);
		AntiAliasedShapes.Singleton.setFade(2);
		AntiAliasedShapes.Singleton.setThickness(2);
		AntiAliasedShapes.Singleton.drawLine(g, xy[2], xy[3], xy[4], xy[5]);
	}

}
