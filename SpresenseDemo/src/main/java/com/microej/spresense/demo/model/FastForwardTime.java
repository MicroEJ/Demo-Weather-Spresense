/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.model;

import java.util.Calendar;

/**
 * Represents the current time.
 *
 * <p>
 * The time that can be updated to simulate a fast-forward time by calling method {@link #updateCurrentTime()}.
 */
public class FastForwardTime extends Time {

	private static final int MILLIS = 1000;
	private static final int MILLI_IN_MINUTES = 60 * MILLIS;
	private static final int MILLI_IN_HOUR = 60 * MILLI_IN_MINUTES;
	private static final long THRESHOLD = MILLI_IN_HOUR * 24;

	private static final long SPEED = 60 * 60;

	private final Calendar calendar;

	private long currentTime;

	private long lastUpdate;

	private long offset = 0;

	/**
	 * Constructs an instance of this class with main date fields set to current time.
	 *
	 */
	public FastForwardTime() {
		super(0, 0, 0, 0, 0);
		this.calendar = Calendar.getInstance();
		long currentTimeMillis = System.currentTimeMillis();
		this.currentTime = currentTimeMillis;
		this.offset = 0;
		updateFields(currentTimeMillis);
	}

	/**
	 * Update the time using its overspeed.
	 */
	public void updateCurrentTime() {
		long currentTimeMillis = System.currentTimeMillis();
		long elapsed = (currentTimeMillis - this.currentTime) * SPEED;
		this.currentTime = currentTimeMillis;
		this.offset += elapsed;
		this.lastUpdate += elapsed;

		if (this.lastUpdate > THRESHOLD) {
			// update all fields using the composing calendar instance only in the case the day changed
			long newTime = this.currentTime + this.offset;
			updateFields(newTime);
		} else {
			// update only hour and minute fields: no need to use the calendar complexity in this case
			this.hour = (int) (this.lastUpdate / MILLI_IN_HOUR);
			this.minute = (int) ((this.lastUpdate % MILLI_IN_HOUR) / MILLI_IN_MINUTES);
		}
	}

	private void updateFields(long newTime) {
		final Calendar calendar = this.calendar;

		calendar.setTimeInMillis(newTime);
		this.year = calendar.get(Calendar.YEAR);
		this.month = calendar.get(Calendar.MONTH);
		this.day = calendar.get(Calendar.DAY_OF_MONTH);
		this.dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		this.hour = calendar.get(Calendar.HOUR_OF_DAY);
		this.minute = calendar.get(Calendar.MINUTE);
		this.lastUpdate = this.hour * MILLI_IN_HOUR + this.minute * MILLI_IN_MINUTES
				+ calendar.get(Calendar.SECOND) * MILLIS + newTime % MILLIS;
	}

}
