package com.gyzh.zain.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	public static String format(long time){
		return format.format(new Date(time));
	}
}
