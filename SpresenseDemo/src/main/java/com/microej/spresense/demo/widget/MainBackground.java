/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.widget;

import com.microej.spresense.demo.style.Images;
import com.microej.spresense.demo.style.StylePopulator;

import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.display.shape.AntiAliasedShapes;
import ej.style.background.Background;
import ej.style.container.Rectangle;
import ej.style.util.StyleHelper;

/**
 * A background with some shapes.
 */
public class MainBackground implements Background {

	private static final int THICKNESS = 3;
	private static final int FADE = 1;
	private static final int BOTTOM_ARC_ANGLE = 127;
	private static final int BOTTOM_ARC_START = 180;
	private static final int TOP_ARC_ANGLE = -139;
	private static final int TOP_ARC_START = 125;
	private static final int TOP_LEFT_LINE_X_LENGTH = 22;
	private static final int TOP_RIGHT_LINE_X_OFFSET = 78;
	private static final int BOTTOM_LEFT_LINE_Y_OFFSET = 60;
	private static final int BOTTOM_LEFT_LINE_X_LENGTH = 1;
	private static final int BOTTOM_RIGHT_LINE_Y_OFFSET = 76;
	private static final int BOTTOM_RIGHT_LINE_X_OFFSET = 77;
	private static final int BOTTOM_RIGHT_LINE_X_LENGTH = 28;
	private static final int BORDER_THICKNESS = 4;

	/**
	 * Diameter of the center circle.
	 */
	public static final int CIRCLE_DIAMETER = 99;

	private final int borderColor;

	/**
	 * Instantiates a {@link MainBackground}.
	 *
	 * @param borderColor
	 *            the color to use for the border.
	 */
	public MainBackground(int borderColor) {
		this.borderColor = borderColor;
	}

	@Override
	public boolean isTransparent() {
		return false;
	}

	@Override
	public void apply(GraphicsContext g, Rectangle bounds, int color) {
		g.setColor(color);
		int width = bounds.getWidth();
		int height = bounds.getHeight();
		g.fillRect(0, 0, width, height);
		g.setBackgroundColor(color);
		g.setColor(borderColor);
		AntiAliasedShapes antiAliasedShapes = getAntiAliased();

		// Draw logo
		Image image = StyleHelper.getImage(Images.LOGO);
		g.drawImage(image, width - StylePopulator.DEFAULT_OUTLINE, height - (StylePopulator.DEFAULT_OUTLINE << 1),
				GraphicsContext.RIGHT_BOTTOM);

		// Draw circle
		int topCircleX = (StylePopulator.getDisplayWidth() - CIRCLE_DIAMETER) >> 1;
		int topCircleY = ((StylePopulator.getDisplayHeight() - CIRCLE_DIAMETER) >> 1)
				- (StylePopulator.getDisplayHeight() - height);
		antiAliasedShapes.drawCircleArc(g, topCircleX, topCircleY, CIRCLE_DIAMETER, TOP_ARC_START, TOP_ARC_ANGLE);
		antiAliasedShapes.drawCircleArc(g, topCircleX, topCircleY, CIRCLE_DIAMETER, BOTTOM_ARC_START, BOTTOM_ARC_ANGLE);

		// Draw bottom lines
		int bottomRightLineXEnd = topCircleX + BOTTOM_RIGHT_LINE_X_OFFSET + BOTTOM_RIGHT_LINE_X_LENGTH;
		antiAliasedShapes.drawLine(g, topCircleX + BOTTOM_RIGHT_LINE_X_OFFSET, topCircleY + BOTTOM_RIGHT_LINE_Y_OFFSET,
				bottomRightLineXEnd,
				topCircleY + BOTTOM_RIGHT_LINE_Y_OFFSET);
		int dotSpacing = (StylePopulator.getDisplayWidth() - bottomRightLineXEnd - StylePopulator.DEFAULT_OUTLINE)
				/ StylePopulator.COUNT_OF_HOUR_VALUES;
		antiAliasedShapes.drawPoint(g, bottomRightLineXEnd + dotSpacing, topCircleY + BOTTOM_RIGHT_LINE_Y_OFFSET);
		antiAliasedShapes.drawPoint(g, bottomRightLineXEnd + (dotSpacing << 1),
				topCircleY + BOTTOM_RIGHT_LINE_Y_OFFSET);
		g.fillRect(0, topCircleY + BOTTOM_LEFT_LINE_Y_OFFSET, topCircleX + BOTTOM_LEFT_LINE_X_LENGTH, BORDER_THICKNESS);

		// Draw top lines
		g.fillRect(0, 0, topCircleX + TOP_LEFT_LINE_X_LENGTH, BORDER_THICKNESS);
		g.fillRect(topCircleX + TOP_RIGHT_LINE_X_OFFSET, 0, width - (topCircleX +TOP_RIGHT_LINE_X_OFFSET), BORDER_THICKNESS);
	}

	/**
	 * Get the antialiased shapes used for the lines.
	 *
	 * @return the antialized shapes.
	 */
	public static AntiAliasedShapes getAntiAliased() {
		AntiAliasedShapes antiAliasedShapes = AntiAliasedShapes.Singleton;
		antiAliasedShapes.setFade(FADE);
		antiAliasedShapes.setThickness(THICKNESS);
		return antiAliasedShapes;
	}

}
