package com.tuacy.xml.canvas.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;

import com.tuacy.xml.R;

public class BitmapShaderView extends View {

	private BitmapShader  bitmapShader  = null;
	private Bitmap        bitmap        = null;
	private Paint         paint         = null;
	private ShapeDrawable shapeDrawable = null;
	private int           BitmapWidth   = 0;
	private int           BitmapHeight  = 0;

	public BitmapShaderView(Context context) {
		this(context, null);
	}

	public BitmapShaderView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BitmapShaderView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		// 得到图像
		bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.test_only)).getBitmap();
		BitmapWidth = bitmap.getWidth();
		BitmapHeight = bitmap.getHeight();
		// 构造渲染器BitmapShader
		bitmapShader = new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.REPEAT);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		//将图片裁剪为椭圆形
		//构建ShapeDrawable对象并定义形状为椭圆
		shapeDrawable = new ShapeDrawable(new OvalShape());
		//得到画笔并设置渲染器
		shapeDrawable.getPaint().setShader(bitmapShader);
		//设置显示区域
		shapeDrawable.setBounds(20, 20, BitmapWidth - 140, BitmapHeight);
		//绘制shapeDrawable
		shapeDrawable.draw(canvas);
	}
}
