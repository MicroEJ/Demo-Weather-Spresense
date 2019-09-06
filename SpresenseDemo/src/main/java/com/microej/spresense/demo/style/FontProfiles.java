/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.style;

import ej.microui.display.Font;
import ej.style.font.FontProfile;

public final class FontProfiles {

	private static final String ROBOTO = "Roboto"; //$NON-NLS-1$
	private static final String PIXEL = "pixel"; //$NON-NLS-1$
	private static final String SOURCE = "Source"; //$NON-NLS-1$


	public static final FontProfile SMALL = new FontProfile(PIXEL, 15, Font.STYLE_PLAIN);
	public static final FontProfile MEDIUM_BOLD = new FontProfile(ROBOTO, 22, Font.STYLE_BOLD);
	public static final FontProfile MEDIUM = new FontProfile(ROBOTO, 20, Font.STYLE_PLAIN);
	public static final FontProfile HUGE = new FontProfile(SOURCE, 60, Font.STYLE_PLAIN);

	private FontProfiles() {
	}

}
