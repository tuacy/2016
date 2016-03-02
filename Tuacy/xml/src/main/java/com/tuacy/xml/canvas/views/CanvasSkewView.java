package com.tuacy.xml.canvas.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CanvasSkewView extends View {

	private Paint  mPaint;
	private Paint  mPaint1;
	private RectF  mRectF;

	public CanvasSkewView(Context context) {
		this(context, null);
	}

	public CanvasSkewView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CanvasSkewView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		mPaint = new Paint();
		mPaint.setColor(Color.GREEN);
		mPaint1 = new Paint();
		mPaint1.setColor(Color.RED);
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
			width = getPaddingLeft() + 440+ getPaddingRight();
		}

		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			height = getPaddingTop() + 150 + getPaddingBottom();
		}
		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint.setStyle(Paint.Style.STROKE);

		mPaint.setColor(Color.GREEN);
		mRectF.set(20, 20, 200, 100);
		canvas.drawRect(mRectF, mPaint);

		mPaint1.setStyle(Paint.Style.STROKE);
		canvas.skew(1,0);//x 60度
		mRectF.set(20, 20, 200, 100);
		canvas.drawRect(mRectF, mPaint1);
	}
}
