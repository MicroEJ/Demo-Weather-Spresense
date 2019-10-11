/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.audio;

import com.microej.spresense.demo.Model;

import ej.audio.AudioFile;
import ej.audio.AudioPlayer;

/**
 *
 */
public class AudioManager extends Thread {
	private static final int CHANGE_RATE = 100;
	private static final int STILL_RATE = 1000;
	private static final String AUDIO = "AUDIO/";
	private static final String MP3 = ".mp3";
	private static final String[] FILES = { "sunny", "rain", "wind" };
	private static final int AUDIO_STEPS = 10;
	private static final int MAX_AUDIO_VOLUME = 100;
	private static final int MIN_AUDIO_VOLUME = 5;

	private final AudioPlayer audioPlayer;

	private AudioPlayback audioPlayback;
	private int currentWeather;
	private int nextWeather;
	private int volume;
	private boolean run;

	public AudioManager() {
		audioPlayer = AudioPlayer.getInstance();
		volume = MIN_AUDIO_VOLUME;
	}

	private synchronized void updateSoundFile() {
		currentWeather = nextWeather;
		audioPlayback.setFile(new AudioFile(AUDIO + FILES[currentWeather - 1] + MP3, AudioFile.AS_CHANNEL_STEREO,
				AudioFile.BITLENGTH_16,
				44100, AudioFile.AUDIO_CODEC_MP3));
	}

	@Override
	public synchronized void start() {
		audioPlayback = new AudioPlayback(audioPlayer);
		volume = MIN_AUDIO_VOLUME;
		audioPlayer.setVolume(volume);
		run = true;
		super.start();
	}

	public synchronized void stop() {
		run = false;
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
				rate = STILL_RATE;
			}
			try {
				Thread.sleep(rate);
			} catch (InterruptedException e) {
				stop();
			}
		}
		audioPlayback.stop();
	}

	/**
	 * @param minAudioVolume
	 */
	private void setVolume(int minAudioVolume) {
		if (volume != minAudioVolume) {
			volume = minAudioVolume;
			audioPlayer.setVolume(volume);
		}
	}
}
