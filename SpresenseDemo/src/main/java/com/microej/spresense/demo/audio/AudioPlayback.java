/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.audio;

import java.io.IOException;
import java.util.logging.Level;

import com.microej.spresense.demo.SpresenseDemo;

import ej.audio.AudioFile;
import ej.audio.AudioPlayer;

/**
 * A playback of the audio stream.
 */
public class AudioPlayback extends Thread {

	private static final String COULD_NOT_PAUSE_PLAYER = "Could not pause player."; //$NON-NLS-1$
	private static final int LOOP_INTERVAL = 30;
	private final AudioPlayer audioPlayer;
	private AudioFile file;

	/**
	 * Instantiates an {@link AudioPlayback}.
	 *
	 * @param audioPlayer
	 *            the {@link AudioPlayer} to use.
	 */
	public AudioPlayback(AudioPlayer audioPlayer) {
		this.audioPlayer = audioPlayer;
	}

	/**
	 * Sets the current file.
	 *
	 * @param file
	 *            the file to play. If a file is already playing, it will be stopped.
	 */
	public synchronized void setFile(AudioFile file) {
		if (!file.equals(this.file)) {
			this.file = file;
			try {
				this.audioPlayer.pause();
			} catch (IOException e) {
				SpresenseDemo.LOGGER.log(Level.INFO, COULD_NOT_PAUSE_PLAYER, e);
			}
		}
	}

	@Override
	public void run() {
		AudioFile file = this.file;
		while (file != null) {
			try {
				this.audioPlayer.play(file, LOOP_INTERVAL);
			} catch (IOException e) {
				SpresenseDemo.LOGGER.log(Level.WARNING, "Could not play " + file.getName(), e); //$NON-NLS-1$
			}
			file = this.file;
		}
		try {
			this.audioPlayer.pause();
		} catch (IOException e) {
			SpresenseDemo.LOGGER.log(Level.INFO, COULD_NOT_PAUSE_PLAYER, e);
		}
	}

	/**
	 * Stops the playback.
	 */
	public synchronized void stop() {
		this.file = null;
	}
}
