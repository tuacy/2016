package com.tuacy.xml.canvas.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.tuacy.xml.R;

public class CanvasTextView extends View {

	private Paint  mPaint;
	private Rect   mBound;
	private String mTitleText;
	private int    mTitleTextColor;
	private int    mTitleTextSize;

	public CanvasTextView(Context context) {
		this(context, null);
	}

	public CanvasTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CanvasTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CanvasTextView, defStyleAttr, 0);
		int count = typeArray.getIndexCount();
		for (int i = 0; i < count; i++) {
			int attr = typeArray.getIndex(i);
			switch (attr) {
				case R.styleable.CanvasTextView_customerTitleText:
					mTitleText = typeArray.getString(attr);
					break;
				case R.styleable.CanvasTextView_customerTitleTextColor:
					mTitleTextColor = typeArray.getColor(attr, Color.BLACK);
					break;
				case R.styleable.CanvasTextView_customerTitleTextSize:
					mTitleTextSize = typeArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16,
																										   getResources().getDisplayMetrics()));
					break;

			}

		}
		typeArray.recycle();
		mPaint = new Paint();
		mPaint.setTextSize(mTitleTextSize);
		mBound = new Rect();
		mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
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
			width = getPaddingLeft() + mBound.width() + getPaddingRight();
		}

		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			height = getPaddingTop() + mBound.height() + getPaddingBottom();
		}

		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint.setColor(Color.YELLOW);
		canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

		mPaint.setColor(mTitleTextColor);
		canvas.drawText(mTitleText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
	}
}
