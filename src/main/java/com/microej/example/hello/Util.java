/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello;

import ej.mwt.Widget;
import ej.widget.basic.Label;
import ej.widget.composed.Wrapper;

public class Util {

	public static final String STRING_PADDING = " "; //$NON-NLS-1$

	public static Widget addWrapper(Widget widget) {
		Wrapper wrapper = new Wrapper();
		wrapper.setAdjustedToChild(false);
		wrapper.setWidget(widget);
		return wrapper;
	}

	public static String addPadding(String value) {
		return STRING_PADDING + value + STRING_PADDING;
	}

	public static void update(Label label, String value) {
		if (!value.equals(label.getText())) {
			label.updateText(value);
		}
	}
}
