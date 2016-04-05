package com.tuacy.sourcecode.behavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.tuacy.sourcecode.R;

public class FollowBehavior extends CoordinatorLayout.Behavior {

	private int targetId;

	public FollowBehavior(Context context, AttributeSet attrs) {
		super(context, attrs);
		//		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Follow);
		//		for (int i = 0; i < a.getIndexCount(); i++) {
		//			int attr = a.getIndex(i);
		//			if (a.getIndex(i) == R.styleable.Follow_target) {
		//				targetId = a.getResourceId(attr, -1);
		//			}
		//		}
		//		a.recycle();
	}

	@Override
	public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
		child.setY(dependency.getY() + dependency.getHeight());
		return true;
	}

	@Override
	public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
									   View child,
									   View directTargetChild,
									   View target,
									   int nestedScrollAxes) {
		return true;//这里返回true，才会接受到后续滑动事件。
	}

	@Override
	public void onNestedScroll(CoordinatorLayout coordinatorLayout,
							   View child,
							   View target,
							   int dxConsumed,
							   int dyConsumed,
							   int dxUnconsumed,
							   int dyUnconsumed) {
		//进行滑动事件处理
	}

	@Override
	public boolean onNestedFling(CoordinatorLayout coordinatorLayout,
								 View child,
								 View target,
								 float velocityX,
								 float velocityY,
								 boolean consumed) {
		//当进行快速滑动
		return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
	}

	@Override
	public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
		return dependency.getId() == targetId;
	}

}
