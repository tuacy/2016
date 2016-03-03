package com.tuacy.xml.canvas.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.tuacy.common.utils.ScreenUtils;

public class CanvasXfermodeView extends View {

	//PorterDuff模式常量 可以在此更改不同的模式测试
	private static final PorterDuff.Mode MODE = PorterDuff.Mode.CLEAR;
	private PorterDuffXfermode porterDuffXfermode;
	private int                screenW, screenH; //屏幕宽高
	private Bitmap srcBitmap, dstBitmap;
	//源图和目标图宽高
	private int width  = 120;
	private int height = 120;

	private Paint mPaint;

	public CanvasXfermodeView(Context context) {
		this(context, null);
	}

	public CanvasXfermodeView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CanvasXfermodeView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		screenW = ScreenUtils.getScreenWidth(context);
		screenH = ScreenUtils.getScreenHeight(context);
		//创建一个PorterDuffXfermode对象
		porterDuffXfermode = new PorterDuffXfermode(MODE);
		//创建原图和目标图
		srcBitmap = makeSrc(width, height);
		dstBitmap = makeDst(width, height);

		mPaint = new Paint();
	}

	//创建一个圆形bitmap，作为dst图
	private Bitmap makeDst(int w, int h) {
		Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(bm);
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setColor(0xFFFFCC44);
		c.drawOval(new RectF(0, 0, w * 3 / 4, h * 3 / 4), p);
		return bm;
	}

	// 创建一个矩形bitmap，作为src图
	private Bitmap makeSrc(int w, int h) {
		Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(bm);
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setColor(0xFF66AAFF);
		c.drawRect(w / 3, h / 3, w * 19 / 20, h * 19 / 20, p);
		return bm;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mPaint.setFilterBitmap(false);
		mPaint.setStyle(Paint.Style.FILL);
		//绘制“src”蓝色矩形原图
		canvas.drawBitmap(srcBitmap, screenW / 8 - width / 4, screenH / 12 - height / 4, mPaint);
		//绘制“dst”黄色圆形原图
		canvas.drawBitmap(dstBitmap, screenW / 2, screenH / 12, mPaint);

		//创建一个图层，在图层上演示图形混合后的效果
		int sc = canvas.saveLayer(0, 0, screenW, screenH, null, Canvas.MATRIX_SAVE_FLAG |
																Canvas.CLIP_SAVE_FLAG |
																Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
																Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
																Canvas.CLIP_TO_LAYER_SAVE_FLAG);

		//先绘制“dst”黄色圆形
		canvas.drawBitmap(dstBitmap, screenW / 4, screenH / 3, mPaint);
		//设置Paint的Xfermode
		mPaint.setXfermode(porterDuffXfermode);
		canvas.drawBitmap(srcBitmap, screenW / 4, screenH / 3, mPaint);
		mPaint.setXfermode(null);
		// 还原画布
		canvas.restoreToCount(sc);
	}
}
