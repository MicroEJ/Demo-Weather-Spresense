/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo;

import java.util.Random;

import ej.mwt.Widget;
import ej.widget.basic.Label;
import ej.widget.composed.Wrapper;

/**
 * Util functions.
 */
public final class Util {

	/**
	 * A shared random.
	 */
	public static final Random RANDOM = new Random();

	private static final String STRING_PADDING = " "; //$NON-NLS-1$

	private Util() {
		// Forbid instantiation.
	}

	/**
	 * Add a wrapper to a widget.
	 *
	 * @param widget
	 *            the widget to wrap.
	 * @return the wrapper containing the widget.
	 */
	public static Wrapper addWrapper(Widget widget) {
		Wrapper wrapper = new Wrapper();
		wrapper.setAdjustedToChild(false);
		wrapper.setWidget(widget);
		return wrapper;
	}

	/**
	 * Add a padding to a value.
	 *
	 * @param value
	 *            the value to add the padding to.
	 * @return the value padded.
	 */
	public static String addPadding(String value) {
		return STRING_PADDING + value + STRING_PADDING;
	}

	/**
	 * Update a label only if its text have changed.
	 *
	 * @param label
	 *            the label to update.
	 * @param value
	 *            the value to set the label to.
	 */
	public static void update(Label label, String value) {
		if (!value.equals(label.getText())) {
			label.setText(value);
		}
	}
}
