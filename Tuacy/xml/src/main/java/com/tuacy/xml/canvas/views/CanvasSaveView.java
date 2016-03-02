package com.tuacy.xml.canvas.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CanvasSaveView extends View {

	private Paint  mPaint;
	private RectF  mRectF;

	public CanvasSaveView(Context context) {
		this(context, null);
	}

	public CanvasSaveView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CanvasSaveView(Context context, AttributeSet attrs, int defStyleAttr) {
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
			width = getPaddingLeft() + 200+ getPaddingRight();
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
		canvas.drawColor(Color.RED);
		canvas.save();

		mRectF.set(25, 25, 175, 175);
		canvas.clipRect(mRectF);
		canvas.drawColor(Color.GREEN);
		canvas.save();

		mRectF.set(75, 75, 125, 125);
		canvas.clipRect(mRectF);
		canvas.drawColor(Color.BLUE);
		canvas.save();

		canvas.restore();
		canvas.restore();
		canvas.drawColor(Color.YELLOW);
	}
}
