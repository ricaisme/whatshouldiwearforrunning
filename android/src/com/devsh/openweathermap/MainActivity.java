package com.devsh.openweathermap;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.devsh.openweathermap.library.response.OpenWeatherResponse;
import com.devsh.openweathermap.library.response.UVIndexResponse;
import com.devsh.openweathermap.m.loc.WeatheContract;
import com.devsh.openweathermap.m.loc.WeatheContract.Presenter;
import com.devsh.openweathermap.m.loc.WeathePresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.devsh.openweathermap.R;

public class MainActivity extends Activity implements WeatheContract.View{

    private TextView txt_update_tips;
    private TextView txt_feelslike;
    private ProgressView progress_sun;
    
    private TextView txt_humidity;
    private TextView txt_dew_point;
    private TextView txt_visibility;
    
    private TextView txt_pressure;
    private TextView txt_uv_index;
    private TextView txt_wind;
    
    private WeathePresenter mPresenter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        
        txt_update_tips = (TextView) findViewById(R.id.txt_update_tips);
        txt_feelslike = (TextView) findViewById(R.id.txt_feelslike);
        progress_sun = (ProgressView) findViewById(R.id.progress_sun);
        
        txt_humidity = (TextView) findViewById(R.id.txt_humidity);
        txt_dew_point = (TextView) findViewById(R.id.txt_dew_point);
        txt_visibility = (TextView) findViewById(R.id.txt_visibility);
        
        txt_pressure = (TextView) findViewById(R.id.txt_pressure);
        txt_uv_index = (TextView) findViewById(R.id.txt_uv_index);
        txt_wind = (TextView) findViewById(R.id.txt_wind);
        
        new WeathePresenter(getApplicationContext(),this).start();
        mPresenter.getCurrentWeather();
        
    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		if (item.getItemId() == R.id.action_refresh) {
			mPresenter.getCurrentWeather();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
    
	@Override
	public void setPresenter(Presenter presenter) {
		mPresenter = (WeathePresenter) presenter;
	}

	@Override
	public void showLocation(double lat, double lon) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showWeather(OpenWeatherResponse resp) {
		// TODO Auto-generated method stub
		
		long updateTime = Prefs.getUpdateTime();
		long newTime    = System.currentTimeMillis();
		Prefs.setUpdateTime(newTime);
		String friendlyTime = TimeUtils.friendlyTime(updateTime == 0? newTime:updateTime);
		txt_update_tips.setText(friendlyTime);
		
//		华氏度 = 32 + 摄氏度 × 1.8
//		摄氏度 = (华氏度 - 32) ÷ 1.8
//		摄氏度  = 开尔文 - 273.13
		double temp =Double.parseDouble(resp.getMain().getTemp()) - 273.13d;
		txt_feelslike.setText(Math.round(temp) + " ℃");
		txt_humidity.setText(resp.getMain().getHumidity() + "%");
		txt_dew_point.setText("# %"); 
		txt_visibility.setText(TextUtils.isEmpty(resp.getVisibility())? "# Mi":resp.getVisibility() + " Mi"); //可见度
		
		txt_pressure.setText(resp.getMain().getPressure() + " hPa");  // 气压
//		txt_uv_index.setText("# LOW");  //紫外线指数
		txt_wind.setText(resp.getWind().getSpeed() +" MPH");
		
		long sunrise = Long.parseLong(resp.getSys().getSunrise()) * 1000L;
		long sunset = Long.parseLong(resp.getSys().getSunset()) * 1000L;
		
		progress_sun.setStartTime(formatTime(sunrise));
		progress_sun.setEndTime(formatTime(sunset));
		progress_sun.setProgress(calcProgress(sunrise,sunset));
		
		getActionBar().setTitle(resp.getName());
		
		Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void showUVIndex(UVIndexResponse resp) {
		// TODO Auto-generated method stub
		txt_uv_index.setText(resp.getData() +" LOW");  //紫外线指数
	}
	
	String formatTime(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(time);
	}
	
	int calcProgress(long sunrise , long sunset) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(sunrise);
		
		long max = sunset - sunrise;
		long curr = System.currentTimeMillis() - sunrise;
		int progress = Math.round((curr * 1.0F / max * 100F));
		
		if(progress< 0)
			progress = 0;
		if(progress > 100)
			progress = 100;
		
		return progress;
	}
}
