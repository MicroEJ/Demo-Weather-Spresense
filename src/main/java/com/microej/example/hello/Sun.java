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
import ej.style.Style;
import ej.style.container.AlignmentHelper;
import ej.style.container.Rectangle;
import ej.widget.StyledWidget;

/**
 *
 */
public class Sun extends StyledWidget {

	private static final int LIGHT_SIZE = 12;
	private static final int OFFSET = 20;
	private static final double SIN_PI_4 = Math.sin(Math.PI / 4);
	private static int percent = 0;

	@Override
	public void renderContent(GraphicsContext g, Style style, Rectangle bounds) {
		percent++;
		if (percent > 100) {
			percent = 0;
		}

		int size = Math.min(bounds.getHeight(), bounds.getWidth());
		int xOffset = AlignmentHelper.computeXLeftCorner(size, 0, bounds.getWidth(), style.getAlignment());
		int yOffset = AlignmentHelper.computeYTopCorner(size, 0, bounds.getHeight(), style.getAlignment());

		g.removeBackgroundColor();
		g.setColor(style.getForegroundColor());

		AntiAliasedShapes singleton = AntiAliasedShapes.Singleton;
		singleton.setFade(1);
		singleton.setThickness(2);

		// Vertical lights
		int halfSize = size / 2;
		int xCenter = xOffset + halfSize;
		int yCenter = yOffset + halfSize;

		singleton.drawLine(g, xCenter, yOffset, xCenter, yOffset + LIGHT_SIZE);
		singleton.drawLine(g, xCenter, yOffset + size, xCenter, yOffset + size - LIGHT_SIZE);

		// Horizontal lights
		singleton.drawLine(g, xOffset, yCenter, xOffset + LIGHT_SIZE, yCenter);
		singleton.drawLine(g, xOffset + size, yCenter, xOffset + size - LIGHT_SIZE, yCenter);

		// Sin PI/4 == cos PI/4 so we will only use Sin
		int xTiledOffset1 = (int) Math.round(SIN_PI_4 * halfSize);
		int yTiledOffset1 = xTiledOffset1;
		int xTiledOffset2 = (int) Math.round(SIN_PI_4 * (halfSize - LIGHT_SIZE));
		int yTiledOffset2 = xTiledOffset2;

		// Diagonal
		singleton.drawLine(g, xCenter - xTiledOffset1, yCenter - yTiledOffset1, xCenter - xTiledOffset2,
				yCenter - yTiledOffset2);
		singleton.drawLine(g, xCenter + xTiledOffset1, yCenter + yTiledOffset1, xCenter + xTiledOffset2,
				yCenter + yTiledOffset2);
		singleton.drawLine(g, xCenter + xTiledOffset1, yCenter - yTiledOffset1, xCenter + xTiledOffset2,
				yCenter - yTiledOffset2);
		singleton.drawLine(g, xCenter - xTiledOffset1, yCenter + yTiledOffset1, xCenter - xTiledOffset2,
				yCenter + yTiledOffset2);


		// Circle
		singleton.setFade(2);
		singleton.setThickness(14);
		singleton.drawCircle(g, xOffset + LIGHT_SIZE + OFFSET, yOffset + LIGHT_SIZE + OFFSET,
				size - (LIGHT_SIZE << 1) - (OFFSET << 1));
	}

	@Override
	public Rectangle validateContent(Style style, Rectangle bounds) {
		return bounds;
	}
}
