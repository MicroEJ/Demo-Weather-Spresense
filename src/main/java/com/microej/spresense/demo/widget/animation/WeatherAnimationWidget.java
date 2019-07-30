/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.widget.animation;

import com.microej.spresense.demo.Model;
import com.microej.spresense.demo.style.StylePopulator;
import com.microej.spresense.demo.widget.MainBackground;

import ej.animation.Animation;
import ej.animation.Animator;
import ej.audio.AudioFile;
import ej.audio.AudioFile.AudioChannel;
import ej.audio.AudioFile.AudioCodec;
import ej.audio.AudioFile.BitLength;
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

	public WeatherAnimationWidget() {
		runSoundThread();
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
			break;
		case Model.RAIN:
			animation = new RainAnimation();
			break;
		case Model.CLOUD:
		default:
			animation = new CloudAnimation();
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

	public AudioFile getAudioFileFromWeather() {
		switch (Model.getWeather()) {
		case Model.SUN:
			return new AudioFile(sunny, AudioChannel.AS_CHANNEL_STEREO, BitLength.BITLENGTH_16, 44100, AudioCodec.MP3);
		case Model.RAIN:
			return new AudioFile(rainy, AudioChannel.AS_CHANNEL_STEREO, BitLength.BITLENGTH_16, 44100, AudioCodec.MP3);
		case Model.CLOUD:
			return new AudioFile(cloudy, AudioChannel.AS_CHANNEL_STEREO, BitLength.BITLENGTH_16, 44100, AudioCodec.MP3);
		default:
			return new AudioFile(cloudy, AudioChannel.AS_CHANNEL_STEREO, BitLength.BITLENGTH_16, 44100, AudioCodec.MP3);
		}
	}

	private void changeSoundAnimation() {
		if (!animationNotChanged) {
			runSoundThread();
		}
	}

	private void runSoundThread() {
		if(soundThread != null) {
			audioPlayer.pause();
			try {
				soundThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		animationNotChanged = true;
		soundThread = new Thread() {
			@Override
			public void run() {
				while(animationNotChanged) {
					AudioFile file = getAudioFileFromWeather();
					audioPlayer.play(file, LOOP_INTERVAL);
				}
			}
		};
		soundThread.start();
		System.gc();
		long freeMemory = Runtime.getRuntime().freeMemory();
		System.out.println("free memory " + freeMemory);
	}
}
