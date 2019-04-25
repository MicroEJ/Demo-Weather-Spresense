/*
 * Java
 *
 * Copyright 2019 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microej.audio;

/**
 * Class of native function for Audio Player
 */
public class AudioPlayerNative {
	/**
	 * A low level initialization function
	 */
	public static native void launch();

	/**
	 * Play a file
	 * @param filename
	 * 		Name of the file
	 * @param filename_length
	 * 		Length of the name of the file.
	 * @param audio_channel
	 * 		Audio channel selected
	 * @param bit_length
	 * 		Bit length in bits (16/24/32)
	 * @param sampling_rate
	 * 		Sampling rate in Hz
	 * @param codec
	 *		Format of the file to decode. Must correspond to a macros / enum defined in C-code
	 * @param duration
	 * 		Duration in seconds to play the file
	 */
	public static native void play(byte[] filename, int filename_length, int audio_channel, int bit_length, int sampling_rate, int codec, int duration);

	/**
	 * Pause the execution of the file
	 */
	public static native void pause();

	/**
	 * Playing a file is asynchrone and will suspend the Java thread calling until execution is over
	 * This function fetch the result of the last executed command and throw an excpetion with an error message if an error occured
	 */
	public static native void fetchResult();

	/**
	 * Set the gain in decibel
	 * @param volumeInDb
	 * 		Gain in decibel to be set
	 */
	public static native void setOutputLevel(double volumeInDb);

	/**
	 * Mute the output
	 */
	public static native void mute();
}
