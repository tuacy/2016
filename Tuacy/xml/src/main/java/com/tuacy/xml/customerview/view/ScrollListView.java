package com.tuacy.xml.customerview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;

import com.nineoldandroids.view.ViewHelper;
import com.tuacy.xml.R;

import java.util.ArrayList;

public class ScrollListView extends ListView{

	private final String TAG                   = "harvic";
	//用户定义的手指可移动的最大高度,在这里，手指移动距离是content移动距离的两倍，是header移动距离的四倍
	private       int    mContentMaxMoveHeight = 0;

	/**
	 * 是否关闭ListView的滑动.
	 */
	private boolean mEnableTouch = false;
	//标识当前view是否移动
	boolean mIsMoving = false;
	//初始点击位置
	private Point mTouchPoint = new Point();

	private int mContentTop;

	private int mHeaderCurTop;
	/**
	 * 阻尼系数,越小阻力就越大.
	 */
	public static final float SCROLL_RATIO = 0.5f;

	//底部图片View
	private View mTopView;
	//头部图片的初始化位置
	private Rect mHeadInitRect    = new Rect();
	//底部View
	private View mContentView     = this;
	//ScrollView的contentView的初始化位置
	private Rect mContentInitRect = new Rect();

	private ArrayList<View> mHeadViews = new ArrayList<View>();

	public ScrollListView(Context context) {
		this(context, null);
	}

	public ScrollListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		if (null != attrs) {
			TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ScrollListView);
			if (ta != null) {
				mContentMaxMoveHeight = (int) ta.getDimension(R.styleable.ScrollListView_maxMoveHeight, -1);
				ta.recycle();
			}
		}
	}

	public void setTopView(View view) {
		mTopView = view;
	}

	@Override
	public void addHeaderView(View v) {
		super.addHeaderView(v);
		if (v != null) {
			mHeadViews.add(v);
		}
	}

	@Override
	public void addHeaderView(View v, Object data, boolean isSelectable) {
		super.addHeaderView(v, data, isSelectable);
		if (v != null) {
			mHeadViews.add(v);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_MOVE: {
				int moveHeight = (int) event.getY() - mTouchPoint.y;
				int scrolledHeight = 0;
				try {
					scrolledHeight = getScrollHeight(this, mHeadViews);
				} catch (Exception e) {
					Log.e(TAG, e.getMessage());
				}
				if (moveHeight > 0 && scrolledHeight == 0) {
					if (moveHeight > mContentMaxMoveHeight){
						moveHeight = mContentMaxMoveHeight;
					}

					float headerMoveHeight = moveHeight * 0.5f * SCROLL_RATIO;
					float contentMoveHeight = moveHeight * SCROLL_RATIO;

					mHeaderCurTop = (int) (mHeadInitRect.top + headerMoveHeight);
					mContentTop = (int) (mContentInitRect.top + contentMoveHeight);

					//viewHelper是导入的jar包，在有些情况下layout()函数实现的并不好使，源自NineOldAndroids开源项目
					ViewHelper.setTranslationY(mTopView, headerMoveHeight);
					ViewHelper.setTranslationY(mContentView, contentMoveHeight);
					mTopView.layout(mHeadInitRect.left, mHeaderCurTop, mHeadInitRect.right, (int) (mHeadInitRect.bottom + headerMoveHeight));
					mContentView.layout(mContentInitRect.left, mContentTop, mContentInitRect.right, (int) (mContentInitRect.bottom + contentMoveHeight));


					mIsMoving = true;
					mEnableTouch = true;
				} else {
					mEnableTouch = false;
				}
			}
			break;
			case MotionEvent.ACTION_UP: {
				//反弹
				if (mIsMoving) {
					ViewHelper.setTranslationY(mTopView, 0);
					mTopView.layout(mHeadInitRect.left, mHeadInitRect.top, mHeadInitRect.right, mHeadInitRect.bottom);
					TranslateAnimation headAnim = new TranslateAnimation(0, 0, mHeaderCurTop - mHeadInitRect.top, 0);
					headAnim.setDuration(200);
					mTopView.startAnimation(headAnim);

					mContentView.layout(mContentInitRect.left, mContentInitRect.top, mContentInitRect.right, mContentInitRect.bottom);
					ViewHelper.setTranslationY(mContentView, 0);
					TranslateAnimation contentAnim = new TranslateAnimation(0, 0, mContentTop - mContentInitRect.top, 0);
					contentAnim.setDuration(200);
					mContentView.startAnimation(contentAnim);
					mIsMoving = false;
				}
				mEnableTouch = false;
			}
			break;
			case MotionEvent.ACTION_CANCEL: {
				mEnableTouch = false;
			}
			break;
		}
		// 禁止控件本身的滑动.
		//这句厉害,如果mEnableMoving返回TRUE,那么就不会执行super.onTouchEvent(event)
		//只有返回FALSE的时候,才会执行super.onTouchEvent(event)
		//禁止控件本身的滑动，就会让它，本来应有的滑动就不会滑动了，比如向上滚动
		//！！！！！这点对于listview控件尤为重要。因为在上滑时，如果不禁止控件本身的向上移动，
		// 就会乱套，因为你本不需要利用setTranslationY（）上移的地方，他仍然会上移
		return mEnableTouch || super.onTouchEvent(event);
	}


	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			//保存原始位置
			mTouchPoint.set((int) event.getX(), (int) event.getY());
			mHeadInitRect.set(mTopView.getLeft(), mTopView.getTop(), mTopView.getRight(), mTopView.getBottom());
			mContentInitRect.set(mContentView.getLeft(), mContentView.getTop(), mContentView.getRight(), mContentView.getBottom());
			mIsMoving = false;
			mEnableTouch = false;
		}
		return super.onInterceptTouchEvent(event);
	}

	/**
	 * 注意：
	 * 1、在ListView中，使用getChildAt(index)的取值，只能是当前可见区域（列表可滚动）的子项！
	 * 即取值范围在 >= ListView.getFirstVisiblePosition() &&  <= ListView.getLastVisiblePosition();
	 * <p/>
	 * 2、不要在listView中添加负值的marginTop，不然会导致getFirstVibisble（）得到的值不准
	 * 正值的marginTop得到的getFirstVisible()是准确的
	 * <p/>
	 * 3、目前计算方法仅考虑heaerview与Item都全部显示的情况下，对于headerView的visible设为GONE的情况没有考虑
	 *
	 * @return
	 */
	public int getScrollHeight(ListView list, ArrayList<? extends View> headviews) {
		if (list == null || headviews == null){
			return -1;
		}
		//!!!注意!!!
		//这里使用list.getHeaderViewsCount();获取到的headview数量
		int headCount = list.getHeaderViewsCount();
		int firstVisiblePos = list.getFirstVisiblePosition();
		int scrollHeight = 0;
		if (firstVisiblePos < headCount) {
			//如果还在header内部，说明只需要逐个计算header的高度就好了。
			if (headviews.size() == 0) {
				new Exception("内部含有headerView,请在函数入口处设置headview list");
				return -1;
			}
			for (int i = 0; i <= firstVisiblePos; i++) {
				View view = headviews.get(i);
				if (view != null && i == firstVisiblePos) {
					//注意，getTop()是负值，因为已经滚到不可见区域去了
					scrollHeight += (-view.getTop());
				} else if (i != firstVisiblePos) {
					scrollHeight += view.getHeight();
				}
			}
		} else {
			//这里假设除了headview以外的所有的正常ListItem的高度都是一样的。如果你的不一样，需要改写这一部分的计算方式了
			//已经滚出所以headView，只需要将所以headview高度相加，然后再加上其它所有list的高度即可
			if (headviews != null) {
				for (View view : headviews) {
					scrollHeight += view.getHeight();
				}
			}
			//获取单个item的视图
			View itemView = list.getChildAt(0);
			//值得非常注意的是firstVisiblePos是从0开始算的，所以headCount正好对应listview的第一个item的索引
			if (itemView != null) {
				//这里计算的是从headview到当前可见的item之间已经被完全滚过去的item的总高度
				scrollHeight += (firstVisiblePos - headCount) * itemView.getHeight();
			}
			//最后加上当前可见的item，已经滚动的部分
			scrollHeight += (-itemView.getTop());
		}
		return scrollHeight;
	}
}
