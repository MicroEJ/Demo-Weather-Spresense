/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello;

import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.style.State;
import ej.style.Stylesheet;
import ej.style.border.ComplexRectangularBorder;
import ej.style.font.FontProfile;
import ej.style.outline.ComplexOutline;
import ej.style.outline.SimpleOutline;
import ej.style.selector.ClassSelector;
import ej.style.selector.StateSelector;
import ej.style.selector.combinator.AndCombinator;
import ej.style.util.EditableStyle;
import ej.style.util.StyleHelper;

/**
 *
 */
public class StylePopulator {

	public static final int COLOR_WHITE = 0xFFFFFF;
	public static final int COLOR_BLUE = 0x1e90ff;
	public static final int COLOR_SUN = 0xffbf00;
	public static final int COLOR_BLACK = 0X000000;

	public static final String CS_TRIANGLE = "a";
	public static final String CS_DAY = "b";
	public static final String CS_WEEK = "c";
	public static final String CS_SUN = "d";
	public static final String CS_DATE = "e";
	public static final String CS_MEDIUM_TEXT = "f";
	public static final String CS_LARGE_TEXT = "g";
	public static final String CS_XSMALL_TEXT = "h";

	public static final String FONT = "SourceSansPro";

	public static final int FONT_XSMALL = 14;
	public static final int FONT_SMALL = 25;
	public static final int FONT_MEDIUM = 39;
	public static final int FONT_LARGE = 91;

	private StylePopulator() {
	}

	public static void populate() {
		Stylesheet stylesheet = StyleHelper.getStylesheet();

		EditableStyle style = new EditableStyle();
		style.setBackgroundColor(COLOR_BLUE);
		style.setBackground(new TriangleBackground(COLOR_WHITE));
		stylesheet.addRule(new ClassSelector(CS_TRIANGLE), style);

		style.clear();
		style.setBackgroundColor(COLOR_WHITE);
		style.setBorder(new ComplexRectangularBorder(0, 0, 4, 0));
		style.setBorderColor(COLOR_WHITE);
		style.setPadding(new ComplexOutline(0, 0, 17, 0));
		style.setMargin(new ComplexOutline(0, 1, 0, 2));
		style.setAlignment(GraphicsContext.HCENTER_TOP);
		style.setFontProfile(new FontProfile(FONT, FONT_XSMALL, Font.STYLE_PLAIN));
		stylesheet.addRule(new ClassSelector(CS_DAY), style);

		style.clear();
		style.setBorderColor(COLOR_BLUE);
		stylesheet.addRule(new AndCombinator(new ClassSelector(CS_DAY), new StateSelector(State.Checked)), style);

		style.clear();
		style.setMargin(new ComplexOutline(0, 10, 0, 9));
		stylesheet.addRule(new ClassSelector(CS_WEEK), style);

		style.clear();
		style.setAlignment(GraphicsContext.HCENTER_VCENTER);
		style.setPadding(new ComplexOutline(34, 10, 34, 10));
		style.setForegroundColor(COLOR_SUN);
		stylesheet.addRule(new ClassSelector(CS_SUN), style);

		style.clear();
		style.setFontProfile(new FontProfile(FONT, FONT_SMALL, Font.STYLE_PLAIN));
		style.setForegroundColor(COLOR_BLACK);
		style.setPadding(new ComplexOutline(34, 10, 20, 20));
		stylesheet.addRule(new ClassSelector(CS_DATE), style);

		style.clear();
		style.setPadding(new SimpleOutline());
		style.setFontProfile(new FontProfile(FONT, FONT_LARGE, Font.STYLE_BOLD));
		stylesheet.addRule(new ClassSelector(CS_LARGE_TEXT), style);

		style.clear();
		style.setPadding(new SimpleOutline());
		style.setFontProfile(new FontProfile(FONT, FONT_XSMALL, Font.STYLE_PLAIN));
		stylesheet.addRule(new ClassSelector(CS_XSMALL_TEXT), style);
	}
}
