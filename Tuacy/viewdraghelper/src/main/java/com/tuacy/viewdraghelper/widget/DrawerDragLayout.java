package com.tuacy.viewdraghelper.widget;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.nineoldandroids.view.ViewHelper;
import com.tuacy.common.log.CustomLog;
import com.tuacy.common.utils.DensityUtils;
import com.tuacy.common.utils.ScreenUtils;


public class DrawerDragLayout extends ViewGroup {

	private static final int DEFAULT_DRAWER_PADDING_LEFT = 100;

	private ViewGroup mDrawerView;
	private int       mDrawerWidth;
	private int       mDrawerHeight;
	private int       mPaddingLeftPx;

	private ViewGroup mContentView;
	private int       mContentViewWidth;
	private int       mContentViewHeight;

	private int mScreenWidth;
	private int mScreenHeight;

	private float mMovePercent;
	private boolean mIsDrawerOpen = false;

	private ViewDragHelper mDragHelper;

	public DrawerDragLayout(Context context) {
		this(context, null);
	}

	public DrawerDragLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		// 获取到两个子View
		mContentView = (ViewGroup) getChildAt(0);
		mDrawerView = (ViewGroup) getChildAt(1);
	}

	public DrawerDragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		mScreenWidth = ScreenUtils.getScreenWidth(context);
		mScreenHeight = ScreenUtils.getScreenHeight(context);
		mPaddingLeftPx = DensityUtils.dp2px(context, DEFAULT_DRAWER_PADDING_LEFT);

		mDrawerWidth = mScreenWidth - mPaddingLeftPx;
		mDrawerHeight = mScreenHeight;

		mContentViewWidth = mScreenWidth;
		mContentViewHeight = mScreenHeight;

		mDragHelper = ViewDragHelper.create(this, 1.0f, mCallback);
		// 左边沿可以drag
		mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
	}

	private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {

		@Override
		public boolean tryCaptureView(View child, int pointerId) {
			CustomLog.tuacy("tryCaptureView");
			return child == mDrawerView;
		}

		@Override
		public void onEdgeDragStarted(int edgeFlags, int pointerId) {
			CustomLog.tuacy("onEdgeDragStarted");
			mDragHelper.captureChildView(mDrawerView, pointerId);
		}

		@Override
		public int getViewHorizontalDragRange(View child) {
			return mDrawerWidth;
		}

		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			if (left > 0) {
				left = 0;
			}
			return left;
		}

		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			if (mMovePercent < 0.5) {
				mIsDrawerOpen = false;
			} else {
				mIsDrawerOpen = true;
			}
			int finalLeft = mMovePercent < 0.5 ? -mDrawerWidth : 0;
			mDragHelper.settleCapturedViewAt(finalLeft, 0);
			invalidate();
		}

		@Override
		public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
			//计算出mTopView变化时占总变化宽度的的实时百分比
			mMovePercent = (mDrawerWidth + left) * 1.0f / mDrawerWidth;
			float drawerViewScale = 0.2f * mMovePercent + 0.8f;
			ViewHelper.setScaleX(mDrawerView, drawerViewScale);   //mTopView.setScaleX();
			ViewHelper.setScaleY(mDrawerView, drawerViewScale);   //mTopView.setScaleY();

			float contentViewScale = 1 - 0.2f * mMovePercent;
			ViewHelper.setScaleY(mContentView, contentViewScale);   //mBottomView.setScaleX();
			ViewHelper.setScaleX(mContentView, contentViewScale);   //mBottomView.setScaleY();
			ViewHelper.setAlpha(mContentView, contentViewScale);
			// translate
			float contentTranslate = mDrawerWidth * mMovePercent;
			ViewHelper.setTranslationX(mContentView, contentTranslate);
			invalidate();
		}
	};

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
		int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
		setMeasuredDimension(measureWidth, measureHeight);

		// 测量子View
		mDrawerView.measure(MeasureSpec.makeMeasureSpec(mDrawerWidth, MeasureSpec.EXACTLY),
							MeasureSpec.makeMeasureSpec(mDrawerHeight, MeasureSpec.EXACTLY));

		mContentView.measure(MeasureSpec.makeMeasureSpec(mContentViewWidth, MeasureSpec.EXACTLY),
							 MeasureSpec.makeMeasureSpec(mContentViewHeight, MeasureSpec.EXACTLY));

		// 测量自己
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (changed) {
			// drawer view 初始的时候完全在左边
			mDrawerView.layout(-mDrawerWidth, 0, 0, mDrawerHeight);
			mContentView.layout(0, 0, mContentViewWidth, mContentViewHeight);
		}
	}

	@Override
	public void computeScroll() {
		if (mDragHelper.continueSettling(true)) {
			invalidate();
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return mDragHelper.shouldInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mDragHelper.processTouchEvent(event);
		return true;
	}

	private void openDrawer() {
		smoothSlide(0);
		mIsDrawerOpen = true;
	}

	private void closeDrawer() {
		smoothSlide(-mDrawerWidth);
		mIsDrawerOpen = false;
	}

	public void switchDrawer() {
		if (mIsDrawerOpen) {
			closeDrawer();
		} else {
			openDrawer();
		}
	}

	private void smoothSlide(int left) {
		if (mDragHelper.getViewDragState() != ViewDragHelper.STATE_IDLE) {
			mDragHelper.abort();
		}
		mDragHelper.smoothSlideViewTo(mDrawerView, left, 0);
		invalidate();
	}

}
