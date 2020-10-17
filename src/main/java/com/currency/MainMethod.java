package com.currency;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainMethod {

	public static void main(String[] args) {
		
		for(int i=0; i<6; i++) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -i);

		Date todayDate = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		String requiredDate = sdf.format(todayDate);
		System.out.println(requiredDate);
		}
	}
}
