/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.audio;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

import com.microej.spresense.demo.SpresenseDemo;
import com.microej.spresense.demo.model.Model;

import ej.audio.AudioFile;
import ej.audio.AudioPlayer;
import ej.components.dependencyinjection.ServiceLoaderFactory;

/**
 * A manager playing the audio of the current weather.
 */
public class AudioManager extends Thread implements Observer {
	private static final int MP3_FRAMERATE = 44100;
	private static final int CHANGE_RATE = 100;
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

	private final Object weatherMutex = new Object();
	private AudioPlayback audioPlayback;
	private int currentWeather;
	private int nextWeather;
	private int volume;
	private boolean run;
	private Model model;

	private AudioManager() {
		this.audioPlayer = AudioPlayer.getInstance();
		this.volume = MIN_AUDIO_VOLUME;
	}

	@Override
	public synchronized void start() {
		this.model = ServiceLoaderFactory.getServiceLoader().getService(Model.class);
		this.model.addObserver(this);
		this.audioPlayback = new AudioPlayback(this.audioPlayer);
		this.volume = MIN_AUDIO_VOLUME;
		this.audioPlayer.setVolume(this.volume);
		this.run = true;
		super.start();
	}

	@Override
	public void run() {
		this.nextWeather = this.model.getWeather();
		// Set initial file.
		updateSoundFile();
		this.audioPlayback.start();
		while (this.run) {
			this.nextWeather = this.model.getWeather();
			if (this.nextWeather != this.currentWeather) {
				if (this.volume <= MIN_AUDIO_VOLUME) {
					setVolume(MIN_AUDIO_VOLUME);
					// Switch to the current weather sound.
					updateSoundFile();
				} else {
					// Decrease the volume before updating file.
					setVolume(Math.max(this.volume - AUDIO_STEPS, MIN_AUDIO_VOLUME));
				}
				sleep();
			} else if (this.volume < MAX_AUDIO_VOLUME) {
				// Increase the volume.
				setVolume(Math.min(this.volume + AUDIO_STEPS, MAX_AUDIO_VOLUME));
				sleep();
			} else {
				// Currently playing the right sound at the maximum volume. Wait for a change.
				setVolume(MAX_AUDIO_VOLUME);
				waitForNextWeather();
			}
		}
		this.audioPlayback.stop();
	}

	private void waitForNextWeather() {
		synchronized (this.weatherMutex) {
			while (this.nextWeather == this.model.getWeather()) {
				try {
					this.weatherMutex.wait();
				} catch (InterruptedException e) {
					this.run = false;
					SpresenseDemo.LOGGER.log(Level.INFO, e.getMessage(), e);
				}
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		synchronized (this.weatherMutex) {
			Model model = ServiceLoaderFactory.getServiceLoader().getService(Model.class);
			if (this.nextWeather != model.getWeather()) {
				this.weatherMutex.notifyAll();
			}
		}
	}

	private void sleep() {
		try {
			Thread.sleep(CHANGE_RATE);
		} catch (InterruptedException e) {
			SpresenseDemo.LOGGER.log(Level.INFO, e.getMessage(), e);
			this.run = false;
		}
	}

	private void setVolume(int newVolume) {
		if (this.volume != newVolume) {
			this.volume = newVolume;
			this.audioPlayer.setVolume(this.volume);
		}
	}

	private synchronized void updateSoundFile() {
		this.currentWeather = this.nextWeather;
		this.audioPlayback.setFile(new AudioFile(FOLDER + FILES[this.currentWeather - 1] + EXTENTION,
				AudioFile.AS_CHANNEL_STEREO, AudioFile.BITLENGTH_16, MP3_FRAMERATE, AudioFile.AUDIO_CODEC_MP3));
	}
}
