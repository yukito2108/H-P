package com.snezana.doctorpractice.utils;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.ModelMap;

/**
 * Utility methods for Date-Time-String Format manipulation.
 */
public class SomeUtils {

	@DateTimeFormat(pattern = "EEE, dd-MM-yyyy")
	private static Date day;

	public static Date getDay() {
		return day;
	}

	public static void setDay(Date day) {
		SomeUtils.day = day;
	}

	public static String dateView(Date date) {
		if (date != null) {
			Format format = new SimpleDateFormat("dd-MM-yyyy");
			return format.format(date);
		} else {
			return "00-00-0000";
		}
	}

	public static long currentTime() {
		Calendar cal = Calendar.getInstance();
		long currTime = cal.getTimeInMillis();
		return currTime;
	}

	public static String dateToString(Date date) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return format.format(date);
	}
	
	public static String dateToString2(Date date) {
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		return format.format(date);
	}

	/* specific date in a appointment table */
	public static String specificDay(int n) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, n);
		Date specDay = cal.getTime();
		Format format = new SimpleDateFormat("EEE, dd-MM-yyyy");
		return format.format(specDay);
	}

	/* appointment date in a appointment table */
	public static String getApptmDay(int n, String hour) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, n);
		Date specDay = cal.getTime();
		Format format = new SimpleDateFormat("yyyy-MM-dd");
		String s1 = format.format(specDay);
		String s2 = s1 + " " + hour;
		return Base64.getEncoder().encodeToString(s2.getBytes());
	}
	
	public static String stringToStringD(String sd) {
		String result = "";
		result = sd.substring(8,10)+ "-" + sd.substring(5,7) + "-" +sd.substring(0,4) + " " + sd.substring(11,16);	
		return result;
	}

	public static Date today() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}

	public static Date stringToDate(String dateString) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		try {
			date = format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date addMinutesToDate(int minutes, Date beforeTime) {
		final long ONE_MINUTE_IN_MILLIS = 60000;// millisecs
		long curTimeInMs = beforeTime.getTime();
		Date afterAddingMins = new Date(curTimeInMs + (minutes * ONE_MINUTE_IN_MILLIS));
		return afterAddingMins;
	}

	public static void setHourStrings(ModelMap model) {
		model.addAttribute("hour8", "08:00");
		model.addAttribute("hour9", "09:00");
		model.addAttribute("hour10", "10:00");
		model.addAttribute("hour11", "11:00");
		model.addAttribute("hour12", "12:00");
		model.addAttribute("hour13", "13:00");
		model.addAttribute("hour14", "14:00");
		model.addAttribute("hour15", "15:00");
	}

}
