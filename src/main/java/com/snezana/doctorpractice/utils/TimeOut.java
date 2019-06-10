package com.snezana.doctorpractice.utils;

/* Singleton pattern for saving the value of paramPage parameter used for session timeout detection*/
public class TimeOut {
	public static final int INAPPL = 1;
	public static final int LOGOUT = 0;

	private static TimeOut instance = null;
	/** Placeholder for pages in application ({@link #INAPPL}) or logout page ({@link #LOGOUT}). */
	public int paramPage;

	private TimeOut() {
		paramPage = LOGOUT;
	}

	public static TimeOut getInstance() {
		if (instance == null)
			instance = new TimeOut();
		return instance;
	}
}
