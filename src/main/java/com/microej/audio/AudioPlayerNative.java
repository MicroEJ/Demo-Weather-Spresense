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
public class AudioPlayerNative {

	public static native void launch();

	public static native void play(byte[] filename, int filename_length, int audio_channel, int bit_length, int sampling_rate, int codec, int duration);

	public static native void pause();

	public static native void fetchResult();

	public static native void setOutputLevel(double volumeInDb);

	public static native void mute();
}
