package com.devsh.openweathermap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Prefs {
	
	private static String FILE_NAME = "android-weather";
	
	private static final String LAST_UPDATED_TIME = "updated_time";
	
	public static void initialization(Context context) {
		mPreference = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
	}
	
	private static SharedPreferences mPreference ;
	
	private static void checkPreferenceHasInit() {
		if(mPreference == null) {
			throw new RuntimeException("PreferenceHelper is not initialization");
		}
	}
	
	public static long getUpdateTime(){
		return readLong(LAST_UPDATED_TIME,0);
	}
	public static void setUpdateTime(long v){
		write(LAST_UPDATED_TIME,v);
	}
	
	public static void write( String k, long v) {
    	checkPreferenceHasInit();
        Editor editor = mPreference.edit();
        editor.putLong(k, v);
        editor.commit();
    }
	
    public static void write( String k, int v) {
    	checkPreferenceHasInit();
        Editor editor = mPreference.edit();
        editor.putInt(k, v);
        editor.commit();
    }

    public static void write( String k,boolean v) {
    	checkPreferenceHasInit();
        Editor editor = mPreference.edit();
        editor.putBoolean(k, v);
        editor.commit();
    }

    public static void write( String k, String v) {

    	checkPreferenceHasInit();
        Editor editor = mPreference.edit();
        editor.putString(k, v);
        editor.commit();
    }

    public static int readInt( String k) {
    	checkPreferenceHasInit();
        return mPreference.getInt(k, 0);
    }

    public static int readInt( String k,
            int defv) {
    	checkPreferenceHasInit();
        return mPreference.getInt(k, defv);
    }
    
    public static long readLong( String k) {
    	checkPreferenceHasInit();
        return mPreference.getLong(k, 0L);
    }

    public static long readLong( String k,long defv) {
    	checkPreferenceHasInit();
        return mPreference.getLong(k, defv);
    }

    public static boolean readBoolean( String k) {
    	checkPreferenceHasInit();
        return mPreference.getBoolean(k, false);
    }

    public static boolean readBoolean(
            String k, boolean defBool) {
    	checkPreferenceHasInit();
        return mPreference.getBoolean(k, defBool);
    }

    public static String readString( String k) {
    	checkPreferenceHasInit();
        return mPreference.getString(k, null);
    }

    public static String readString( String k,
            String defV) {
    	checkPreferenceHasInit();
        return mPreference.getString(k, defV);
    }

    public static void remove( String k) {
    	checkPreferenceHasInit();
        Editor editor = mPreference.edit();
        editor.remove(k);
        editor.commit();
    }

    public static void clean() {
    	checkPreferenceHasInit();
        Editor editor = mPreference.edit();
        editor.clear();
        editor.commit();
    }
}
