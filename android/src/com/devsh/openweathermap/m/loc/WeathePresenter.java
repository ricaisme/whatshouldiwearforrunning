package com.devsh.openweathermap.m.loc;

import com.devsh.openweathermap.library.OpenWeatherMap;
import com.devsh.openweathermap.library.response.OpenWeatherResponse;
import com.devsh.openweathermap.library.response.OpenWeatherResponseCallback;
import com.devsh.openweathermap.library.response.UVIndexResponse;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import retrofit2.Call;

public class WeathePresenter implements WeatheContract.Presenter{

	private static final String TAG = "LocationPresenter";
	private static final String API_KEY = "b6f14dd30937827baa4426b1befccec4";
	
	private double latitude = 0.0;
	private double longitude = 0.0;
	
	private LocationManager lm;
	private Context mContext;
	private WeatheContract.View mViewModel;
	
	public WeathePresenter(Context cxt, WeatheContract.View view) {
		mContext = cxt;
		mViewModel = view;
		mViewModel.setPresenter(this);
	}
	
	@Override
	public void start() {
		OpenWeatherMap.initialize(API_KEY);
		lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
	}

	@Override
	public void getCurrentWeather() {
		if(lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			getLocationBy(LocationManager.NETWORK_PROVIDER);
		} else {
			getLocationBy(LocationManager.GPS_PROVIDER);
		}
	}
	
	void getLocationBy(String provider){
		Location location = lm.getLastKnownLocation(provider);
		if (location != null) {
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			if(mViewModel != null) {
				mViewModel.showLocation(latitude, longitude);
			}
			OpenWeatherMap.getWeatherGeoLocation(latitude +"", longitude + "",new CurrentWeatcherResponseCallback());
			OpenWeatherMap.getUVIndexGeoLocation(latitude +"", longitude + "", new UVIndexResponseCallback());
		} else {
			lm.requestLocationUpdates(provider, 1000, 0, locationListener);
		}
	}
	
	LocationListener locationListener = new LocationListener() {
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
		}

		@Override
		public void onProviderEnabled(String provider) {
			// Provider被enable时触发此函数，比如GPS被打开
			Log.e(TAG, provider);
		}

		@Override
		public void onProviderDisabled(String provider) {
			// Provider被disable时触发此函数，比如GPS被关闭
			Log.e(TAG, provider);
		}

		@Override
		public void onLocationChanged(Location location) {
			// 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
			if (location != null) {
				Log.e("Map", "Location changed : Lat: " + location.getLatitude() + " Lng: " + location.getLongitude());
				latitude = location.getLatitude(); // 经度
				longitude = location.getLongitude(); // 纬度
				if(mViewModel != null) {
					mViewModel.showLocation(latitude, longitude);
				}
				OpenWeatherMap.getWeatherGeoLocation(latitude + "", longitude + "",new CurrentWeatcherResponseCallback());
				OpenWeatherMap.getUVIndexGeoLocation(latitude +"", longitude + "", new UVIndexResponseCallback());
			}
		}
	};
	
	class CurrentWeatcherResponseCallback implements OpenWeatherResponseCallback<OpenWeatherResponse> {
        @Override
        public void onResponseSuccess(OpenWeatherResponse response) {
//            OpenWeatherMap.printResponse(response);
            if(mViewModel != null) {
            	mViewModel.showWeather(response);
            }
        }

        @Override
        public void onResponseFail(int statusCode, String error) {
        	Log.e(TAG, error);
        }

        @Override
        public void onFailure(Call<OpenWeatherResponse> call, Throwable t) {
        	Log.e(TAG, "error ",t);
        }
    }
	
	class UVIndexResponseCallback implements OpenWeatherResponseCallback<UVIndexResponse> {
        @Override
        public void onResponseSuccess(UVIndexResponse response) {
//        	OpenWeatherMap.printResponse(response);
            
            if(mViewModel != null) {
            	mViewModel.showUVIndex(response);
            }
        }

        @Override
        public void onResponseFail(int statusCode, String error) {
        	Log.e(TAG, error);
        }

        @Override
        public void onFailure(Call<UVIndexResponse> call, Throwable t) {
        	Log.e(TAG, "error ",t);
        }
    }
	
}
