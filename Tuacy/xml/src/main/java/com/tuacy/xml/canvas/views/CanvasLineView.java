package com.tuacy.xml.canvas.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.tuacy.common.log.CustomLog;

public class CanvasLineView extends View {

	private Paint mPaint;

	public CanvasLineView(Context context) {
		this(context, null);
	}

	public CanvasLineView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CanvasLineView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		mPaint = new Paint();
		mPaint.setColor(Color.GREEN);
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
			width = getPaddingLeft() + 520 + getPaddingRight();
		}

		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			height = getPaddingTop() + 40 + getPaddingBottom();
		}

		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//		mPaint.setStrokeWidth((float) 5.0);
		canvas.drawLine(0, 10, 100, 10, mPaint);

		canvas.drawLine(110, 10, 210, 30, mPaint);

		float[] pts = {210,
					   10,
					   310,
					   30,
					   310,
					   30,
					   410,
					   10,
					   410,
					   10,
					   510,
					   30};
		canvas.drawLines(pts, mPaint);
	}
}
