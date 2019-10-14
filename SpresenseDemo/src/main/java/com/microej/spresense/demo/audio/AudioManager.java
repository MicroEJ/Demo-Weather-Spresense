/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.audio;

import java.util.logging.Level;

import com.microej.spresense.demo.Model;
import com.microej.spresense.demo.SpresenseDemo;

import ej.audio.AudioFile;
import ej.audio.AudioPlayer;

/**
 * A manager playing the audio of the current weather.
 */
public class AudioManager extends Thread {
	private static final int MP3_FRAMERATE = 44100;
	private static final int CHANGE_RATE = 100;
	private static final int STILL_RATE = 1000;
	private static final String FOLDER = "AUDIO/"; //$NON-NLS-1$
	private static final String EXTENTION = ".mp3"; //$NON-NLS-1$
	private static final String[] FILES = { "sunny", "rain", "wind" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	private static final int AUDIO_STEPS = 5;
	private static final int MAX_AUDIO_VOLUME = 100;
	private static final int MIN_AUDIO_VOLUME = 5;

	/**
	 * An instance of the audio manager.
	 */
	public static final AudioManager INSTANCE = new AudioManager();

	private final AudioPlayer audioPlayer;

	private AudioPlayback audioPlayback;
	private int currentWeather;
	private int nextWeather;
	private int volume;
	private boolean run;

	private AudioManager() {
		audioPlayer = AudioPlayer.getInstance();
		volume = MIN_AUDIO_VOLUME;
	}


	@Override
	public synchronized void start() {
		audioPlayback = new AudioPlayback(audioPlayer);
		volume = MIN_AUDIO_VOLUME;
		audioPlayer.setVolume(volume);
		run = true;
		super.start();
	}

	@Override
	public void run() {
		nextWeather = Model.getWeather();
		updateSoundFile();
		audioPlayback.start();
		while (run) {
			int rate = CHANGE_RATE;
			nextWeather = Model.getWeather();
			if (nextWeather != currentWeather) {
				if (volume <= MIN_AUDIO_VOLUME) {
					setVolume(MIN_AUDIO_VOLUME);
					updateSoundFile();
				} else {
					setVolume(Math.max(volume - AUDIO_STEPS, MIN_AUDIO_VOLUME));
				}
			} else if (volume < MAX_AUDIO_VOLUME) {
				setVolume(Math.min(volume + AUDIO_STEPS, MAX_AUDIO_VOLUME));
			} else {
				setVolume(MAX_AUDIO_VOLUME);
				rate = STILL_RATE;
			}
			try {
				Thread.sleep(rate);
			} catch (InterruptedException e) {
				SpresenseDemo.LOGGER.log(Level.INFO, e.getMessage(), e);
				run = false;
			}
		}
		audioPlayback.stop();
	}

	private void setVolume(int newVolume) {
		if (volume != newVolume) {
			volume = newVolume;
			audioPlayer.setVolume(volume);
		}
	}

	private synchronized void updateSoundFile() {
		currentWeather = nextWeather;
		audioPlayback.setFile(new AudioFile(FOLDER + FILES[currentWeather - 1] + EXTENTION, AudioFile.AS_CHANNEL_STEREO,
				AudioFile.BITLENGTH_16, MP3_FRAMERATE, AudioFile.AUDIO_CODEC_MP3));
	}
}
