/*
 * Java
 *
 * Copyright 2019 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microej.audio;

/**
 *
 */
public class AudioPlayer {

	public enum AudioOutputState {
		SOUND_OFF,
		SOUND_ON
	}

	public AudioPlayer() {
	}

	public static void initialize(PlayList playlistArgs) {
		playList = playlistArgs;
	}

	public static void launch() {
		AudioPlayerNative.launch();
	}

	public static void play(AudioFile file, int duration) {
		file.play(duration);
	}

	public static void play(int duration) {
		playList.getCurrFile().play(duration);
	}

	public static void next(int duration) {
		playList.selectNextFile();
		playList.getCurrFile().play(duration);
	}

	public static void previous(int duration) {
		playList.selectPreviousFile();
		playList.getCurrFile().play(duration);
	}

	public static void pause() {
		AudioPlayerNative.pause();
		AudioPlayerNative.fetchResult();
	}

	public static void setVolume(double volume) {
		// TODO : convert volume in percentage in DB
		audioVolume = volume;
		audioOutput = convertVolumeToOutput(volume);
		outputState = AudioOutputState.SOUND_ON;
		AudioPlayerNative.setOutputLevel(audioOutput);
	}

	public static void setOutputLevel(double volumeDB) {
		// TODO : convert DB to percentage
		audioVolume = convertOutputToVolume(volumeDB);
		audioOutput = volumeDB;
		outputState = AudioOutputState.SOUND_ON;
		AudioPlayerNative.setOutputLevel(audioOutput);
	}


	public static double getVolume() {
		return audioVolume;
	}

	public static double getOutput() {
		return audioOutput;
	}

	public static void mute() {
		outputState = AudioOutputState.SOUND_OFF;
		AudioPlayerNative.mute();
	}

	public static void unmute() {
		outputState = AudioOutputState.SOUND_ON;
		AudioPlayerNative.setOutputLevel(audioOutput);
	}

	public static AudioOutputState getAudioOutputState() {
		return outputState;
	}

	private static double convertOutputToVolume(double db) {
		if(db < LOW_DB_GAIN_1_PER_CENT) {
			// Error
		}
		double result = DbToGain(db) / (LOW_DB_GAIN_1_PER_CENT);
		System.out.println("AudioPlayer.convertOutputToVolume() db=" + db);
		System.out.println("AudioPlayer.convertOutputToVolume() result=" + result);
		return result;
	}

	private static double convertVolumeToOutput(double volume) {
		double result = GainToDb(volume*(LOW_DB_GAIN_1_PER_CENT)/100);
		System.out.println("AudioPlayer.convertVolumeToOutput() volume=" + volume);
		System.out.println("AudioPlayer.convertVolumeToOutput() result=" + result);
		return result;
	}

	private static double DbToGain(double db) {
		double result = 100*Math.pow(10, db/20);
		System.out.println("AudioPlayer.DbToGain() db=" + db);
		System.out.println("AudioPlayer.DbToGain() result=" + result);
		return result;
	}
	private static double GainToDb(double gain) {
		if(gain <0) {
			System.out.println("########### ERROR #########");
		}
		double result = 20*Math.log10(gain);
		System.out.println("AudioPlayer.GainToDb() gain=" + gain);
		System.out.println("AudioPlayer.GainToDb() result=" + result);
		return result;
	}

	// Volume in %
	private static double audioVolume;
	// Output in DB
	private static double audioOutput;
	private static AudioOutputState outputState = AudioOutputState.SOUND_ON;
	private final static double LOW_DB_GAIN_1_PER_CENT = AudioPlayer.DbToGain(-60);
	private static PlayList playList;

}
