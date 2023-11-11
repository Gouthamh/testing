package com.tutorialspoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Snippet {
	public static void main(String[] args) throws ParseException {
		Date currentDate = new Date();
		System.out.println(currentDate);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		String current = dateFormat.format(currentDate);
		System.out.println("current "+current);
		int value = 60;
		if (value == 0) {
			System.out.println(current);
		} else {
			Date date = dateFormat.parse(current);
			System.out.println("date   " + date);
			Calendar calendar = Calendar.getInstance();
			// System.out.println("calendar "+calendar);
			calendar.setTime(date);
			calendar.add(Calendar.MINUTE, value);
			Date updatedTime = calendar.getTime();
			System.out.println(updatedTime);
			String secondDate = dateFormat.format(updatedTime);
			System.out.println("secondDate  " + secondDate);

		}

	}
}
