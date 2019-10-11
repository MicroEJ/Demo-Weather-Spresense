/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.audio;

import java.io.IOException;

import ej.audio.AudioFile;
import ej.audio.AudioPlayer;

/**
 *
 */
public class AudioPlayback extends Thread {

	private static final int LOOP_INTERVAL = 30;
	private final AudioPlayer audioPlayer;
	private AudioFile file;

	public AudioPlayback(AudioPlayer audioPlayer) {
		this.audioPlayer = audioPlayer;
	}

	public synchronized void setFile(AudioFile file) {
		if (!file.equals(this.file)) {
			this.file = file;
			try {
				audioPlayer.pause();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		while (file != null) {
			try {
				audioPlayer.play(file, LOOP_INTERVAL);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			audioPlayer.pause();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	public synchronized void stop() {
		file = null;
	}
}
