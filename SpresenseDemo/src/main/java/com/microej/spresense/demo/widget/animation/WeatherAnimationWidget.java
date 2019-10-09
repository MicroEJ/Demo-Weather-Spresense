/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.widget.animation;

import java.io.IOException;

import com.microej.spresense.demo.Model;
import com.microej.spresense.demo.style.StylePopulator;
import com.microej.spresense.demo.widget.MainBackground;

import ej.animation.Animation;
import ej.animation.Animator;
import ej.audio.AudioFile;
import ej.audio.AudioPlayer;
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
	private static final int LOOP_INTERVAL = 30;
	private static final String sunny = "AUDIO/sunny.mp3";
	private static final String rainy= "AUDIO/rain.mp3";
	private static final String cloudy = "AUDIO/wind.mp3";
	private static final AudioPlayer audioPlayer = AudioPlayer.getInstance();

	private volatile boolean animationNotChanged;
	private Thread soundThread;

	private WeatherAnimation animation;
	private AudioFile file;

	public WeatherAnimationWidget() {
	}

	@Override
	public void renderContent(GraphicsContext g, Style style, Rectangle bounds) {
		g.setColor(Model.getColor(Model.getTime()));
		g.setBackgroundColor(g.getColor());
		g.fillRect(0, 0, bounds.getWidth(), bounds.getHeight());

		if (Model.getWeather() != animation.getWeather()) {
			animationNotChanged = false;
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
			file = new AudioFile(sunny, AudioFile.AS_CHANNEL_STEREO, AudioFile.BITLENGTH_16, 44100, AudioFile.AUDIO_CODEC_MP3);
			break;
		case Model.RAIN:
			animation = new RainAnimation();
			file = new AudioFile(rainy, AudioFile.AS_CHANNEL_STEREO, AudioFile.BITLENGTH_16, 44100, AudioFile.AUDIO_CODEC_MP3);
			break;
		case Model.CLOUD:
		default:
			animation = new CloudAnimation();
			file = new AudioFile(cloudy, AudioFile.AS_CHANNEL_STEREO, AudioFile.BITLENGTH_16, 44100, AudioFile.AUDIO_CODEC_MP3);
			break;
		}
		changeSoundAnimation();
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

	private void changeSoundAnimation() {
		if (!animationNotChanged) {
			runSoundThread();
		}
	}

	private void runSoundThread() {
		if(soundThread != null) {

			try {
				audioPlayer.pause();
				soundThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		animationNotChanged = true;
		soundThread = new Thread() {
			@Override
			public void run() {
				while(animationNotChanged) {
					try {
						audioPlayer.play(file, LOOP_INTERVAL);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		soundThread.start();
	}
}
