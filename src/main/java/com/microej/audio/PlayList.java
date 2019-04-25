/*
 * Java
 *
 * Copyright 2019 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microej.audio;

import java.util.ArrayList;

/**
 * A playlist object using AudioFile
 */
public class PlayList {
	/**
	 * Simple construcotr
	 */
	public PlayList() {
		audiofileList = new ArrayList<AudioFile>();
	}

	/**
	 * Add an audiofile
	 * @param arg
	 * 		AudioFile to add
	 */
	public void addAudioFile(AudioFile arg) {
		audiofileList.add(arg);
	}

	/**
	 * Remove an audio file
	 * @param arg
	 * 		AudioFile to remove
	 */
	public void removeAudioFile(AudioFile arg) {
		audiofileList.remove(arg);
	}

	/**
	 * Select next file in list
	 */
	public void selectNextFile() {
		indexCurrFile++;
		if(indexCurrFile >= audiofileList.size()) {
			indexCurrFile = 0;
		}
	}
	/**
	 * Select previous file in list
	 */
	public void selectPreviousFile() {
		indexCurrFile--;
		if(indexCurrFile < 0) {
			indexCurrFile = audiofileList.size();
		}
	}

	/**
	 * @return
	 * 		the currently selected audio file
	 */
	public AudioFile getCurrFile() {
		return audiofileList.get(indexCurrFile);
	}

	private final ArrayList<AudioFile> audiofileList;
	private int indexCurrFile = 0;
}
