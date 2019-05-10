/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello.widget;

import ej.microui.display.Font;
import ej.style.Style;
import ej.style.container.Rectangle;
import ej.style.text.TextManager;
import ej.style.util.StyleHelper;
import ej.widget.basic.Label;

/**
 *
 */
public class MaxSizeLabel extends Label {

	private String words[];
	public MaxSizeLabel() {
		super();
	}

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
		this.words = words;
	}

	/**
	 * Gets the words.
	 *
	 * @return the words.
	 */
	public String[] getWords() {
		return words;
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
