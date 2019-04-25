/*
 * Java
 *
 * Copyright 2019 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microej.audio;

/**
 * Class audio player
 */
public class AudioPlayer {
	/**
	 * A simple enum representing the audio output state (on / off)
	 */
	public enum AudioOutputState {
		SOUND_OFF,
		SOUND_ON
	}

	public AudioPlayer() {
	}
	/**
	 * Optionnal
	 * Initialize the play list
	 * @param playlistArgs
	 * 		playlist to give the audioplayer
	 */
	public static void initialize(PlayList playlistArgs) {
		playList = playlistArgs;
	}
	/**
	 * Required
	 * Launch the audio player low level
	 */
	public static void launch() {
		AudioPlayerNative.launch();
	}
	/**
	 * Play the audiofile
	 * @param file
	 * 		file to play
	 * @param duration
	 * 		in seconds, how long will be the file be played
	 */
	public static void play(AudioFile file, int duration) {
		file.play(duration);
	}

	/**
	 * play the currently selected file in the playlist
	 * @param duration
	 * 		in seconds, how long will the file be played
	 */
	public static void play(int duration) {
		playList.getCurrFile().play(duration);
	}

	/**
	 * Play the next file in the playlist
	 * @param duration
	 * 		in seconds, how long will the file be played
	 */
	public static void next(int duration) {
		playList.selectNextFile();
		playList.getCurrFile().play(duration);
	}

	/**
	 * Play the previous file in the playlist
	 * @param duration
	 * 		in seconds, how long will the file be played
	 */
	public static void previous(int duration) {
		playList.selectPreviousFile();
		playList.getCurrFile().play(duration);
	}

	/**
	 * Pause the file currently playing
	 */
	public static void pause() {
		AudioPlayerNative.pause();
		AudioPlayerNative.fetchResult();
	}
	/**
	 * Set the gain in percentage
	 * @param volume
	 * 		volume in percentage
	 */
	public static void setVolume(double volume) {
		audioVolume = volume;
		audioOutput = convertVolumeToOutput(volume);
		outputState = AudioOutputState.SOUND_ON;
		AudioPlayerNative.setOutputLevel(audioOutput);
	}

	/**
	 * Set the gain in decibel
	 * @param volumeDB
	 * 		gain in decibel
	 */
	public static void setOutputLevel(double volumeDB) {
		audioVolume = convertOutputToVolume(volumeDB);
		audioOutput = volumeDB;
		outputState = AudioOutputState.SOUND_ON;
		AudioPlayerNative.setOutputLevel(audioOutput);
	}

	/**
	 *
	 * @return
	 * 		current volume in percent
	 */
	public static double getVolume() {
		return audioVolume;
	}
	/**
	 * @return
	 * 		Current gain in decibel
	 */
	public static double getOutput() {
		return audioOutput;
	}
	/**
	 * Mute audio output
	 */
	public static void mute() {
		outputState = AudioOutputState.SOUND_OFF;
		AudioPlayerNative.mute();
	}
	/**
	 * unmute audio output
	 */
	public static void unmute() {
		outputState = AudioOutputState.SOUND_ON;
		AudioPlayerNative.setOutputLevel(audioOutput);
	}
	/**
	 *  Getter for the current state of the audio output
	 * @return
	 * 		AudioOutputState
	 */
	public static AudioOutputState getAudioOutputState() {
		return outputState;
	}


	/**
	 *
	 * @param db
	 * 		decibel value to be converted
	 * @return
	 * 		Volume in percentage
	 */
	private static double convertOutputToVolume(double db) {
		if(db < LOW_DB_GAIN_1_PER_CENT) {
			// Error
		}
		double result = DbToGain(db) / (LOW_DB_GAIN_1_PER_CENT);
		System.out.println("AudioPlayer.convertOutputToVolume() db=" + db);
		System.out.println("AudioPlayer.convertOutputToVolume() result=" + result);
		return result;
	}

	/**
	 * @param volume
	 * 		volume in percentage
	 * @return
	 * 		gain value in decibel
	 */
	private static double convertVolumeToOutput(double volume) {
		double result = GainToDb(volume*(LOW_DB_GAIN_1_PER_CENT)/100);
		System.out.println("AudioPlayer.convertVolumeToOutput() volume=" + volume);
		System.out.println("AudioPlayer.convertVolumeToOutput() result=" + result);
		return result;
	}

	/**
	 * A simply formula to convert logarithmic scale to linear scale
	 * @param db
	 * 		value to convert
	 * @return
	 * 		100*10^(decibel/20)
	 */
	private static double DbToGain(double db) {
		double result = 100*Math.pow(10, db/20);
		System.out.println("AudioPlayer.DbToGain() db=" + db);
		System.out.println("AudioPlayer.DbToGain() result=" + result);
		return result;
	}
	/**
	 * A simple formula to convert a linear scale to logarithmic scale
	 * @param gain
	 * 		value to convert
	 * @return
	 * 		20*log10(gain)
	 */
	private static double GainToDb(double gain) {
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
