package com.tuacy.xml.canvas.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class CanvasRectView extends View {

	private Paint mPaint;
	private Rect mRect;

	public CanvasRectView(Context context) {
		this(context, null);
	}

	public CanvasRectView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CanvasRectView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		mPaint = new Paint();
		mPaint.setColor(Color.GREEN);
		mRect = new Rect();
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
			width = getPaddingLeft() + 160 + getPaddingRight();
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
		mPaint.setStyle(Paint.Style.FILL);//设置填满
		canvas.drawRect(10, 10, 40, 30, mPaint);// 正方形
		canvas.drawRect(50, 10, 70, 30, mPaint);// 长方形
		mRect.set(80, 10, 110, 30);
		canvas.drawRect(mRect, mPaint);
		mRect.set(120, 10, 140, 30);
		canvas.drawRect(mRect, mPaint);
	}
}
