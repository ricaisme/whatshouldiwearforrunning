package com.devsh.openweathermap.m.loc;

import com.devsh.openweathermap.library.response.OpenWeatherResponse;
import com.devsh.openweathermap.library.response.UVIndexResponse;
import com.devsh.openweathermap.m.BasePresenter;
import com.devsh.openweathermap.m.BaseView;

public interface WeatheContract {
	
	interface View extends BaseView<Presenter> {
		public void showLocation(double lat,double lon);
		public void showWeather(OpenWeatherResponse resp);
		public void showUVIndex(UVIndexResponse resp);
	}

	interface Presenter extends BasePresenter {
		public void getCurrentWeather();
	}
}
