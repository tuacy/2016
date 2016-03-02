package com.tuacy.xml.canvas.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CanvasOvalView extends View {

	private Paint  mPaint;
	private RectF  mOvalRectF;// = new RectF(10, 10, 40, 30);

	public CanvasOvalView(Context context) {
		this(context, null);
	}

	public CanvasOvalView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CanvasOvalView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		mPaint = new Paint();
		mPaint.setColor(Color.GREEN);
		mOvalRectF = new RectF();
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
			width = getPaddingLeft() + 240 + getPaddingRight();
		}

		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			height = getPaddingTop() + 130 + getPaddingBottom();
		}
		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint.setStyle(Paint.Style.STROKE);//设置空心
		mOvalRectF.set(10, 10, 110, 90);
		canvas.drawOval(mOvalRectF, mPaint);
		mPaint.setStyle(Paint.Style.FILL);//设置空心
		mOvalRectF.set(120, 10, 220, 90);
		canvas.drawOval(mOvalRectF, mPaint);
	}
}
