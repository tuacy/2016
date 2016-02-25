package com.tuacy.slide.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.tuacy.common.utils.DensityUtils;
import com.tuacy.common.utils.ScreenUtils;

public class SlideMenuView extends ViewGroup {

	private final int DEFAULT_MENU_PADDING = 100;

	private int       mScreenWidth;
	private int       mScreenHeight;
	private int       mMenuRightPadding;
	private int       mMenuWidth;
	private int       mContentWidth;
	private ViewGroup mMenuView;
	private ViewGroup mContentView;
	private int       mLastX;
	private int       mLastY;
	private Scroller  mScroller;

	public SlideMenuView(Context context) {
		this(context, null);
	}

	public SlideMenuView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SlideMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		mScreenWidth = ScreenUtils.getScreenWidth(context);
		mScreenHeight = ScreenUtils.getScreenHeight(context);
		mMenuRightPadding = (int) DensityUtils.int2dp(context, DEFAULT_MENU_PADDING);
		mScroller = new Scroller(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		mMenuView = (ViewGroup) getChildAt(0);
		mContentView = (ViewGroup) getChildAt(1);
		mMenuWidth = mMenuView.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
		mContentWidth = mContentView.getLayoutParams().width = mScreenWidth;
		measureChild(mMenuView, widthMeasureSpec, heightMeasureSpec);
		measureChild(mContentView, widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(mMenuWidth + mContentWidth, mScreenHeight);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (changed) {
			mMenuView.layout(-mMenuWidth, 0, 0, mScreenHeight);
			mContentView.layout(0, 0, mScreenWidth, mScreenHeight);
		}
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			invalidate();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				mLastX = (int) event.getX();
				mLastY = (int) event.getY();
				break;
			case MotionEvent.ACTION_UP:
				if (getScrollX() < -mMenuWidth / 2) {
					mScroller.startScroll(getScrollX(), 0, -mMenuWidth - getScrollX(), 0, 300);
					invalidate();
				} else {
					mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0, 300);
					invalidate();
				}
				break;
			case MotionEvent.ACTION_MOVE:
				int currentX = (int) event.getX();
				int currentY = (int) event.getY();
				int dX = currentX - mLastX;
				if (dX < 0) {
					if (getScrollX() + Math.abs(dX) > 0) {
						scrollTo(0, 0);
					} else {
						scrollBy(-dX, 0);
					}
				} else {
					if (getScrollX() - dX <= -mMenuWidth) {
						scrollTo(-mMenuWidth, 0);
					} else {
						scrollBy(-dX, 0);
					}
				}
				mLastX = currentX;
				mLastY = currentY;
				break;
		}
		return true;
	}
}
