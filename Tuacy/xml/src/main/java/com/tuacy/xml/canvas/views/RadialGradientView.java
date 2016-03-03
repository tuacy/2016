package com.tuacy.xml.canvas.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class RadialGradientView extends View {

	private Paint  mPaint          = null;
	// 环形渐变渲染
	private Shader mRadialGradient = null;

	public RadialGradientView(Context context) {
		this(context, null);
	}

	public RadialGradientView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RadialGradientView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		//1.圆心X坐标2.Y坐标3.半径 4.颜色数组 5.相对位置数组，可为null 6.渲染器平铺模式
		mRadialGradient = new RadialGradient(240, 240, 240, new int[]{Color.YELLOW,
																	  Color.GREEN,
																	  Color.TRANSPARENT,
																	  Color.RED}, null, Shader.TileMode.REPEAT);

		mPaint = new Paint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// 绘制环形渐变
		mPaint.setShader(mRadialGradient);
		// 第一个,第二个参数表示圆心坐标
		// 第三个参数表示半径
		canvas.drawCircle(240, 360, 200, mPaint);
	}
}
