/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello.style;

import com.microej.example.hello.widget.MainBackground;

import ej.microui.display.Display;
import ej.microui.display.GraphicsContext;
import ej.mwt.Composite;
import ej.style.Stylesheet;
import ej.style.background.NoBackground;
import ej.style.background.PlainBackground;
import ej.style.dimension.FixedDimension;
import ej.style.outline.SimpleOutline;
import ej.style.selector.ClassSelector;
import ej.style.selector.TypeOrSubtypeSelector;
import ej.style.util.EditableStyle;
import ej.style.util.StyleHelper;

public class StylePopulator {

	private static int displayHeight = 0;
	private static int displayWidth = 0;

	private static final int TOP_OFFSET = 8;
	public static final int DEFAULT_OUTLINE = 5;

	private StylePopulator() {
	}

	public static void populate() {
		Stylesheet stylesheet = StyleHelper.getStylesheet();

		EditableStyle style = new EditableStyle();
		style.setBackground(new MainBackground(Colors.GREY_LIGHT));
		style.setBackgroundColor(Colors.WHITE);
		style.setPadding(new SimpleOutline(DEFAULT_OUTLINE));
		stylesheet.addRule(new ClassSelector(ClassSelectors.MAINBACKGROUND), style);

		style.clear();
		style.setBackgroundColor(Colors.WHITE);
		style.setBorderColor(Colors.GREY_LIGHT);
		style.setBackground(new PlainBackground());
		style.setDimension(new FixedDimension(getDisplayWidth(),
				(getDisplayHeight() - MainBackground.CIRCLE_DIAMETER) / 2 + TOP_OFFSET));
		stylesheet.addRule(new ClassSelector(ClassSelectors.TOPBACKGROUND), style);

		style.clear();
		style.setAlignment(GraphicsContext.HCENTER_VCENTER);
		stylesheet.addRule(new ClassSelector(ClassSelectors.DATA_FRAME), style);

		style.clear();
		style.setFontProfile(FontProfiles.SMALL);
		style.setAlignment(GraphicsContext.HCENTER_VCENTER);
		style.setForegroundColor(Colors.GREY_LIGHT);
		stylesheet.addRule(new ClassSelector(ClassSelectors.DETAILS), style);

		style.clear();
		style.setAlignment(GraphicsContext.HCENTER_VCENTER);
		style.setDimension(
				new FixedDimension(MainBackground.CIRCLE_DIAMETER, MainBackground.CIRCLE_DIAMETER - 2 * TOP_OFFSET));
		stylesheet.addRule(new ClassSelector(ClassSelectors.CIRCLE), style);

		style.clear();
		style.setBackground(NoBackground.NO_BACKGROUND);
		stylesheet.addRule(new ClassSelector(ClassSelectors.NO_BACKGROUND), style);
		stylesheet.addRule(new TypeOrSubtypeSelector(Composite.class), style);

		style.clear();
		style.setBackground(new PlainBackground());
		style.setBackgroundColor(0xFF2222);
		stylesheet.addRule(new ClassSelector(ClassSelectors.TEST), style);
	}

	public static int getDisplayHeight() {
		if (displayHeight == 0) {
			displayHeight = Display.getDefaultDisplay().getHeight();
		}
		return displayHeight;
	}

	public static int getDisplayWidth() {
		if (displayWidth == 0) {
			displayWidth = Display.getDefaultDisplay().getWidth();
		}
		return displayWidth;
	}
}
