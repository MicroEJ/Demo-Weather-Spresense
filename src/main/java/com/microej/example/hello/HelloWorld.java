/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.hello;

import com.microej.audio.AudioFile;
import com.microej.audio.AudioFile.AudioChannel;
import com.microej.audio.AudioFile.AudioCodec;
import com.microej.audio.AudioFile.BitLength;
import com.microej.audio.AudioFile.SamplingRate;
import com.microej.audio.AudioPlayer;
import com.microej.audio.PlayList;

import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.microui.MicroUI;

/**
 * Prints the message "Hello World !" an displays MicroEJ splash
 */
public class HelloWorld {
	public static void main(String[] args) {
		MicroUI.start();
		new DrawnGui();
		PlayList p = new PlayList();
		p.addAudioFile(new AudioFile("Forest-daytime-ambience.mp3",AudioChannel.AS_CHANNEL_STEREO,BitLength.BITLENGTH_16, SamplingRate.SAMPLINGRATE_44100 ,AudioCodec.MP3));
		p.addAudioFile(new AudioFile("Wind-blowing-sound-effect.mp3",AudioChannel.AS_CHANNEL_STEREO,BitLength.BITLENGTH_16, SamplingRate.SAMPLINGRATE_44100 ,AudioCodec.MP3));
		p.addAudioFile(new AudioFile("Rain-drop-sounds.mp3",AudioChannel.AS_CHANNEL_STEREO,BitLength.BITLENGTH_16, SamplingRate.SAMPLINGRATE_44100 ,AudioCodec.MP3));
		p.addAudioFile(new AudioFile("Light-rain-with-thunder-sound-effect.mp3",AudioChannel.AS_CHANNEL_STEREO,BitLength.BITLENGTH_16, SamplingRate.SAMPLINGRATE_44100 ,AudioCodec.MP3));
		p.addAudioFile(new AudioFile("Rain-thunder-and-pigeon-sounds.mp3",AudioChannel.AS_CHANNEL_STEREO,BitLength.BITLENGTH_16, SamplingRate.SAMPLINGRATE_44100 ,AudioCodec.MP3));
		AudioPlayer.initialize(p);
		AudioPlayer.launch();

		String filename = ("Rain-thunder-and-pigeon-sounds.mp3");
		AudioFile audiofile = new AudioFile(filename, AudioChannel.AS_CHANNEL_STEREO, BitLength.BITLENGTH_16, SamplingRate.SAMPLINGRATE_44100, AudioCodec.MP3);
		System.out.println("Playing... Rain-thunder-and-pigeon-sounds.mp3");
		AudioPlayer.play(audiofile, 6);
		System.out.println("Done playing... Rain-thunder-and-pigeon-sounds.mp3");

		Timer timer = ServiceLoaderFactory.getServiceLoader().getService(Timer.class, Timer.class);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				String filename = ("Rain-drop-sounds.mp3");
				AudioFile audiofile = new AudioFile(filename, AudioChannel.AS_CHANNEL_STEREO, BitLength.BITLENGTH_16, SamplingRate.SAMPLINGRATE_44100, AudioCodec.MP3);
				AudioPlayer.next(40);
			}
		}, 0, 1000*duration);
		AudioPlayer.setVolume(50);
		index = 1;
		while(index >= 0)
		{
			switch(test_async_cmd)
			{
			case 0:
				System.out.println("Java says ... calling pause in 5 seconds");
				pause(5*1000);
				AudioPlayer.pause();
				System.out.println("Java says ... pause called");
				//				test_async_cmd++;
				break;
			case 1:
				System.out.println("Java says ... setting volume to -60DB");
				AudioPlayer.setOutputLevel(-60);
				pause(2*1000);
				System.out.println("Java says ... setting volume to -50DB");
				AudioPlayer.setOutputLevel(-50);
				pause(2*1000);
				System.out.println("Java says ... setting volume to -40DB");
				AudioPlayer.setOutputLevel(-40);
				pause(2*1000);
				System.out.println("Java says ... setting volume to -30DB");
				AudioPlayer.setOutputLevel(-30);
				pause(2*1000);
				System.out.println("Java says ... setting volume to -20DB");
				AudioPlayer.setOutputLevel(-20);
				pause(2*1000);
				System.out.println("Java says ... setting volume to -10DB");
				AudioPlayer.setOutputLevel(-10);
				pause(2*1000);
				//				test_async_cmd++;
				break;
			case 2:
				System.out.println("Java says ... setting volume to 1%");
				AudioPlayer.setVolume(1.5);
				pause(2*1000);
				System.out.println("Java says ... setting volume to 15%");
				AudioPlayer.setVolume(15.0);
				pause(2*1000);
				System.out.println("Java says ... setting volume to 30%");
				AudioPlayer.setVolume(30.1);
				pause(2*1000);
				System.out.println("Java says ... setting volume to 50%");
				AudioPlayer.setVolume(50.0);
				pause(2*1000);
				System.out.println("Java says ... setting volume to 70%");
				AudioPlayer.setVolume(70.0);
				pause(2*1000);
				System.out.println("Java says ... setting volume to 90%");
				AudioPlayer.setVolume(90.0);
				pause(2*1000);
				//				test_async_cmd++;
				break;
			case 3:
				System.out.println("Java says ... Muting for 5 seconds");
				AudioPlayer.mute();
				pause(5*1000);
				System.out.println("Java says ... Unmuting");
				AudioPlayer.unmute();
				pause(5*1000);
				//				test_async_cmd++;
				break;
			default:
				break;
			}
			pause(1*1000);
		}
	}

	public static void pause(long delay) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public HelloWorld() {
	}

	private final static int duration = 20;
	private static int index = 0;
	private static int test_async_cmd = 2;
}
