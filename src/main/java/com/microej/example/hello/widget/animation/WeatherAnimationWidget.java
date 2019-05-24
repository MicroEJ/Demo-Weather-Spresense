/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello.widget.animation;

import com.microej.example.hello.Model;
import com.microej.example.hello.style.StylePopulator;
import com.microej.example.hello.widget.MainBackground;

import ej.animation.Animation;
import ej.animation.Animator;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.microui.display.GraphicsContext;
import ej.microui.display.shape.AntiAliasedShapes;
import ej.style.Style;
import ej.style.container.Rectangle;
import ej.widget.StyledWidget;

/**
 *
 */
public class WeatherAnimationWidget extends StyledWidget implements Animation {

	private WeatherAnimation animation;

	public WeatherAnimationWidget() {
	}

	@Override
	public void renderContent(GraphicsContext g, Style style, Rectangle bounds) {
		g.setColor(Model.getColor(Model.getTime()));
		g.setBackgroundColor(g.getColor());
		g.fillRect(0, 0, bounds.getWidth(), bounds.getHeight());

		if (Model.getWeather() != animation.getWeather()) {
			animation.stop();
		}
		if (!animation.render(g)) {
			startAnimation();
		}

		g.setColor(style.getBackgroundColor());
		int circleX = (StylePopulator.getDisplayWidth() - MainBackground.CIRCLE_DIAMETER) / 2;
		int circleY = (StylePopulator.getDisplayHeight() - MainBackground.CIRCLE_DIAMETER) / 2;
		g.fillCircle(circleX, circleY, MainBackground.CIRCLE_DIAMETER);
		g.setColor(style.getBorderColor());
		g.removeBackgroundColor();
		AntiAliasedShapes antiAliased = MainBackground.getAntiAliased();
		antiAliased.drawCircleArc(g, circleX, circleY, MainBackground.CIRCLE_DIAMETER, 125, -70);
	}

	@Override
	public Rectangle validateContent(Style style, Rectangle bounds) {
		return bounds;
	}

	@Override
	public boolean isTransparent() {
		return false;
	}

	@Override
	public void showNotify() {
		super.showNotify();
		startAnimation();
		ServiceLoaderFactory.getServiceLoader().getService(Animator.class).startAnimation(this);
	}

	private void startAnimation() {
		switch (Model.getWeather()) {
		case Model.SUN:
			animation = new SunAnimation();
			break;
		case Model.RAIN:
			animation = new RainAnimation();
			break;
		case Model.CLOUD:
		default:
			animation = new CloudAnimation();
			break;
		}
	}

	@Override
	public void hideNotify() {
		super.hideNotify();
		ServiceLoaderFactory.getServiceLoader().getService(Animator.class).stopAnimation(this);
	}

	@Override
	public boolean tick(long currentTimeMillis) {
		repaint();
		return true;
	}

}
