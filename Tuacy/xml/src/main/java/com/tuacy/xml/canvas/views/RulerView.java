package com.tuacy.xml.canvas.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class RulerView extends View {

	// 刻度尺高度
	private static final int DIVIDING_RULE_HEIGHT            = 70;
	// 距离左右间
	private static final int DIVIDING_RULE_MARGIN_LEFT_RIGHT = 10;
	// 第一条线距离边框距离
	private static final int FIRST_LINE_MARGIN               = 5;
	// 打算绘制的厘米数
	private static final int DEFAULT_COUNT                   = 9;

	private Paint mRectPaint, mLinePaint;
	private int  mRuleLineWidth;
	private int  mHalfRuleLineWidth;
	private int  mCalibrationLineWidth;
	private int  mRuleHeight;
	private int  mRuleLeftMargin;
	private int  mLineInterval;
	private int  mLineStartX;
	private int  mMaxLineTop;
	private int  mMiddleLineTop;
	private int  mMinLineTop;
	private int  mRuleBottom;
	private Rect mOutRect;


	public RulerView(Context context) {
		this(context, null);
	}

	public RulerView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RulerView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		mRuleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DIVIDING_RULE_HEIGHT,
													  getResources().getDisplayMetrics());
		mCalibrationLineWidth = mRuleLineWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2,
																				 getResources().getDisplayMetrics());
		mHalfRuleLineWidth = mRuleLineWidth / 2;
		mRuleLeftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DIVIDING_RULE_MARGIN_LEFT_RIGHT,
														  getResources().getDisplayMetrics());
		mOutRect = new Rect();
		initPaint();
	}

	private void initPaint() {
		mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mRectPaint.setColor(Color.RED);
		// 将画笔设置为空心
		mRectPaint.setStyle(Paint.Style.STROKE);
		// 设置画笔宽度
		mRectPaint.setStrokeWidth(mRuleLineWidth);
		mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mLinePaint.setColor(Color.RED);
		mLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
		// 设置画笔宽度
		mLinePaint.setStrokeWidth(mCalibrationLineWidth);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 绘制外框
		drawOuter(canvas);
		// 绘制刻度线
		drawLines(canvas);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mLineInterval = (w - 2 * mRuleLeftMargin - 2 * mRuleLineWidth) / (DEFAULT_COUNT * 10 - 1);
		mOutRect.set(mRuleLeftMargin + mHalfRuleLineWidth, h / 2 - mRuleHeight / 2 + mHalfRuleLineWidth,
					 w - mRuleLeftMargin - mHalfRuleLineWidth, h / 2 + mRuleHeight / 2 - mHalfRuleLineWidth);
		mLineStartX = mRuleLeftMargin + mHalfRuleLineWidth;
		mRuleBottom = h / 2 + mRuleHeight / 2 - mHalfRuleLineWidth;
		mMaxLineTop = mRuleBottom - 40;
		mMiddleLineTop = mRuleBottom - 25;
		mMinLineTop = mRuleBottom - 15;
	}

	private void drawLines(Canvas canvas) {
		canvas.save();
		canvas.translate(mLineStartX, 0);
		int top;
		for (int i = 0; i <= DEFAULT_COUNT * 10; i++) {
			if (i % 10 == 0) {
				top = mMaxLineTop;
			} else if (i % 5 == 0) {
				top = mMiddleLineTop;
			} else {
				top = mMinLineTop;
			}

			canvas.drawLine(0, mRuleBottom, 0, top, mLinePaint);
			canvas.translate(mLineInterval, 0);

		}
		canvas.restore();
	}

	private void drawOuter(Canvas canvas) {
		canvas.drawRect(mOutRect, mRectPaint);
	}
}
