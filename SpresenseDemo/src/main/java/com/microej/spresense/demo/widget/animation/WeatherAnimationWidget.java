/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.widget.animation;

import java.util.Observable;
import java.util.Observer;

import com.microej.spresense.demo.model.DemoModel;
import com.microej.spresense.demo.model.Model;
import com.microej.spresense.demo.model.Weather;
import com.microej.spresense.demo.style.StylePopulator;
import com.microej.spresense.demo.widget.MainBackground;

import ej.animation.Animation;
import ej.animation.Animator;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.microui.display.GraphicsContext;
import ej.microui.display.shape.AntiAliasedShapes;
import ej.style.Style;
import ej.style.container.Rectangle;
import ej.widget.StyledWidget;

/**
 * A widget displaying the animation of the current weather.
 */
public class WeatherAnimationWidget extends StyledWidget implements Animation, Observer {

	private static final int END_ANGLE = -70;
	private static final int START_ANGLE = 125;
	private WeatherAnimation animation;

	@Override
	public void renderContent(GraphicsContext g, Style style, Rectangle bounds) {
		Model model = ServiceLoaderFactory.getServiceLoader().getService(Model.class);
		g.setColor(DemoModel.getColor(model.getTime()));
		g.setBackgroundColor(g.getColor());
		g.fillRect(0, 0, bounds.getWidth(), bounds.getHeight());

		if (!this.animation.render(g)) {
			startAnimation();
		}

		g.setColor(style.getBackgroundColor());
		int circleX = (StylePopulator.getDisplayWidth() - MainBackground.CIRCLE_DIAMETER) >> 1;
		int circleY = (StylePopulator.getDisplayHeight() - MainBackground.CIRCLE_DIAMETER) >> 1;
		g.fillCircle(circleX, circleY, MainBackground.CIRCLE_DIAMETER);
		g.setColor(style.getBorderColor());
		g.removeBackgroundColor();
		AntiAliasedShapes antiAliased = MainBackground.getAntiAliased();
		antiAliased.drawCircleArc(g, circleX, circleY, MainBackground.CIRCLE_DIAMETER, START_ANGLE, END_ANGLE);
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
		Model model = ServiceLoaderFactory.getServiceLoader().getService(Model.class);
		model.addObserver(this);
		ServiceLoaderFactory.getServiceLoader().getService(Animator.class).startAnimation(this);
	}

	@Override
	public void hideNotify() {
		super.hideNotify();
		Model model = ServiceLoaderFactory.getServiceLoader().getService(Model.class);
		model.deleteObserver(this);
		ServiceLoaderFactory.getServiceLoader().getService(Animator.class).stopAnimation(this);
	}

	@Override
	public boolean tick(long currentTimeMillis) {
		repaint();
		return true;
	}

	private void startAnimation() {
		Model model = ServiceLoaderFactory.getServiceLoader().getService(Model.class);
		switch (model.getWeather()) {
		case Weather.SUN:
			this.animation = new SunAnimation();
			break;
		case Weather.RAIN:
			this.animation = new RainAnimation();
			break;
		case Weather.CLOUD:
		default:
			this.animation = new CloudAnimation();
			break;
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		WeatherAnimation animation = this.animation;
		Model model = ServiceLoaderFactory.getServiceLoader().getService(Model.class);
		if (animation != null && model.getWeather() != animation.getWeather()) {
			animation.stop();
		}
	}
}
