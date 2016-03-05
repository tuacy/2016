package com.tuacy.xml.customerview.view;


import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

public class PullScrollView extends ScrollView {

	//头部图片View
	private View mHeaderView;
	//头部图片的初始化位置
	private Rect mHeadInitRect = new Rect();

	//contentView View
	private View mContentView;
	//contentView的初始化位置
	private Rect mContentInitRect = new Rect();

	//初始点击位置
	private Point mTouchPoint = new Point();


	//标识当前view是否移动
	boolean mIsMoving = false;

	//是否禁止控件本身的的移动
	boolean mEnableMoving = false;

	//是否使用layout函数移动布局
	boolean mIsLayout = false;
	/**
	 * 阻尼系数,越小阻力就越大.
	 */
	private static final float SCROLL_RATIO = 0.5f;

	private int mContentTop, mContentBottom;

	private int mHeaderCurTop, mHeaderCurBottom;

	public PullScrollView(Context context) {
		this(context, null);
	}

	public PullScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PullScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		setOverScrollMode(OVER_SCROLL_NEVER);
	}

	@Override
	protected void onFinishInflate() {
		if (getChildCount() > 0) {
			mContentView = getChildAt(0);
		}
		super.onFinishInflate();
	}

	public void setHeaderView(View view){
		mHeaderView = view;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_MOVE:
				int deltaY =(int)event.getY() - mTouchPoint.y;
				deltaY = deltaY > mHeaderView.getHeight() ? mHeaderView.getHeight() : deltaY;
				if (deltaY > 0 && deltaY >= getScrollY() && mIsLayout) {
					float headerMoveHeight = deltaY * 0.5f * SCROLL_RATIO;
					mHeaderCurTop = (int) (mHeadInitRect.top + headerMoveHeight);
					mHeaderCurBottom = (int) (mHeadInitRect.bottom + headerMoveHeight);

					float contentMoveHeight = deltaY * SCROLL_RATIO;
					mContentTop = (int) (mContentInitRect.top + contentMoveHeight);
					mContentBottom = (int) (mContentInitRect.bottom + contentMoveHeight);

					if (mContentTop <= mHeaderCurBottom) {
						mHeaderView.layout(mHeadInitRect.left, mHeaderCurTop, mHeadInitRect.right, mHeaderCurBottom);
						mContentView.layout(mContentInitRect.left, mContentTop, mContentInitRect.right, mContentBottom);
						mIsMoving = true;
						mEnableMoving = true;
					}
				}
				break;
			case MotionEvent.ACTION_UP:
				//反弹
				if (mIsMoving) {
					mHeaderView.layout(mHeadInitRect.left, mHeadInitRect.top, mHeadInitRect.right, mHeadInitRect.bottom);
					TranslateAnimation headAnim = new TranslateAnimation(0, 0, mHeaderCurTop - mHeadInitRect.top, 0);
					headAnim.setDuration(200);
					mHeaderView.startAnimation(headAnim);

					mContentView.layout(mContentInitRect.left, mContentInitRect.top, mContentInitRect.right, mContentInitRect.bottom);
					TranslateAnimation contentAnim = new TranslateAnimation(0, 0, mContentTop - mContentInitRect.top, 0);
					contentAnim.setDuration(200);
					mContentView.startAnimation(contentAnim);
					mIsMoving = false;
				}
				mEnableMoving = false;
				mIsLayout = false;
				break;
		}
		return mEnableMoving || super.onTouchEvent(event);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			//保存原始位置
			mTouchPoint.set((int) event.getX(), (int) event.getY());
			mHeadInitRect.set(mHeaderView.getLeft(), mHeaderView.getTop(), mHeaderView.getRight(), mHeaderView.getBottom());
			mContentInitRect.set(mContentView.getLeft(), mContentView.getTop(), mContentView.getRight(), mContentView.getBottom());
			mIsMoving = false;
			if (getScrollY() == 0){
				mIsLayout = true;
			}
		} else if(event.getAction() == MotionEvent.ACTION_MOVE){
			//如果当前的事件是我们要处理的事件时，比如现在的下拉，这时候，我们就不能让子控件来处理这个事件
			//这里就需要把它截获，不传给子控件，更不能让子控件消费这个事件
			//不然子控件的行为就可能与我们的相冲突
			int deltaY = (int) event.getY() - mTouchPoint.y;
			deltaY = deltaY > mHeaderView.getHeight() ? mHeaderView.getHeight() : deltaY;
			if (deltaY > 0 && deltaY >= getScrollY()) {
				onTouchEvent(event);
				return true;
			}
		}
		return super.onInterceptTouchEvent(event);
	}
}
