package com.devsh.openweathermap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.RectF;
import android.provider.CalendarContract.Colors;
import android.support.v4.graphics.ColorUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ProgressView extends View {

	// π
//	private double pai = 3.14159265358979323846;
	// 背景矩形
	private RectF rectBg;

	// 进度槽
	private Paint progressGroove;

	// 进度条
	private Paint progressBar;

	// 进度条的圆
	private Paint progressRound;
	
	// 时间文字
	private Paint textPaint;

	// 进度槽开始角度
	private int startAngle = -150; // -180 180
	// 进度槽起始角度
	private int endAngle = 120;

	// 圆心
	// 直径 半径为diameter/2
	private int diameter = 450;

	// 进度宽度
//	private int barStrokeWidth = 15;
//	private int barWidth = 18;
	private int barStrokeWidth = 12;
	private int barWidth = 15;

	private String startTime = "6:46";
	private String endTime = "19:36";
	
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public ProgressView(Context context) {
		super(context);
		init();
	}

	public ProgressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	float barEndAngle = 0;
	private int left;
	private int top;
	private int right;
	private int bottom;
	private int scrrenwidth, scrrenheight;

	public int getScrrenwidth() {
		return scrrenwidth;
	}

	public void setScrrenwidth(int scrrenwidth) {
		this.scrrenwidth = scrrenwidth;
	}

	public int getScrrenheight() {
		return scrrenheight;
	}

	public void setScrrenheight(int scrrenheight) {
		this.scrrenheight = scrrenheight;
	}

	private void init() {

		// 初始化进度槽的画笔
		progressGroove = new Paint();
		progressGroove.setAntiAlias(true);
		progressGroove.setFlags(Paint.ANTI_ALIAS_FLAG);// 帮助消除锯齿
		progressGroove.setStrokeWidth(barStrokeWidth);
		progressGroove.setColor(0XFFffffff);
		progressGroove.setStrokeCap(Cap.ROUND);// 画笔样式为圆
		progressGroove.setStyle(Paint.Style.STROKE);// 设置中空的样式
		// 初始化进度条的画笔
		progressBar = new Paint();
		progressBar.setAntiAlias(true);
		progressBar.setFlags(Paint.ANTI_ALIAS_FLAG);// 帮助消除锯齿
		progressBar.setStrokeWidth(barWidth);
		progressBar.setColor(0XFF9cd7f3);
		progressBar.setStrokeCap(Cap.ROUND);// 画笔样式为圆
		progressBar.setStyle(Paint.Style.STROKE);// 设置中空的样式
		// 初始化进度圆的画笔
		progressRound = new Paint();
		progressRound.setAntiAlias(true);
		
		textPaint = new Paint();
		textPaint.setTextSize(42);
		textPaint.setColor(Color.parseColor("#d9dee1"));
		
	}

	public void setProgress(int progress) {
		float a = ((float) progress / 100) * endAngle;
//		Log.i("test", "----" + a);
		barEndAngle = a;
		// barEndAngle=progress;

		invalidate();
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Log.i("test", getWidth() + "//");
//		left = getWidth() / 2 - 180;//150;
//		top = getWidth() / 2 - 180;//150;
//		right = getWidth() / 2 + 180;//150;
//		bottom = getWidth() / 2 + 180;//150;
		
		left   = 30;
		top    = 30;
		right  = getWidth() -30;
		bottom = getWidth() -30;
		
		rectBg = new RectF(left, top, right, bottom);
		// init();
		// 计算弧形的圆心和半径。
//		int cx1 = (left + right) / 2;
//		int cy1 = (left + right) / 2;
//		int arcRadius = (right - left) / 2;
		
		// 画进度槽
		canvas.drawArc(rectBg, startAngle, endAngle, false, progressGroove);
		// 画进度条

		canvas.drawArc(rectBg, startAngle, barEndAngle, false, progressBar);

		// 会话字体
		canvas.drawText(startTime, 10, getHeight(), textPaint);
		canvas.drawText(endTime, getWidth() -100, getHeight(), textPaint);
		
		
//		Log.e("当前进度条增加角度", "==========" + barEndAngle);
		// 画那个小圆
//		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dian);

//		int height = bitmap.getHeight();
//		int width = bitmap.getWidth();
		
		// 画进度的小圆点
//		float l1 = (float) (cx1 + arcRadius * Math.cos(((startAngle + barEndAngle) * Math.PI / 180)));
//		float l2 = (float) (cy1 + arcRadius * Math.sin(((startAngle + barEndAngle) * Math.PI / 180)));
//		canvas.drawCircle(l1, l2, barStrokeWidth / 2, progressBar);
//		
//		l1 = l1 - width / 2;
//		l2 = l2 - height / 2;
//		canvas.drawBitmap(bitmap, l1, l2, progressRound);

	}

}
