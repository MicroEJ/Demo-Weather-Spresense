/*
 * Java
 *
 * Copyright 2019 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microej.example.hello;

import com.microej.gnss.GnssManager;
import com.microej.gnss.LocalizationData;

import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.util.EventHandler;

public class DrawnGui  extends Displayable implements EventHandler{
	private static final int PADDING_TEXT = 5;
	private final int width;
	private final int height;
	private final int color;
	private static final int pauseDelay = 500;
	private static final int repeatDelay = 1;


	public DrawnGui() {
		super(Display.getDefaultDisplay());
		new GnssManager();
		GnssManager.switchOn();

		width = getDisplay().getWidth();
		height = getDisplay().getHeight();

		color = 0x01b9ff;

		Timer timer = ServiceLoaderFactory.getServiceLoader().getService(Timer.class, Timer.class);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					GnssManager.readPosition();
				} catch(ej.sni.NativeException e) {

				}
				repaint();
				show();
			}
		}, pauseDelay, repeatDelay);
	}

	public void drawSun(GraphicsContext g) {
		g.setColor(Colors.WHITE);
		int radius = (int)((height/4)*0.9);
		g.drawCircle(2*width/3, height/4,  radius);
		// g.
	}


	@Override
	public void paint(GraphicsContext g) {
		// clean
		g.setColor(color);
		g.fillRect(0, 0, width, height);

		Font font = Font.getFont(Font.LATIN, 48, Font.STYLE_PLAIN);

		g.setFont(font);
		g.setColor(Colors.WHITE);

		int y = height / 6;
		y += font.getHeight() + PADDING_TEXT;
		g.drawString("Nantes", width / 6, y,GraphicsContext.LEFT_BOTTOM );
		y +=font.getHeight() + PADDING_TEXT;

		g.drawString("France", width / 6, y,GraphicsContext.LEFT_BOTTOM );
		y +=font.getHeight() + PADDING_TEXT;

		y = 2*height / 3;
		y += font.getHeight() + PADDING_TEXT;

		String timeElapsed =  "Hours: "+LocalizationData.getTimeHours()+", Minutes: "+LocalizationData.getTimeMinutes()+", Seconds: "+LocalizationData.getTimeSeconds();
		g.drawString(timeElapsed, width / 6, y,GraphicsContext.LEFT_BOTTOM );
		y +=font.getHeight() + PADDING_TEXT;

		String message = LocalizationDataToStr(GnssManager.getLatitude());
		g.drawString(message, width / 6, y,GraphicsContext.LEFT_BOTTOM );
		y +=font.getHeight() + PADDING_TEXT;

		message = LocalizationDataToStr(GnssManager.getLongitude());
		g.drawString(message, width / 6, y,GraphicsContext.LEFT_BOTTOM );
		y +=font.getHeight() + PADDING_TEXT;

		drawSun(g);

	}
	@Override
	public boolean handleEvent(int event) {
		return false;
	}
	@Override
	public EventHandler getController() {
		return this;
	}
	public String LocalizationDataToStr(LocalizationData data) {
		String str ="";
		switch(data.getType()) {
		case LAT:
			str = ">LAT "+data.getLocalizationDegree()+"."+data.getLocalizationMinute()+"."+data.getLocalizationFrac();
			break;
		case LONG:
			str = ">LONG "+data.getLocalizationDegree()+"."+data.getLocalizationMinute()+"."+data.getLocalizationFrac();
			break;
		case INVALID:
			str = "No position data";
		default:
		}
		return str;
	}


}
