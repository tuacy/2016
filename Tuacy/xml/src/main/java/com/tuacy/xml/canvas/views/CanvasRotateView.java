package com.tuacy.xml.canvas.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CanvasRotateView extends View {

	private Paint  mPaint;
	private RectF  mRectF;

	public CanvasRotateView(Context context) {
		this(context, null);
	}

	public CanvasRotateView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CanvasRotateView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		mPaint = new Paint();
		mPaint.setColor(Color.GREEN);
		mRectF = new RectF();
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
			width = getPaddingLeft() + 220 + getPaddingRight();
		}

		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			height = getPaddingTop() + 220 + getPaddingBottom();
		}
		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint.setStyle(Paint.Style.STROKE);

		mPaint.setColor(Color.GREEN);
		mRectF.set(100, 10, 200, 100);
		canvas.drawRect(mRectF, mPaint);

		mPaint.setColor(Color.RED);
		canvas.rotate(30);//顺时针旋转画布
		mRectF.set(100, 10, 200, 100);
		canvas.drawRect(mRectF, mPaint);
	}
}
