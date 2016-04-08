package com.tuacy.bezier.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class BezierView extends View {

	private Paint mPaint;
	private Path  mPath;

	public BezierView(Context context) {
		this(context, null);
	}

	public BezierView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(5);
		mPaint.setColor(0xFF009688);

		mPath = new Path();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		mPath.moveTo(100, 100);
		mPath.cubicTo(800, 100, 100, 800, 800, 800);

		canvas.drawPath(mPath, mPaint);
	}
}
