package vn.ce.sale.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import android.R.string;

public class TimeUtil {
	public static int previousOrientation = 0;
	public static boolean isNeedChangOrientaion = false;

	public static int getTimeZone() {
		TimeZone tz = TimeZone.getDefault();
		Date now = new Date();
		int offsetFromUtc = tz.getOffset(now.getTime()) / 3600000;
		return offsetFromUtc;
		// String m2tTimeZoneIs = Integer.toString(offsetFromUtc);
	}

	public static String getStringTimeNow(String formatDate) {
		Date now = new Date();
		return dateToString(now, formatDate);
	}

	public static Date stringToDate(String stringValue, String formatDate) {
		// DateTime myDate = DateTime.ParseExact("2009-05-08 14:40:52,531",
		// "yyyy-MM-dd HH:mm:ss,fff",
		// System.Globalization.CultureInfo.InvariantCulture)
		try {
			return new SimpleDateFormat(formatDate).parse(stringValue);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}

	public static Date sYYYYMMDDToDateTime(String yyyyMMddHHmmss) {
		return stringToDate(yyyyMMddHHmmss, "yyyyMMddHHmmss");
	}

	public static String dateToString(Date date, String formatDate) {
		// DateTime myDate = DateTime.ParseExact("2009-05-08 14:40:52,531",
		// "yyyy-MM-dd HH:mm:ss,fff",
		// System.Globalization.CultureInfo.InvariantCulture)
		DateFormat df = new SimpleDateFormat(formatDate);
		return df.format(date);
	}

	public static Date add(Date base, int miliseconds) {
		return new Date(base.getTime() + miliseconds);
	}

	public static String GetStringNow() {
		// TODO Auto-generated method stub
		return getStringTimeNow("yyyyMMddHHmmss");
	}
}
