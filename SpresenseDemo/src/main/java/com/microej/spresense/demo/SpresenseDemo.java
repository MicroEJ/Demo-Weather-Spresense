/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo;

import java.util.logging.Logger;

import com.microej.spresense.demo.audio.AudioManager;
import com.microej.spresense.demo.model.Model;
import com.microej.spresense.demo.style.StylePopulator;
import com.microej.spresense.demo.widget.MainFrame;

import ej.animation.Animation;
import ej.animation.Animator;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.microui.MicroUI;
import ej.widget.StyledDesktop;
import ej.widget.StyledPanel;

/**
 * Entry point of the demo.
 */
public class SpresenseDemo {

	/**
	 * Logger used for the demo.
	 */
	public static final Logger LOGGER = Logger.getLogger("SpresenseDemo"); //$NON-NLS-1$
	private static final long INIT_TIME = 1557844987038L;

	private SpresenseDemo() {
		// Forbid instantiation.
	}

	/**
	 * Entry point.
	 *
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {
		// Set the base time to avoid starting in 1970.
		if (System.currentTimeMillis() < INIT_TIME) {
			ej.bon.Util.setCurrentTimeMillis(INIT_TIME);
		}

		// In this demo, the time flows faster to make the weather changes more engaging
		simulateFastForwardTime();

		// Starts the audio
		AudioManager.INSTANCE.start();

		// Starts the UI
		MicroUI.start();
		StylePopulator.populate();
		StyledDesktop desktop = new StyledDesktop();
		StyledPanel panel = new StyledPanel();
		panel.setWidget(new MainFrame());
		desktop.show();
		panel.showFullScreen(desktop);
	}

	private static void simulateFastForwardTime() {
		Animator animator = ServiceLoaderFactory.getServiceLoader().getService(Animator.class);
		animator.startAnimation(new Animation() {

			@Override
			public boolean tick(long currentTimeMillis) {
				Model model = ServiceLoaderFactory.getServiceLoader().getService(Model.class);
				model.getTime().updateCurrentTime();
				return true;
			}
		});
	}

}
