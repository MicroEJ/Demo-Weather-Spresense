/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.widget.animation;

import com.microej.spresense.demo.style.StylePopulator;
import com.microej.spresense.demo.util.Util;

import ej.color.GradientHelper;
import ej.library.ui.MicroEJColors;
import ej.microui.display.GraphicsContext;
import ej.microui.display.shape.AntiAliasedShapes;

/**
 * A cloud.
 */
public class Cloud {

	private static final int FADE = 2;
	private static final int THICKNESS = 2;
	private static final float COLOR_THREADHOLD = 0.2f;
	private static final int DURATION = 5000;
	private static final int MIN_DURATION = 1000;

	private final int diameter;
	private final float color;
	private int xInitial;
	private int y;
	private int travelLength;
	private long startRender;
	private float duration;

	/**
	 * Instantiates a cloud.
	 *
	 * @param diameter
	 *            diameter of the cloud.
	 * @param color
	 *            color of the cloud to used.
	 */
	public Cloud(int diameter, float color) {
		this.diameter = diameter;
		this.color = color;
		generateValues();
	}

	/**
	 * Restart the animation of the cloud.
	 */
	public void restart() {
		generateValues();
	}

	/**
	 * Render the cloud.
	 *
	 * @param g
	 *            the graphic context to draw to.
	 * @param backgroundColor
	 *            the background color.
	 * @param currentTime
	 *            the current time.
	 * @return <code>true</code> if the animation is still running.
	 */
	public boolean render(GraphicsContext g, int backgroundColor, long currentTime) {
		boolean isRunning = true;
		if (startRender == 0) {
			startRender = currentTime;
		} else {
			long diff = currentTime - startRender;
			isRunning = diff < duration;
			if (isRunning) {
				int clipHeight = g.getClipHeight();
				int clipWidth = g.getClipWidth();
				int clipX = g.getClipX();
				int clipY = g.getClipY();
				int halfDiameter = diameter / 2;
				int height = halfDiameter + y;
				g.setClip(clipX, clipY, clipWidth, height);
				float ratio = Math.min(diff / duration, 1);
				g.setColor(GradientHelper.blendColors(backgroundColor, MicroEJColors.WHITE, getRatio(color, ratio)));
				AntiAliasedShapes.Singleton.setThickness(THICKNESS);
				AntiAliasedShapes.Singleton.setFade(FADE);
				int x = (int) (xInitial + travelLength * ratio);
				AntiAliasedShapes.Singleton.drawCircle(g, x, y, diameter);
				g.setClip(clipX, clipY, clipWidth, clipHeight);
				AntiAliasedShapes.Singleton.drawLine(g, x, y + halfDiameter, x + diameter,
						y + halfDiameter);
			}
		}

		return isRunning;
	}

	private void generateValues() {
		startRender = 0;
		xInitial = Util.RANDOM.nextInt(StylePopulator.getDisplayWidth()) - diameter;
		int minY = diameter >> 2;
			y = Util.RANDOM.nextInt(StylePopulator.getTopHeight() - minY) + minY - diameter / 2;
			travelLength = diameter + Util.RANDOM.nextInt(StylePopulator.getDisplayWidth());
			duration = MIN_DURATION + Util.RANDOM.nextInt(DURATION);
	}

	private static float getRatio(float wantedRatio, float animationRatio) {
		float ratio = wantedRatio;
		if (animationRatio < COLOR_THREADHOLD) {
			ratio = wantedRatio * (animationRatio / COLOR_THREADHOLD);
		} else if (animationRatio > 1 - COLOR_THREADHOLD) {
			ratio = wantedRatio * ((1 - animationRatio) / COLOR_THREADHOLD);
		}
		return ratio;
	}
}
