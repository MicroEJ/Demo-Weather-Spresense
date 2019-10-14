/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.style;

import com.microej.spresense.demo.widget.HourlyDetail;
import com.microej.spresense.demo.widget.MainBackground;
import com.microej.spresense.demo.widget.PaddedBackground;

import ej.library.ui.MicroEJColors;
import ej.microui.display.Display;
import ej.microui.display.GraphicsContext;
import ej.mwt.Composite;
import ej.style.Selector;
import ej.style.Stylesheet;
import ej.style.background.NoBackground;
import ej.style.background.PlainBackground;
import ej.style.dimension.FixedDimension;
import ej.style.outline.ComplexOutline;
import ej.style.selector.ClassSelector;
import ej.style.selector.FirstChildSelector;
import ej.style.selector.NthChildSelector;
import ej.style.selector.TypeOrSubtypeSelector;
import ej.style.selector.TypeSelector;
import ej.style.selector.combinator.AndCombinator;
import ej.style.util.EditableStyle;
import ej.style.util.StyleHelper;

/**
 * Populator for the stylesheet.
 */
public class StylePopulator {

	private static final int TOP_OFFSET = 8;

	/**
	 * Number of detail hour values to display.
	 */
	public static final int COUNT_OF_HOUR_VALUES = 3;

	/**
	 * Outline used for the demo.
	 */
	public static final int DEFAULT_OUTLINE = 5;

	private static int DisplayHeight = 0;
	private static int DisplayWidth = 0;

	private StylePopulator() {
	}

	/**
	 * Populate the stylesheet.
	 */
	public static void populate() {
		Stylesheet stylesheet = StyleHelper.getStylesheet();

		EditableStyle style = new EditableStyle();
		style.setBackground(new MainBackground(MicroEJColors.CONCRETE));
		style.setBackgroundColor(MicroEJColors.WHITE);
		style.setPadding(new ComplexOutline(0, DEFAULT_OUTLINE, DEFAULT_OUTLINE, DEFAULT_OUTLINE));
		stylesheet.addRule(new ClassSelector(ClassSelectors.MAIN_BACKGROUND), style);

		style.clear();
		style.setBackgroundColor(MicroEJColors.WHITE);
		style.setBorderColor(MicroEJColors.CONCRETE);
		style.setBackground(new PlainBackground());
		style.setDimension(new FixedDimension(getDisplayWidth(),
				getTopHeight()));
		stylesheet.addRule(new ClassSelector(ClassSelectors.TOP_BACKGROUND), style);

		style.clear();
		style.setAlignment(GraphicsContext.HCENTER_VCENTER);
		stylesheet.addRule(new ClassSelector(ClassSelectors.DATA_FRAME), style);

		style.clear();
		style.setFontProfile(FontProfiles.SMALL);
		style.setAlignment(GraphicsContext.HCENTER_BOTTOM);
		style.setForegroundColor(MicroEJColors.CONCRETE);
		stylesheet.addRule(new ClassSelector(ClassSelectors.WEATHER_DETAILS), style);

		style.clear();
		style.setAlignment(GraphicsContext.HCENTER_VCENTER);
		style.setDimension(
				new FixedDimension(MainBackground.CIRCLE_DIAMETER,
						MainBackground.CIRCLE_DIAMETER - TOP_OFFSET + DEFAULT_OUTLINE));
		stylesheet.addRule(new ClassSelector(ClassSelectors.CIRCLE), style);

		style.clear();
		style.setForegroundColor(MicroEJColors.CONCRETE_BLACK_75);
		style.setFontProfile(FontProfiles.MEDIUM_BOLD);
		stylesheet.addRule(new ClassSelector(ClassSelectors.DAY), style);

		style.clear();
		style.setForegroundColor(MicroEJColors.CONCRETE_BLACK_75);
		style.setBackground(new PaddedBackground(new ComplexOutline(DEFAULT_OUTLINE, 0, 0, 0)));
		style.setFontProfile(FontProfiles.HUGE);
		stylesheet.addRule(new ClassSelector(ClassSelectors.MAIN_TEMPERATURE), style);

		style.clear();
		style.setForegroundColor(MicroEJColors.CONCRETE);
		style.setFontProfile(FontProfiles.MEDIUM);
		style.setMargin(new ComplexOutline(0, 0, 18, 0));
		stylesheet.addRule(new ClassSelector(ClassSelectors.DATE_DETAILS), style);

		style.clear();
		style.setBackground(NoBackground.NO_BACKGROUND);
		stylesheet.addRule(new ClassSelector(ClassSelectors.NO_BACKGROUND), style);
		stylesheet.addRule(new TypeOrSubtypeSelector(Composite.class), style);

		style.clear();
		style.setPadding(new ComplexOutline(0, 0, 0, TOP_OFFSET));
		stylesheet.addRule(new ClassSelector(ClassSelectors.HOURLY_TEMPERATURE), style);

		Selector hourly = new TypeSelector(HourlyDetail.class);
		style.clear();
		style.setFontProfile(FontProfiles.MEDIUM_BOLD);
		style.setForegroundColor(MicroEJColors.CONCRETE_BLACK_75);
		stylesheet.addRule(new AndCombinator(hourly, new FirstChildSelector()), style);

		style.setForegroundColor(MicroEJColors.CONCRETE_BLACK_25);
		stylesheet.addRule(new AndCombinator(hourly, new NthChildSelector(1)), style);

		style.setForegroundColor(MicroEJColors.CONCRETE);
		stylesheet.addRule(new AndCombinator(hourly, new NthChildSelector(2)), style);

		style.clear();
		style.setForegroundColor(MicroEJColors.CORAL);
		style.setAlignment(GraphicsContext.HCENTER_VCENTER);
		stylesheet.addRule(new ClassSelector(ClassSelectors.ICON), style);

		style.clear();
		style.setForegroundColor(MicroEJColors.CONCRETE_BLACK_75);
		style.setFontProfile(FontProfiles.SMALL);
		style.setAlignment(GraphicsContext.RIGHT_VCENTER);
		stylesheet.addRule(new ClassSelector(ClassSelectors.WEATHER_VALUE), style);

		style.clear();
		style.setBackground(new PlainBackground());
		style.setBackgroundColor(0xFF2222);
		stylesheet.addRule(new ClassSelector(ClassSelectors.TEST), style);
	}

	/**
	 * Gets the top height.
	 *
	 * @return the top height.
	 */
	public static int getTopHeight() {
		return ((getDisplayHeight() - MainBackground.CIRCLE_DIAMETER) >> 1) + TOP_OFFSET;
	}

	/**
	 * Gets the display height.
	 *
	 * @return the display height.
	 */
	public static int getDisplayHeight() {
		if (DisplayHeight == 0) {
			DisplayHeight = Display.getDefaultDisplay().getHeight();
		}
		return DisplayHeight;
	}

	/**
	 * Gets the display width.
	 *
	 * @return the display width.
	 */
	public static int getDisplayWidth() {
		if (DisplayWidth == 0) {
			DisplayWidth = Display.getDefaultDisplay().getWidth();
		}
		return DisplayWidth;
	}
}
