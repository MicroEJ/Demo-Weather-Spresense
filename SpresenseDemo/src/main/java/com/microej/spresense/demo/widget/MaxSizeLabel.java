/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.widget;

import ej.microui.display.Font;
import ej.style.Style;
import ej.style.container.Rectangle;
import ej.style.text.TextManager;
import ej.style.util.StyleHelper;
import ej.widget.basic.Label;

/**
 * A label taking the size of the string with the longest width.
 */
public class MaxSizeLabel extends Label {

	private String[] words;

	/**
	 * Instantiates a {@link MaxSizeLabel}.
	 */
	public MaxSizeLabel() {
		super();
	}

	/**
	 * Instantiates a {@link MaxSizeLabel}.
	 *
	 * @param text
	 *            the text to display.
	 */
	public MaxSizeLabel(String text) {
		super(text);
	}

	/**
	 * Sets the words.
	 *
	 * @param words
	 *            the words to set.
	 */
	public void setWords(String[] words) {
		this.words = (words == null) ? null : words.clone();
	}

	/**
	 * Gets the words.
	 *
	 * @return the words.
	 */
	public String[] getWords() {
		return (words == null) ? null : words.clone();
	}

	@Override
	public Rectangle validateContent(Style style, Rectangle availableSize) {
		Font font = StyleHelper.getFont(style);
		TextManager textManager = style.getTextManager();
		Rectangle contentSize = textManager.computeContentSize(getText(), font, availableSize);
		if (words != null) {
			for (String string : words) {
				Rectangle computeContentSize = textManager.computeContentSize(string, font, availableSize);
				contentSize.setWidth(Math.max(contentSize.getWidth(), computeContentSize.getWidth()));
				contentSize.setHeight(Math.max(contentSize.getHeight(), computeContentSize.getHeight()));
			}
		}
		return contentSize;
	}
}
