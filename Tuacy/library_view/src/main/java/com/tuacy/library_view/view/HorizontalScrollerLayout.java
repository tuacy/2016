package com.tuacy.library_view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class HorizontalScrollerLayout extends LinearLayout {


	private Scroller          mScroller;
	private ViewConfiguration mViewConfiguration;
	private View              mContentView;
	private View              mScrollerView;

	public HorizontalScrollerLayout(Context context) {
		this(context, null);
	}

	public HorizontalScrollerLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public HorizontalScrollerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		mScroller = new Scroller(context);
		mViewConfiguration = ViewConfiguration.get(context);
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
				super.dispatchTouchEvent(ev);
				return true;
			case MotionEvent.ACTION_MOVE:

				float offsetX = x - mLastX;
				float offsetY = y - mLastY;
				if (Math.abs(offsetX) >= mViewConfiguration.getScaledTouchSlop()) {
					int offset = (int) -offsetX;
					if (getScrollX() + offset > mScrollerView.getWidth() || getScrollX() + offset < 0) {
						return true;
					}
					scrollBy(offset, 0);
					mLastX = x;
					mLastY = y;
					return true;
				}
				break;
			case MotionEvent.ACTION_UP:
				int offset;
				if (getScrollX() > mScrollerView.getWidth() / 2) {
					offset = mScrollerView.getWidth();
				} else {
					offset = 0;
				}
				mScroller.startScroll(getScrollX(), getScrollY(), offset - getScrollX(), 0);
				invalidate();
				break;
		}

		return super.dispatchTouchEvent(ev);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mContentView = getChildAt(0);
		mScrollerView = getChildAt(1);
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}
	}
}
