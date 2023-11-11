package com.tutorialspoint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Time {
	public static void main(String[] args) {
		Date date = new Date();
		long diff = date.getTime();
		System.out.println("Current date_in_millisec: " + diff + " milliseconds in present.");

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		String formattedDate = dateFormat.format(date);
		System.out.println("Formatted date: " + formattedDate + " present");

		System.out.println("------------------------------------------------------------");
		long millisecond = 60000;
		long min = 60 * millisecond;
		if (min == 0) {
			System.out.println(diff + " milliseconds in present.");
			System.out.println("Formatted date: " + formattedDate + " present");
		} else if (min < 0) {
			long pastDiff = diff - min;
			System.out.println(pastDiff + "	 milliseconds in past.");

			String formattedPastDate = dateFormat.format(new Date(pastDiff));
			System.out.println("Formatted date: " + formattedPastDate + " past");
		} else {
			long futureDiff = diff + min;
			System.out.println(+futureDiff + " milliseconds in future.");

			String formattedFutureDate = dateFormat.format(new Date(futureDiff));
			System.out.println("Formatted date: " + formattedFutureDate + " future");
		}
	}
}
