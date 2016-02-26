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
	private boolean   mIsMenuOpen;
	private float     mScaler;
	private int       mLastXIntercept;
	private int       mLastYIntercept;

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
		mIsMenuOpen = false;
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
			mScaler = Math.abs((float) getScrollX()) / (float) mMenuWidth; // 0 ~ 1
			//			slideMode1();
			slideMode2();
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
				scrolling(currentX - mLastX);
				mScaler = Math.abs((float) getScrollX()) / (float) mMenuWidth; // 0 ~ 1
				//				slideMode1();
				slideMode2();
				mLastX = currentX;
				mLastY = currentY;
				break;
		}
		return true;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		boolean intercept = false;
		int x = (int) ev.getX();
		int y = (int) ev.getY();
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				intercept = false;
				break;
			case MotionEvent.ACTION_MOVE:
				int deltaX = (int) ev.getX() - mLastXIntercept;
				int deltaY = (int) ev.getY() - mLastYIntercept;
				if (Math.abs(deltaX) > Math.abs(deltaY)) {
					intercept = true;
				} else {
					intercept = false;
				}
				break;
			case MotionEvent.ACTION_UP:
				intercept = false;
				break;
		}
		mLastX = x;
		mLastY = y;
		mLastXIntercept = x;
		mLastYIntercept = y;
		return intercept;
	}

	private void closeMenu() {
		mIsMenuOpen = false;
		mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0, 500);
		invalidate();
	}

	private void openMenu() {
		mIsMenuOpen = true;
		mIsMenuOpen = false;
		mScroller.startScroll(getScrollX(), 0, mMenuWidth - getScrollX(), 0, 500);
		invalidate();
	}

	public void toggleMenu() {
		if (mIsMenuOpen) {
			closeMenu();
		} else {
			openMenu();
		}
	}

	private void scrolling(int dx) {
		if (dx < 0) {
			if (getScrollX() + Math.abs(dx) > 0) {
				scrollTo(0, 0);
			} else {
				scrollBy(-dx, 0);
			}
		} else {
			if (getScrollX() - dx <= -mMenuWidth) {
				scrollTo(-mMenuWidth, 0);
			} else {
				scrollBy(-dx, 0);
			}
		}
	}

	private void slideMode1() {
		mMenuView.setTranslationX(2 * (mMenuWidth + getScrollX()) / 3);
	}

	private void slideMode2() {
		mMenuView.setTranslationX(mMenuWidth + getScrollX() - (mMenuWidth / 2) * (1.0f - mScaler));
		mMenuView.setScaleX(0.7f + 0.3f * mScaler);
		mMenuView.setScaleY(0.7f + 0.3f * mScaler);
		mMenuView.setAlpha(mScaler);

		mContentView.setScaleX(1 - 0.3f * mScaler);
		mContentView.setPivotX(0);
		mContentView.setScaleY(1.0f - 0.3f * mScaler);
	}
}
