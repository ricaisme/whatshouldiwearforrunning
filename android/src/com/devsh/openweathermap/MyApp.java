package com.devsh.openweathermap;

import android.app.Application;
import android.content.Context;


public class MyApp extends Application {
	private static MyApp instance = null;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;

		Prefs.initialization(getContext());
	}

	public static MyApp getInstance() {
		return instance;
	}

	public static Context getContext() {
		return instance.getApplicationContext();
	}
}
