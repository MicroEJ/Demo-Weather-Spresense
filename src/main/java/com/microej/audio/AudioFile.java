/*
 * Java
 *
 * Copyright 2019 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microej.audio;

import ej.sni.SNI;

/**
 * Class representing an audio file
 */
public class AudioFile {
	/**
	 * Audio Codec
	 * Format of the file
	 */
	public enum AudioCodec {
		MP3(0),
		/** WAV */
		WAV(1),
		/** AAC */
		AAC(2),
		/** OPUS */
		OPUS(3),
		/** MEDIA Packet */
		MEDIA(4),
		/** Linear PCM */
		LPCM(5);

		AudioCodec(int v) {
			this.m_value = v;
		}
		int value() {
			return m_value;
		}
		private final int m_value;
	}
	/**
	 * Enum representing the bit length
	 * value function allow access to the integer value of the enum
	 */
	public enum BitLength {
		BITLENGTH_16(16),
		BITLENGTH_24(24),
		BITLENGTH_32(32);

		BitLength(int v) {
			this.m_value = v;
		}
		int value() {
			return m_value;
		}
		private final int m_value;
	}
	/**
	 * Sampling rate in Hz represented by an enum
	 * value function allow access to the integer value of the enum
	 */
	public enum SamplingRate  {
		/* Auto */
		SAMPLINGRATE_AUTO(0),
		/** 8kHz */
		SAMPLINGRATE_8000(8000),
		/** 11.025kHz */
		SAMPLINGRATE_11025(11025),
		/** 12kHz */
		SAMPLINGRATE_12000(12000),
		/** 16kHz */
		SAMPLINGRATE_16000(16000),
		/** 22.05kHz */
		SAMPLINGRATE_22050(22050),
		/** 24kHz */
		SAMPLINGRATE_24000(24000),
		/** 32kHz */
		SAMPLINGRATE_32000(32000),
		/** 44.1kHz */
		SAMPLINGRATE_44100(44100),
		/** 48kHz */
		SAMPLINGRATE_48000(48000),
		/** 64kHz */
		SAMPLINGRATE_64000(64000),
		/** 88.2kHz */
		SAMPLINGRATE_88200(88200),
		/** 96kHz */
		SAMPLINGRATE_96000(96000),
		/** 128kHz */
		SAMPLINGRATE_128000(128000),
		/** 176.4kHz */
		SAMPLINGRATE_176400(176400),
		/** 192kHz */
		SAMPLINGRATE_192000(192000);

		SamplingRate(int v) {
			this.m_value = v;
		}
		int value() {
			return m_value;
		}
		private final int m_value;
	}
	/**
	 * Enum defining the audio channel
	 */
	public enum AudioChannel {
		AS_CHANNEL_MONO(1),
		/** STEREO (2ch) */
		AS_CHANNEL_STEREO(2),
		/** 4ch */
		AS_CHANNEL_4CH(4),
		/** 6ch */
		AS_CHANNEL_6CH(6),
		/** 8ch */
		AS_CHANNEL_8CH(8);

		AudioChannel(int v) {
			this.m_value = v;
		}
		int value() {
			return m_value;
		}
		private final int m_value;
	}

	/**
	 * Construct an AudioFile
	 * @param name_args
	 * 		Filename
	 * @param channel_args
	 * 		AudioChannel selected
	 * @param bit_length_args
	 * 		ButLength of the audio file
	 * @param sampling_rate_length
	 * 		Sampling rate in Hz
	 * @param codec_args
	 * 		Format of encoded audio
	 */
	public AudioFile(String name_args, AudioChannel channel_args, BitLength bit_length_args, SamplingRate sampling_rate_length, AudioCodec codec_args) {
		name = name_args;
		channel = channel_args;
		bit_length = bit_length_args;
		sampling_rate = sampling_rate_length;
		codec = codec_args;
	}

	/**
	 * Play the file for the given duration
	 * @param duration
	 * 		in seconds
	 */
	public void play(int duration) {
		int filename_length = this.name.length();
		byte[] filename = new byte[filename_length+1];
		System.out.println("AudioFile::play -> "+this.name);
		SNI.toCString(this.name, filename);
		System.out.println("Calling play native");
		AudioPlayerNative.play(filename, filename_length, this.channel.value(), this.bit_length.value(), this.sampling_rate.value(), this.codec.value(), duration);
		AudioPlayerNative.fetchResult();
	}

	public String name;
	public AudioChannel channel;
	public AudioCodec codec;
	public SamplingRate sampling_rate;
	public BitLength bit_length;
}
