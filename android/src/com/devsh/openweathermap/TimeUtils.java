package com.devsh.openweathermap;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@SuppressLint("SimpleDateFormat")
public class TimeUtils {

    private final static ThreadLocal<SimpleDateFormat> FORMAT_DATE_TIME = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> FORMAT_DATE = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
	
    private final static ThreadLocal<SimpleDateFormat> FORMAT_HHmm = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HH:mm");
        }
    };
    
    private final static ThreadLocal<SimpleDateFormat> FORMAT_HHmmss = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HH:mm:ss");
        }
    };
    
    /**
     * 返回当前系统时间(格式以HH:mm形式)
     */
    public static String getTime_HHmm() {
    	return FORMAT_HHmm.get().format(new Date());
    }
    
    /**
     * 返回当前系统时间(格式以HH:mm:ss形式)
     */
    public static String getTime_HHmmss() {
    	return FORMAT_HHmmss.get().format(new Date());
    }
    
    /**
     * 返回当前系统时间(格式以yyyy-MM-dd形式)
     */
    public static String getDate() {
    	return FORMAT_DATE.get().format(new Date());
    }
    
    /**
     * 返回当前系统时间(格式以yyyy-MM-dd HH:mm:ss形式)
     */
    public static String getDateTime() {
    	return FORMAT_DATE_TIME.get().format(new Date());
    }
    
    public static String getDateTime(long millis) {
    	return FORMAT_DATE_TIME.get().format(new Date(millis));
    }
    
    public static String friendlyTime(long millis) {
    	String formatDate = FORMAT_DATE_TIME.get().format(new Date(millis));
    	return friendlyTime(formatDate);
    }
    

    public static String friendlyTime(String sdate) {
        Date time = null;

        if (isInEasternEightZones()) {
            try {
				time = FORMAT_DATE_TIME.get().parse(sdate);
			} catch (ParseException e) {
			}
        } else {
        	try {
        		time = transformTime(FORMAT_DATE_TIME.get().parse(sdate), TimeZone.getTimeZone("GMT+08"),
                    TimeZone.getDefault());
        	} catch (ParseException e) {
			}
        }

        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();


        String curDate = FORMAT_DATE.get().format(cal.getTime());
        String paramDate = FORMAT_DATE.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + " minute ago";
            else
                ftime = hour + " hours ago";
            return "Updated about " + ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + " minute ago";
            else
                ftime = hour + " hours ago";
        } else if (days == 1) {
            ftime = " yesterday";
        } else if (days == 2) {
            ftime = " on the day before yesterday ";
        } else if (days > 2 && days < 31) {
            ftime = days + " days ago";
        } else if (days >= 31 && days <= 2 * 31) {
            ftime = " 1 months ago";
        } else if (days > 2 * 31 && days <= 3 * 31) {
            ftime = " 2 months ago";
        } else if (days > 3 * 31 && days <= 4 * 31) {
            ftime = " 3 months ago";
        } else {
            ftime = FORMAT_DATE.get().format(time);
        }
        return ftime;
    }


    public static boolean isInEasternEightZones() {
        boolean defaultVaule = true;
        if (TimeZone.getDefault() == TimeZone.getTimeZone("GMT+08"))
            defaultVaule = true;
        else
            defaultVaule = false;
        return defaultVaule;
    }


    public static Date transformTime(Date date, TimeZone oldZone,
            TimeZone newZone) {
        Date finalDate = null;
        if (date != null) {
            int timeOffset = oldZone.getOffset(date.getTime())
                    - newZone.getOffset(date.getTime());
            finalDate = new Date(date.getTime() - timeOffset);
        }
        return finalDate;
    }
    
}
