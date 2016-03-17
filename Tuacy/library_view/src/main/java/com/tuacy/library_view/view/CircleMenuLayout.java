package com.tuacy.library_view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuacy.common.utils.ScreenUtils;
import com.tuacy.library_view.R;


public class CircleMenuLayout extends ViewGroup {

	/**
	 * 该容器内child item的默认尺寸
	 */
	private static final float RADIO_DEFAULT_CHILD_DIMENSION  = 1 / 4f;
	/**
	 * 菜单的中心child的默认尺寸
	 */
	private static final float RADIO_DEFAULT_CENTER_DIMENSION = 1 / 3f;
	/**
	 * 该容器的内边距,无视padding属性，如需边距请用该变量
	 */
	private static final float RADIO_PADDING_LAYOUT           = 1 / 12f;
	/**
	 * 当每秒移动角度达到该值时，认为是快速移动
	 */
	private static final int   FLINGABLE_VALUE                = 300;

	private float                   mDiameter;
	private float                   mPadding;
	private double                  mStartAngle;
	private int                     mItemIds[];
	private String                  mItemText[];
	private OnMenuItemClickListener mListener;
	private long                    mDownTime;
	private float                   mTmpAngle;
	/**
	 * 判断是否正在自动滚动
	 */
	private boolean                 isFling;

	public CircleMenuLayout(Context context) {
		this(context, null);
	}

	public CircleMenuLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CircleMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		setPadding(0, 0, 0, 0);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 测量自己的大小
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(widthMeasureSpec);
		int resWidth, resHeight;
		if (widthMode != MeasureSpec.EXACTLY || heightMode != MeasureSpec.EXACTLY) {
			resWidth = getSuggestedMinimumWidth();
			resWidth = resWidth == 0 ? getDefaultWidth() : resWidth;

			resHeight = getSuggestedMinimumHeight();
			resHeight = resHeight == 0 ? getDefaultWidth() : resHeight;
		} else {
			resWidth = resHeight = Math.min(widthSize, heightSize);
		}
		setMeasuredDimension(resWidth, resHeight);
		mDiameter = Math.min(getMeasuredWidth(), getMeasuredHeight());

		int childCount = getChildCount();
		int centerSize = (int) (mDiameter * RADIO_DEFAULT_CENTER_DIMENSION);
		int menuSize = (int) (mDiameter * RADIO_DEFAULT_CHILD_DIMENSION);
		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);
			int childMeasureSpec;
			if (child.getId() == R.id.id_circle_menu_item_center) {
				childMeasureSpec = MeasureSpec.makeMeasureSpec(centerSize, MeasureSpec.EXACTLY);
			} else {
				childMeasureSpec = MeasureSpec.makeMeasureSpec(menuSize, MeasureSpec.EXACTLY);
			}
			child.measure(childMeasureSpec, childMeasureSpec);
		}
		mPadding = mDiameter * RADIO_PADDING_LAYOUT;

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childCount = getChildCount();
		float angleDelay = 360f / (childCount - 1);
		int centerSize = (int) (mDiameter * RADIO_DEFAULT_CENTER_DIMENSION);
		int menuSize = (int) (mDiameter * RADIO_DEFAULT_CHILD_DIMENSION);
		float temp = mDiameter / 2 - mPadding - menuSize / 2;
		for (int i = 0; i < childCount; i++) {
			View childView = getChildAt(i);
			int left, top;
			if (childView.getId() == R.id.id_circle_menu_item_center) {
				left = (int) (mDiameter / 2 - centerSize / 2);
				top = (int) (mDiameter / 2 - centerSize / 2);
				childView.layout(left, top, left + centerSize, top + centerSize);
			} else {
				mStartAngle = mStartAngle % 360;
				left = (int) (mDiameter / 2 + Math.round(temp * Math.cos(Math.toRadians(mStartAngle))) - menuSize / 2);
				top = (int) (mDiameter / 2 + Math.round(temp * Math.sin(Math.toRadians(mStartAngle))) - menuSize / 2);
				mStartAngle = mStartAngle + angleDelay;
				childView.layout(left, top, left + menuSize, top + menuSize);
			}
		}

	}

	public void setItemMenuInfo(int itemIds[], String itemTexts[]) {
		mItemIds = itemIds;
		mItemText = itemTexts;
		addItemMenuViews();
	}

	private void addItemMenuViews() {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		for (int i = 0; i < mItemIds.length; i++) {
			View itemMenuView = inflater.inflate(R.layout.item_circle_menu, this, false);
			ImageView imageView = (ImageView) itemMenuView.findViewById(R.id.id_circle_menu_item_image);
			imageView.setImageResource(mItemIds[i]);
			TextView textView = (TextView) itemMenuView.findViewById(R.id.id_circle_menu_item_text);
			textView.setText(mItemText[i]);
			final int index = i;
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mListener != null) {
						mListener.onMenuClick(index);
					}
				}
			});
			addView(itemMenuView);
		}
	}

	private int getDefaultWidth() {
		int width = ScreenUtils.getScreenWidth(getContext());
		int height = ScreenUtils.getScreenHeight(getContext());
		return Math.min(width, height);
	}

	public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
		mListener = listener;
	}


	public interface OnMenuItemClickListener {

		void onCenterMenuClick();

		void onMenuClick(int index);

	}

	private float mLastX;
	private float mLastY;

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		float x = ev.getX();
		float y = ev.getY();

		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mDownTime = System.currentTimeMillis();
				mLastX = x;
				mLastY = y;
				mTmpAngle = 0;
				// 如果当前已经在快速滚动
				if (isFling) {
					// 移除快速滚动的回调
					removeCallbacks(mFlingRunnable);
					isFling = false;
					return true;
				}
				break;
			case MotionEvent.ACTION_MOVE:
				float startAngle = getAngle(mLastX, mLastY);
				float endAngle = getAngle(x, y);
				if (getQuadrant(x, y) == 1 || getQuadrant(x, y) == 4) {
					mStartAngle += endAngle - startAngle;
					mTmpAngle += endAngle - startAngle;
				} else {
					mStartAngle += startAngle - endAngle;
					mTmpAngle += startAngle - endAngle;
				}
				// 重新布局
				requestLayout();

				mLastX = x;
				mLastY = y;
				break;
			case MotionEvent.ACTION_UP:
				float anglePerSecond = mTmpAngle * 1000 / (System.currentTimeMillis() - mDownTime);
				if (Math.abs(anglePerSecond) > FLINGABLE_VALUE && !isFling) {
					// post一个任务，去自动滚动
					post(mFlingRunnable = new AutoFlingRunnable(anglePerSecond));

					return true;
				}
				break;
		}

		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}

	private float getAngle(float xTouch, float yTouch) {
		double x = xTouch - mDiameter / 2d;
		double y = yTouch - mDiameter / 2d;
		//		return (float) Math.toDegrees(Math.asin(y / Math.hypot(x, y)));
		return (float) (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
	}

	/**
	 * 根据当前位置计算象限
	 */
	private int getQuadrant(float x, float y) {
		int tmpX = (int) (x - mDiameter / 2);
		int tmpY = (int) (y - mDiameter / 2);
		if (tmpX >= 0) {
			return tmpY >= 0 ? 4 : 1;
		} else {
			return tmpY >= 0 ? 3 : 2;
		}

	}

	private AutoFlingRunnable mFlingRunnable;

	public class AutoFlingRunnable implements Runnable {

		private float angelPerSecond;

		public AutoFlingRunnable(float velocity) {
			angelPerSecond = velocity;
		}

		@Override
		public void run() {
			// 如果小于20,则停止
			if ((int) Math.abs(angelPerSecond) < 20) {
				isFling = false;
				return;
			}
			isFling = true;
			// 不断改变mStartAngle，让其滚动，/30为了避免滚动太快
			mStartAngle += (angelPerSecond / 30);
			// 逐渐减小这个值
			angelPerSecond /= 1.0666F;
			postDelayed(this, 30);
			// 重新布局
			requestLayout();
		}
	}
}
