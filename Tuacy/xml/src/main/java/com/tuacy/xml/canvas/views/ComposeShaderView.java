package com.tuacy.xml.canvas.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.tuacy.xml.R;

public class ComposeShaderView extends View {

	private Bitmap        mBitmap         = null;
	private int           bitwidth        = 0;
	private int           bitheight       = 0;
	private Paint         mPaint          = null;
	// bitmap渲染
	private Shader        mBitmapShader   = null;
	// 线性渐变渲染
	private Shader        mLinearGradient = null;
	// 混合渲染
	private Shader        mComposeShader  = null;
	private ShapeDrawable mShapeDrawable  = null;

	public ComposeShaderView(Context context) {
		this(context, null);
	}

	public ComposeShaderView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ComposeShaderView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		// 装载资源
		mBitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.test_only)).getBitmap();
		// 得到宽高
		bitwidth = mBitmap.getWidth();
		bitheight = mBitmap.getHeight();
		// 创建BitmapShader对象
		mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
		// 创建LinearGradient并设置渐变颜色数组,平铺效果为镜像
		mLinearGradient = new LinearGradient(0, 0, 0, 100, new int[]{Color.WHITE,
																	 Color.LTGRAY,
																	 Color.TRANSPARENT,
																	 Color.GREEN}, null, Shader.TileMode.MIRROR);

		// 混合渲染 将两个效果叠加,使用PorterDuff叠加模式
		mComposeShader = new ComposeShader(mBitmapShader, mLinearGradient, PorterDuff.Mode.MULTIPLY);
		mPaint = new Paint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// 绘制混合渲染效果
		mPaint.setShader(mComposeShader);
		canvas.drawCircle(240, 360, 200, mPaint);

	}
}
