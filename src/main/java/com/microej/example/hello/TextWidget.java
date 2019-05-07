/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello;

import java.util.Calendar;

import ej.animation.Animation;
import ej.animation.Animator;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.style.Style;
import ej.style.container.Rectangle;
import ej.style.util.ElementAdapter;
import ej.style.util.StyleHelper;
import ej.util.text.EnglishDateFormatSymbols;
import ej.widget.StyledWidget;

/**
 *
 */
public class TextWidget extends StyledWidget implements Animation {

	private final ElementAdapter temperatureElement = new ElementAdapter(this);
	private final ElementAdapter formatElement = new ElementAdapter(this);
	private final ElementAdapter amPmElement = new ElementAdapter(this);

	/**
	 *
	 */
	public TextWidget() {
		temperatureElement.addClassSelector(StylePopulator.CS_LARGE_TEXT);
		formatElement.addClassSelector(StylePopulator.CS_MEDIUM_TEXT);
		amPmElement.addClassSelector(StylePopulator.CS_XSMALL_TEXT);
	}

	@Override
	public void renderContent(GraphicsContext g, Style style, Rectangle bounds) {
		g.setColor(style.getForegroundColor());
		Font font = StyleHelper.getFont(style);
		g.setFont(font);
		int y = -5;
		g.drawString(Model.getLocation(), 0, y, GraphicsContext.LEFT_TOP);
		y += 5;
		font = StyleHelper.getFont(temperatureElement.getStyle());
		g.setFont(font);
		String str = "" + Model.getTemperature();
		g.drawString(str, 0, y, GraphicsContext.LEFT_TOP);
		g.setFont(StyleHelper.getFont(formatElement.getStyle()));
		g.drawString("F", font.stringWidth(str) + 3, y + 15, GraphicsContext.LEFT_TOP);
		y += font.getBaselinePosition() + 27;
		font = StyleHelper.getFont(style);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Model.getCurrentTime());
		g.setFont(font);
		int hour = calendar.get(Calendar.HOUR);
		if (hour == 0) {
			hour = 12;
		}
		int minutes = calendar.get(Calendar.MINUTE);
		String hourText = getPadding(hour) + ":" + getPadding(minutes);
		g.drawString(hourText, 0, y, GraphicsContext.LEFT_TOP);
		g.setFont(StyleHelper.getFont(amPmElement.getStyle()));
		int AM_PM = calendar.get(Calendar.AM_PM);
		g.drawString(EnglishDateFormatSymbols.getInstance().getAmPmString(AM_PM),
				font.stringWidth(hourText) + 3, y + ((AM_PM == Calendar.AM) ? 2 : 10), GraphicsContext.LEFT_TOP);
		y += 23;
		g.setFont(StyleHelper.getFont(style));
		g.drawString(EnglishDateFormatSymbols.getInstance().getWeekday(calendar.get(Calendar.DAY_OF_WEEK) - 1), 0, y,
				GraphicsContext.LEFT_TOP);
	}

	/**
	 * @param hour
	 * @return
	 */
	private String getPadding(int hour) {
		String text = (hour < 10) ? "0" : "";
		return text + hour;
	}

	@Override
	public Rectangle validateContent(Style style, Rectangle bounds) {
		return bounds;
	}

	@Override
	public void showNotify() {
		super.showNotify();
		ServiceLoaderFactory.getServiceLoader().getService(Animator.class).startAnimation(this);
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
