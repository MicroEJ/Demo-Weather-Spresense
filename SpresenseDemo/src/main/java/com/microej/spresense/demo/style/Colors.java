/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.style;

/**
 * Colors used for the demo.
 */
public final class Colors {

	/**
	 * Number of colors per day.
	 */
	public static final int COLOR_COUNT_PER_DAY = 4;

	/**
	 * Colors used for the week days. 4 colours a day from the beginning of the day to half-day then the same colours
	 * are used back. The colours are ordered from sunday to saturday.
	 */
	public static final int[] WEEK = {
			// SUNDAY
			0xFF5200, 0xFF7500, 0xF59F00, 0xFCC400,

			// MONDAY
			0x007800, 0x5C9400, 0x74B800, 0x94D800,

			// TUESDAY
			0x0B7285, 0x1098AD, 0x22B8CF, 0x3BC9DB,

			// WEDNESDAY
			0x364FC7, 0x4263EB, 0x1B7EE6, 0x339AF0,

			// THURSDAY
			0x862E9C, 0xAE3EC9, 0xCB5DE8, 0xE599F7,

			// FRIDAY
			0xA61E4D, 0xD6336C, 0xF06595, 0xFAA2C1,

			// SATURDAY
			0xC92A00, 0xF03E00, 0xFF6B6B, 0xFFA8A8
	};

	private Colors() {
	}

}
