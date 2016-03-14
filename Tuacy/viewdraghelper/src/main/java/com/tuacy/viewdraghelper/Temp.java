package com.tuacy.viewdraghelper;

import android.view.View;

public class Temp {
	/**
	 * A Callback is used as a communication channel with the ViewDragHelper back to the
	 * parent view using it. <code>on*</code>methods are invoked on siginficant events and several
	 * accessor methods are expected to provide the ViewDragHelper with more information
	 * about the state of the parent view upon request. The callback also makes decisions
	 * governing the range and draggability of child views.
	 */
	public static abstract class Callback {

		/**
		 * 当拖拽状态发生改变的时候调用
		 * state:总共有三种状态STATE_IDLE(view没有被拖拽且不再动画状态)， STATE_DRAGGING(view正在被拖拽)，
		 * STATE_SETTLING(view正在往预定的位置上放)
		 */
		public void onViewDragStateChanged(int state) {}

		/**
		 * 当拖拽的View的位置发生改变的时候调用，
		 */
		public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {}

		/**
		 * 捕获capture view的时候回调
		 */
		public void onViewCaptured(View capturedChild, int activePointerId) {}

		/**
		 * 当拖拽的View手指释放的时候回调
		 */
		public void onViewReleased(View releasedChild, float xvel, float yvel) {}

		/**
		 * 当触摸屏幕边界的时候回调
		 */
		public void onEdgeTouched(int edgeFlags, int pointerId) {}

		/**
		 * 是否锁住边界
		 */
		public boolean onEdgeLock(int edgeFlags) {
			return false;
		}

		/**
		 * 在边缘滑动的时候可以设置滑动另一个子View跟着滑动
		 */
		public void onEdgeDragStarted(int edgeFlags, int pointerId) {}

		/**
		 * 改变同一个坐标（x,y）去寻找captureView位置的方法。
		 */
		public int getOrderedChildIndex(int index) {
			return index;
		}

		/**
		 * 返回一个大于0的数，然后才会在水平方向移动
		 */
		public int getViewHorizontalDragRange(View child) {
			return 0;
		}

		/**
		 * 返回一个大于0的数，然后才会在垂直方向移动
		 */
		public int getViewVerticalDragRange(View child) {
			return 0;
		}

		/**
		 * 传递当前触摸的子View实例，如果当前的子View需要进行拖拽移动返回true，否则返回false
		 */
		public abstract boolean tryCaptureView(View child, int pointerId);

		/**
		 * 决定拖拽的View在水平方向上面移动到的位置
		 */
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			return 0;
		}

		/**
		 * 决定拖拽的View在垂直方向上面移动到的位置
		 */
		public int clampViewPositionVertical(View child, int top, int dy) {
			return 0;
		}
	}
}
