package com.tuacy.xml.animation.interpolator;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

public class BouncePointView extends View {

	private Point mCurrentPoint;
	private Paint mPaint = new Paint();

	public BouncePointView(Context context) {
		super(context);
	}

	public BouncePointView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BouncePointView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (mCurrentPoint != null) {
			mPaint.setAntiAlias(true);
			mPaint.setColor(Color.RED);
			mPaint.setStyle(Paint.Style.FILL);
			canvas.drawCircle(300, 300, mCurrentPoint.getRadius(), mPaint);
		}
	}

	public void doPointAnim(){
		ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(),new Point(20),new Point(200));
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				mCurrentPoint = (Point)animation.getAnimatedValue();
				invalidate();
			}
		});
		animator.setDuration(10000);
		animator.setInterpolator(new BounceInterpolator());
		animator.start();
	}
}
