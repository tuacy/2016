package com.tuacy.xml.canvas.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

public class SweepGradientView extends View {

	private Paint  mPaint         = null;
	// 梯度渲染
	private Shader mSweepGradient = null;

	public SweepGradientView(Context context) {
		this(context, null);
	}

	public SweepGradientView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SweepGradientView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		mSweepGradient = new SweepGradient(240, 360, new int[]{Color.CYAN,
															   Color.DKGRAY,
															   Color.GRAY,
															   Color.LTGRAY,
															   Color.MAGENTA,
															   Color.GREEN,
															   Color.TRANSPARENT,
															   Color.BLUE}, null);
		mPaint = new Paint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 绘制梯度渐变
		mPaint.setShader(mSweepGradient);
		canvas.drawCircle(240, 360, 200, mPaint);
	}
}
