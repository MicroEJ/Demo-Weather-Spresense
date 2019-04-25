/*
 * Java
 *
 * Copyright 2019 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microej.audio;

import java.util.ArrayList;

/**
 *
 */
public class PlayList {
	public PlayList() {
		audiofileList = new ArrayList<AudioFile>();
	}

	public void addAudioFile(AudioFile arg) {
		audiofileList.add(arg);
	}

	public void removeAudioFile(AudioFile arg) {
		audiofileList.remove(arg);
	}

	public void selectNextFile() {
		indexCurrFile++;
		if(indexCurrFile >= audiofileList.size()) {
			indexCurrFile = 0;
		}
	}

	public void selectPreviousFile() {
		indexCurrFile--;
		if(indexCurrFile < 0) {
			indexCurrFile = audiofileList.size();
		}
	}

	public AudioFile getCurrFile() {
		return audiofileList.get(indexCurrFile);
	}

	private final ArrayList<AudioFile> audiofileList;
	private int indexCurrFile = 0;
}
