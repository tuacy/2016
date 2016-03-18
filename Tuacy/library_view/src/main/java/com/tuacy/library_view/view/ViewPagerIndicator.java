package com.tuacy.library_view.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tuacy.common.log.CustomLog;
import com.tuacy.common.utils.ScreenUtils;

import java.util.List;

public class ViewPagerIndicator extends LinearLayout {

	/**
	 * 标题正常时的颜色
	 */
	private static final int COLOR_TEXT_NORMAL = 0x77FFFFFF;
	/**
	 * 标题选中时的颜色
	 */
	private static final int COLOR_TEXT_SELECT = 0xFFFFFFFF;

	private int mVisibleCount = 4;
	private float        mEachTabWidth;
	private List<String> mTabLists;

	private ViewPager mViewPager;

	private Paint mPaint;
	private Path  mPath;
	private int   mTriangleWidth;
	private int   mTriangleHeight;
	private int   mInitTranslationX;
	private float mTranslationX;
	private static final float RADIO_TRIANGEL = 1.0f / 6;

	private ViewConfiguration mViewConfiguration;

	public ViewPagerIndicator(Context context) {
		this(context, null);
	}

	public ViewPagerIndicator(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		mEachTabWidth = ScreenUtils.getScreenWidth(getContext()) / mVisibleCount;

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.parseColor("#ffffffff"));
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setPathEffect(new CornerPathEffect(3));

		mViewConfiguration = ViewConfiguration.get(context);
	}

	public void setTabDatas(List<String> datas) {
		if (datas != null && datas.size() > 0) {
			mTabLists = datas;

			for (String title : mTabLists) {
				addView(getTextView(title));
			}
		}
	}

	private TextView getTextView(String title) {
		TextView tabView = new TextView(getContext());
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
																	 ViewGroup.LayoutParams.MATCH_PARENT);
		lp.width = (int) mEachTabWidth;
		tabView.setLayoutParams(lp);
		tabView.setText(title);
		tabView.setGravity(Gravity.CENTER);
		tabView.setTextColor(COLOR_TEXT_NORMAL);
		tabView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

		return tabView;
	}

	public void setViewPager(ViewPager viewPager, int position) {
		mViewPager = viewPager;
		mViewPager.setCurrentItem(position);
		mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				CustomLog.tuacy("position = " + position + " positionOffset = " + positionOffset);
				scroll(position, positionOffset);
			}

			@Override
			public void onPageSelected(int position) {
				setTabSelect(position);
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
		setTabSelect(position);
	}

	private void setTabSelect(int position) {
		for (int index = 0; index < getChildCount(); index++) {
			View view = getChildAt(index);
			if (view instanceof TextView) {
				((TextView) view).setTextColor(COLOR_TEXT_NORMAL);
			}
		}
		View view = getChildAt(position);
		if (view instanceof TextView) {
			((TextView) view).setTextColor(COLOR_TEXT_SELECT);
		}
		setViewClick();
	}

	private void scroll(int position, float positionOffset) {

		mTranslationX = mEachTabWidth * position + mEachTabWidth * positionOffset;
		CustomLog.tuacy("mTranslationX = " + mTranslationX);

		int tabCount = getChildCount();
		if (tabCount <= mVisibleCount) {
			return;
		}
		if (positionOffset > 0 && tabCount > mVisibleCount && position >= mVisibleCount - 2 && position < tabCount - 2) {
			int xTo = (int) ((position - (mVisibleCount - 2)) * mEachTabWidth + positionOffset * mEachTabWidth);
			scrollTo(xTo, 0);
		}
		invalidate();

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mTriangleWidth = (int) (w / mVisibleCount * RADIO_TRIANGEL);// 1/6 of
		mInitTranslationX = getWidth() / mVisibleCount / 2 - mTriangleWidth / 2;
		initTriangle();
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		canvas.save();
		// 画笔平移到正确的位置
		canvas.translate(mInitTranslationX + mTranslationX, getHeight() + 1);
		canvas.drawPath(mPath, mPaint);
		canvas.restore();
	}

	private void initTriangle() {
		mPath = new Path();

		mTriangleHeight = (int) (mTriangleWidth / 2 / Math.sqrt(2));
		mPath.moveTo(0, 0);
		mPath.lineTo(mTriangleWidth, 0);
		mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
		mPath.close();
	}

	private float mLastX;
	private float mLastY;

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		float x = ev.getX();
		float y = ev.getY();
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mLastX = x;
				mLastY = y;
//				return true;
				break;
			case MotionEvent.ACTION_MOVE:
				int offset = (int) (x - mLastX);
				if (getChildCount() > mVisibleCount) {
					if (Math.abs(x - mLastX) >= mViewConfiguration.getScaledTouchSlop()) {
						int newScrollX = getScrollX() - offset;
						if (newScrollX < 0 || newScrollX > (getChildCount() - mVisibleCount) * mEachTabWidth) {
							return true;
						}
						scrollBy(-offset, 0);
						mLastX = x;
						mLastY = y;
						return true;
					}
				}

				break;
			case MotionEvent.ACTION_UP:
				break;
		}
		return super.dispatchTouchEvent(ev);
	}

	private void setViewClick() {
		int count = getChildCount();
		for (int index = 0; index < count; index ++ ) {
			final int position = index;
			View view = getChildAt(index);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mViewPager.setCurrentItem(position);
				}
			});
		}
	}
}
