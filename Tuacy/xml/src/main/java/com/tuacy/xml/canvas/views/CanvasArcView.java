package com.tuacy.xml.canvas.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class CanvasArcView extends View {

	private Paint  mPaint;
	private Paint  mShaderPaint;
	private RectF  mOvalRectF;// = new RectF(10, 10, 40, 30);
	private Shader mShader;

	public CanvasArcView(Context context) {
		this(context, null);
	}

	public CanvasArcView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CanvasArcView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		mPaint = new Paint();
		mPaint.setColor(Color.GREEN);
		mOvalRectF = new RectF();
		mShaderPaint = new Paint();
		mShader = new LinearGradient(10, 80, 110, 180, new int[]{Color.RED,
															   Color.GREEN,
															   Color.BLUE,
															   Color.YELLOW,
															   Color.LTGRAY}, null, Shader.TileMode.REPEAT);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int width;
		int height;
		if (widthMode == MeasureSpec.EXACTLY) {
			width = widthSize;
		} else {
			width = getPaddingLeft() + 130 + getPaddingRight();
		}

		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			height = getPaddingTop() + 200 + getPaddingBottom();
		}
		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint.setStyle(Paint.Style.STROKE);//设置空心
		mOvalRectF.set(10, 10, 40, 40);
		canvas.drawArc(mOvalRectF, 180, 180, true, mPaint);//小弧形
		mOvalRectF.set(50, 10, 80, 40);
		canvas.drawArc(mOvalRectF, 180, 180, false, mPaint);//小弧形
		mOvalRectF.set(25, 30, 65, 70);
		mPaint.setStyle(Paint.Style.FILL);
		canvas.drawArc(mOvalRectF, 0, 180, false, mPaint);//小弧形

		mShaderPaint.setShader(mShader);
		mOvalRectF.set(10, 80, 110, 180);// 设置个新的长方形，扫描测量
//		canvas.drawRect(mOvalRectF, mShaderPaint);
		canvas.drawArc(mOvalRectF, 180, 180, true, mShaderPaint);
	}
}
