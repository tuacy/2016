package com.tuacy.xml.canvas.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.util.AttributeSet;
import android.view.View;

public class CanvasRegionView extends View {

	private Paint  mPaint;
	private Paint  mRegionPaint;
	private Region mRegion;
	private RectF  mRectF;
	private Path   mOvalPath;

	private Rect   mRect1;// = new Rect(100,100,400,200);
	private Rect   mRect2;// = new Rect(200,0,300,300);
	private Region mRegion1;
	private Region mRegion2;

	public CanvasRegionView(Context context) {
		this(context, null);
	}

	public CanvasRegionView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CanvasRegionView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		mPaint = new Paint();
		mPaint.setColor(Color.RED);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(2);
		mRegion = new Region();
		mOvalPath = new Path();
		mRectF = new RectF();
		mRect1 = new Rect();
		mRect2 = new Rect();

		mRegion1 = new Region();
		mRegion2 = new Region();
		mRegionPaint = new Paint();
		mRegionPaint.setColor(Color.GREEN);
		mRegionPaint.setStyle(Paint.Style.FILL);
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
			width = getPaddingLeft() + 420 + getPaddingRight();
		}

		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			height = getPaddingTop() + 320 + getPaddingBottom();
		}
		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 测试set
		//		mRegion.set(10, 10, 100, 100);
		//		drawRegion(canvas, mRegion, mPaint);

		// 测试setPath
		//		mRectF.set(50, 50, 200, 500);
		//		mRegion.set(50, 50, 200, 200);
		//		mOvalPath.addOval(mRectF, Path.Direction.CCW);
		//		mRegion.setPath(mOvalPath, mRegion);
		//		drawRegion(canvas, mRegion, mPaint);

		// 交集
		mRect1.set(100, 100, 400, 200);
		mRect2.set(200, 10, 300, 300);
		canvas.drawRect(mRect1, mPaint);
		canvas.drawRect(mRect2, mPaint);

		//构造两个Region
		mRegion1.set(mRect1);
		mRegion2.set(mRect2);

		//取两个区域的交集
		mRegion1.op(mRegion2, Region.Op.INTERSECT);
		drawRegion(canvas, mRegion1, mRegionPaint);

	}

	private void drawRegion(Canvas canvas, Region rgn, Paint paint) {
		RegionIterator iter = new RegionIterator(rgn);
		Rect r = new Rect();
		while (iter.next(r)) {
			canvas.drawRect(r, paint);
		}
	}
}
