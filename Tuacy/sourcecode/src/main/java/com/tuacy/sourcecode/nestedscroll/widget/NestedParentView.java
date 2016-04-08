package com.tuacy.sourcecode.nestedscroll.widget;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Arrays;

public class NestedParentView extends FrameLayout implements NestedScrollingParent {

	private NestedScrollingParentHelper mScrollingParentHelper;

	public NestedParentView(Context context) {
		this(context, null);
	}

	public NestedParentView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public NestedParentView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		mScrollingParentHelper = new NestedScrollingParentHelper(this);
	}

	/*
	子类开始请求滑动
     */
	@Override
	public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
//		Log.d("tuacy", "onStartNestedScroll() called with: " + "child = [" + child + "], target = [" + target + "], nestedScrollAxes = [" +
//					   nestedScrollAxes + "]");
		return true;
	}


	@Override
	public void onNestedScrollAccepted(View child, View target, int axes) {
		mScrollingParentHelper.onNestedScrollAccepted(child, target, axes);
	}


	@Override
	public int getNestedScrollAxes() {
		return mScrollingParentHelper.getNestedScrollAxes();
	}

	@Override
	public void onStopNestedScroll(View child) {
		mScrollingParentHelper.onStopNestedScroll(child);
	}

	@Override
	public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
//		Log.d("tuacy",
//			  "onNestedPreScroll() called with: " + "dx = [" + dx + "], dy = [" + dy + "], consumed = [" + Arrays.toString(consumed) + "]");
		final View child = target;
		if (dx > 0) {
			if (child.getRight() + dx > getWidth()) {
				dx = child.getRight() + dx - getWidth();//多出来的
				offsetLeftAndRight(dx);
				consumed[0] += dx;//父亲消耗
			}
		} else {
			if (child.getLeft() + dx < 0) {
				dx = dx + child.getLeft();
				offsetLeftAndRight(dx);
//				Log.d("tuacy", "dx:" + dx);
				consumed[0] += dx;//父亲消耗
			}
		}

		if (dy > 0) {
			if (child.getBottom() + dy > getHeight()) {
				dy = child.getBottom() + dy - getHeight();
				offsetTopAndBottom(dy);
				consumed[1] += dy;
			}
		} else {
			if (child.getTop() + dy < 0) {
				dy = dy + child.getTop();
				offsetTopAndBottom(dy);
//				Log.d("tuacy", "dy:" + dy);
				consumed[1] += dy;//父亲消耗
			}
		}
	}
}
