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

	// ��
//	private double pai = 3.14159265358979323846;
	// ��������
	private RectF rectBg;

	// ���Ȳ�
	private Paint progressGroove;

	// ������
	private Paint progressBar;

	// ��������Բ
	private Paint progressRound;
	
	// ʱ������
	private Paint textPaint;

	// ���Ȳۿ�ʼ�Ƕ�
	private int startAngle = -150; // -180 180
	// ���Ȳ���ʼ�Ƕ�
	private int endAngle = 120;

	// Բ��
	// ֱ�� �뾶Ϊdiameter/2
	private int diameter = 450;

	// ���ȿ��
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

		// ��ʼ�����Ȳ۵Ļ���
		progressGroove = new Paint();
		progressGroove.setAntiAlias(true);
		progressGroove.setFlags(Paint.ANTI_ALIAS_FLAG);// �����������
		progressGroove.setStrokeWidth(barStrokeWidth);
		progressGroove.setColor(0XFFffffff);
		progressGroove.setStrokeCap(Cap.ROUND);// ������ʽΪԲ
		progressGroove.setStyle(Paint.Style.STROKE);// �����пյ���ʽ
		// ��ʼ���������Ļ���
		progressBar = new Paint();
		progressBar.setAntiAlias(true);
		progressBar.setFlags(Paint.ANTI_ALIAS_FLAG);// �����������
		progressBar.setStrokeWidth(barWidth);
		progressBar.setColor(0XFF9cd7f3);
		progressBar.setStrokeCap(Cap.ROUND);// ������ʽΪԲ
		progressBar.setStyle(Paint.Style.STROKE);// �����пյ���ʽ
		// ��ʼ������Բ�Ļ���
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
		// ���㻡�ε�Բ�ĺͰ뾶��
//		int cx1 = (left + right) / 2;
//		int cy1 = (left + right) / 2;
//		int arcRadius = (right - left) / 2;
		
		// �����Ȳ�
		canvas.drawArc(rectBg, startAngle, endAngle, false, progressGroove);
		// ��������

		canvas.drawArc(rectBg, startAngle, barEndAngle, false, progressBar);

		// �Ự����
		canvas.drawText(startTime, 10, getHeight(), textPaint);
		canvas.drawText(endTime, getWidth() -100, getHeight(), textPaint);
		
		
//		Log.e("��ǰ���������ӽǶ�", "==========" + barEndAngle);
		// ���Ǹ�СԲ
//		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dian);

//		int height = bitmap.getHeight();
//		int width = bitmap.getWidth();
		
		// �����ȵ�СԲ��
//		float l1 = (float) (cx1 + arcRadius * Math.cos(((startAngle + barEndAngle) * Math.PI / 180)));
//		float l2 = (float) (cy1 + arcRadius * Math.sin(((startAngle + barEndAngle) * Math.PI / 180)));
//		canvas.drawCircle(l1, l2, barStrokeWidth / 2, progressBar);
//		
//		l1 = l1 - width / 2;
//		l2 = l2 - height / 2;
//		canvas.drawBitmap(bitmap, l1, l2, progressRound);

	}

}
