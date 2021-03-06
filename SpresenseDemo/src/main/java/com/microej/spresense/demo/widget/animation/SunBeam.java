/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.widget.animation;

import ej.color.GradientHelper;
import ej.library.ui.MicroEJColors;
import ej.microui.display.GraphicsContext;
import ej.microui.display.shape.AntiAliasedShapes;

/**
 * A sun beam.
 */
public class SunBeam {

	// Max duration of a beam
	private static final int FADE = 1;
	private static final int THICKNESS = 2;

	private final float cos;
	private final float sin;

	private final int centerY;
	private final int centerX;

	private long startRender;
	private final int lengthDiff;
	private final int initialR;
	private final int rDisplayed;
	private final float duration;


	/**
	 * Render a sun beam.
	 *
	 * @param cos
	 *            the cosinus of the beam.
	 * @param sin
	 *            the sinus of the beam.
	 * @param maxLengthDrawn
	 *            the maximum length to draw.
	 * @param maxR
	 *            the maximum ratio.
	 * @param centerX
	 *            the X center of the beam.
	 * @param centerY
	 *            the Y center of the beam.
	 * @param duration
	 *            the duration of the beam.
	 */
	public SunBeam(float cos, float sin, int maxLengthDrawn, int maxR, int centerX, int centerY, int duration) {
		this.cos = cos;
		this.sin = sin;
		this.centerX = centerX;
		this.centerY = centerY;
		this.initialR = maxLengthDrawn;
		this.duration = duration;
		this.lengthDiff = initialR >> 1;
		this.rDisplayed = maxR + initialR;
		startRender = 0;
	}

	/**
	 * Restart the render.
	 */
	public void restart() {
		startRender = 0;
	}

	/**
	 * Render the beam.
	 *
	 * @param g
	 *            the graphic context to render to.
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
			float ratio = Math.min(diff / duration, 1);
			g.setColor(GradientHelper.blendColors(MicroEJColors.WHITE, backgroundColor, ratio));
			double r = ratio * rDisplayed;
			double r2 = r + initialR - (ratio * lengthDiff);
			AntiAliasedShapes aliasedShapes = AntiAliasedShapes.Singleton;
			aliasedShapes.setFade(FADE);
			aliasedShapes.setThickness((int) ((1 - ratio) * THICKNESS + 1));
			aliasedShapes.drawLine(g, centerX + (int) (cos * r), centerY - (int) (sin * r), centerX + (int) (cos * r2),
					centerY - (int) (sin * r2));
			isRunning = ratio < 1;
		}
		return isRunning;
	}

}
