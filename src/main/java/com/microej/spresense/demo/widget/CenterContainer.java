/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.widget;

import java.util.Iterator;

import ej.mwt.MWT;
import ej.mwt.Widget;
import ej.style.Style;
import ej.style.container.Rectangle;
import ej.widget.StyledComposite;

/**
 *
 */
public class CenterContainer extends StyledComposite {

	private Widget first;
	private Widget last;
	private Widget center;
	/**
	 *
	 */
	public CenterContainer() {
	}

	/**
	 * Sets the first.
	 *
	 * @param first
	 *            the first to set.
	 */
	public void setFirst(Widget first) {
		this.first = first;
		setWidgets();
	}

	/**
	 *
	 */
	private void setWidgets() {
		removeAllWidgets();
		if (first != null) {
			add(first);
		}
		if (center != null) {
			add(center);
		}
		if (last != null) {
			add(last);
		}
	}

	/**
	 * Gets the first.
	 *
	 * @return the first.
	 */
	public Widget getFirst() {
		return first;
	}

	/**
	 * Sets the last.
	 *
	 * @param last
	 *            the last to set.
	 */
	public void setLast(Widget last) {
		this.last = last;
		setWidgets();
	}

	/**
	 * Gets the last.
	 *
	 * @return the last.
	 */
	public Widget getLast() {
		return last;
	}

	/**
	 * Sets the center.
	 *
	 * @param center
	 *            the center to set.
	 */
	public void setCenter(Widget center) {
		this.center = center;
		setWidgets();
	}

	/**
	 * Gets the center.
	 *
	 * @return the center.
	 */
	public Widget getCenter() {
		return center;
	}

	@Override
	public boolean contains(int x, int y) {
		if (getWidgetsCount() == 0) {
			return super.contains(x, y);
		}
		int relativeX = x - getX();
		int relativeY = y - getY();
		// browse children recursively
		Iterator<Widget> iterator = iterator();
		while (iterator.hasNext()) {
			Widget widget = iterator.next();
			Widget at = widget.getWidgetAt(relativeX, relativeY);
			if (at != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Rectangle validateContent(Style style, Rectangle bounds) {
		int widthHint = bounds.getWidth();
		int heightHint = bounds.getHeight();

		center.validate(MWT.NONE, MWT.NONE);
		int currentWidth = center.getPreferredWidth();
		widthHint = (widthHint == MWT.NONE) ? MWT.NONE : (widthHint - currentWidth) / 2;
		if (first != null) {
			first.validate(widthHint, heightHint);
			currentWidth += first.getPreferredWidth();
		}
		if (last != null) {
			last.validate(widthHint, heightHint);
			currentWidth += last.getPreferredWidth();
		}

		return new Rectangle(0, 0, currentWidth, center.getPreferredHeight());
	}

	@Override
	protected void setBoundsContent(Rectangle bounds) {
		int boundsX = bounds.getX();
		int boundsY = bounds.getY();
		int boundsWidth = bounds.getWidth();
		int boundsHeight = bounds.getHeight();
		int centerWidth = center.getPreferredWidth();
		int centerHeight = center.getPreferredHeight();
		int spareWidth = boundsWidth-centerWidth;
		int spareHeight = boundsHeight-centerHeight;
		center.setBounds(boundsX + spareWidth/2, boundsY+ spareHeight/2, centerWidth, centerHeight);
		if(first!=null) {
			first.setBounds(boundsX, boundsY, spareWidth / 2, boundsHeight);
		}
		if(last!=null) {
			last.setBounds(boundsX + spareWidth / 2 + centerWidth, boundsY, spareWidth / 2, boundsHeight);
		}
	}
}
