package com.tuacy.xml.canvas.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class CanvasPolygonView extends View {

	private Paint  mPaint;
	private Path mPath;

	public CanvasPolygonView(Context context) {
		this(context, null);
	}

	public CanvasPolygonView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CanvasPolygonView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		mPaint = new Paint();
		mPaint.setColor(Color.GREEN);
		mPath = new Path();
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
			width = getPaddingLeft() + 170 + getPaddingRight();
		}

		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			height = getPaddingTop() + 50 + getPaddingBottom();
		}
		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint.setStyle(Paint.Style.STROKE);
		mPath.moveTo(10, 10);
		mPath.lineTo(30, 30);
		mPath.lineTo(50, 10);
		mPath.close();
		canvas.drawPath(mPath, mPaint);

		mPath.moveTo(60, 10);
		mPath.lineTo(60, 30);
		mPath.lineTo(100, 30);
		mPath.lineTo(150, 10);
		mPath.close();
		canvas.drawPath(mPath, mPaint);
	}
}
