/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo;

import com.microej.spresense.demo.fake.FakeWeatherProvider;
import com.microej.spresense.demo.style.StylePopulator;
import com.microej.spresense.demo.widget.MainFrame;

import ej.animation.Animation;
import ej.animation.Animator;
import ej.audio.AudioFile;
import ej.audio.AudioFile.AudioChannel;
import ej.audio.AudioFile.AudioCodec;
import ej.audio.AudioFile.BitLength;
import ej.audio.AudioPlayer;
import ej.audio.Playlist;
import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.microui.MicroUI;
import ej.widget.StyledDesktop;
import ej.widget.StyledPanel;


public class SpresenseDemo {
	private static long INIT = 1557844987038L;
	private static final int LOOP_INTERVAL = 10;
	private static final String sunny = "AUDIO/sunny.mp3";
	private static AudioPlayer audioPlayer;

	public static void main(String[] args) {
		if (System.currentTimeMillis() < INIT) {
			ej.bon.Util.setCurrentTimeMillis(INIT);
		}
		FakeWeatherProvider.getWeather(0, 0);
		ServiceLoaderFactory.getServiceLoader().getService(Animator.class).startAnimation(new Animation() {

			@Override
			public boolean tick(long currentTimeMillis) {
				Model.getTime().updateCurrentTime();
				return true;
			}
		});
		MicroUI.start();
		StylePopulator.populate();

		StyledDesktop desktop = new StyledDesktop();
		StyledPanel panel = new StyledPanel();
		panel.setWidget(new MainFrame());
		desktop.show();
		panel.showFullScreen(desktop);
		LoopSound();
	}

	private static void LoopSound() {
		audioPlayer = AudioPlayer.getInstance();
		Playlist p = new Playlist();
		p.addAudioFile(new AudioFile(sunny, AudioChannel.AS_CHANNEL_STEREO,BitLength.BITLENGTH_16, 44100 ,AudioCodec.MP3));

		audioPlayer.initialize(p);
		audioPlayer.launch();
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				audioPlayer.next(LOOP_INTERVAL);
			}
		};
		timer.schedule(task,0, LOOP_INTERVAL*1000);
	}

	private SpresenseDemo() {
	}
}
