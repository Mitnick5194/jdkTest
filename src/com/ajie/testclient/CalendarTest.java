package com.ajie.testclient;

import java.util.Calendar;

/**
 * @author niezhenjie
 * 
 */
public class CalendarTest {
	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.add(Calendar.DAY_OF_MONTH, +7);
			int i = c.get(Calendar.MONTH);
			System.out.println(i);
			System.out.println(c.getTime());
			System.out.println(c.getActualMaximum(Calendar.DATE));
	}

}
