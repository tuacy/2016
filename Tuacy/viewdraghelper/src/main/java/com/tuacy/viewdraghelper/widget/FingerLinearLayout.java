package com.tuacy.viewdraghelper.widget;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class FingerLinearLayout extends LinearLayout {

	private ViewDragHelper mDragHelper;

	public FingerLinearLayout(Context context) {
		this(context, null);
	}

	public FingerLinearLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FingerLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		mDragHelper = ViewDragHelper.create(this, 1.0f, mDragHelperCallback);
		mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
	}

	private ViewDragHelper.Callback mDragHelperCallback = new ViewDragHelper.Callback() {
		@Override
		public boolean tryCaptureView(View child, int pointerId) {
			return true;
		}

		@Override
		public void onEdgeDragStarted(int edgeFlags, int pointerId) {
		}

		@Override
		public void onViewCaptured(View capturedChild, int activePointerId) {
		}

		@Override
		public int getViewVerticalDragRange(View child) {
			return 0;
		}

		@Override
		public int getViewHorizontalDragRange(View child) {
			return 0;
		}

		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			return left;
		}

		@Override
		public int clampViewPositionVertical(View child, int top, int dy) {
			return top;
		}

		@Override
		public void onEdgeTouched(int edgeFlags, int pointerId) {
		}
	};

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mDragHelper.processTouchEvent(event);
		return true;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return mDragHelper.shouldInterceptTouchEvent(ev);
	}

	@Override
	public void computeScroll() {
		if (mDragHelper.continueSettling(true)) {
			invalidate();
		}
	}
}
