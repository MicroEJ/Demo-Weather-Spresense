/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello;

import com.microej.example.hello.style.StylePopulator;
import com.microej.example.hello.widget.MainFrame;

import ej.microui.MicroUI;
import ej.widget.StyledDesktop;
import ej.widget.StyledPanel;

/**
 * Prints the message "Hello World !" an displays MicroEJ splash
 */
public class HelloWorld {
	public static void main(String[] args) {
		Model.getTime().updateCurrentTime();
		MicroUI.start();
		StylePopulator.populate();

		StyledDesktop desktop = new StyledDesktop();
		StyledPanel panel = new StyledPanel();
		panel.setWidget(new MainFrame());
		desktop.show();
		panel.showFullScreen(desktop);
	}

	private HelloWorld() {
	}
}
