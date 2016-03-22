package com.tuacy.library_view.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.tuacy.library_view.R;

/**
 * Created by tuacy on 2016/3/22.
 */
public class ProportionLayout extends ViewGroup {

	private static final int DEFAULT_ROW    = 2;
	private static final int DEFAULT_COLUMN = 2;

	private int mRow    = DEFAULT_ROW;
	private int mColumn = DEFAULT_COLUMN;

	// 每个item的宽度和高度，这个是确定的。
	private int mEachItemWidth;
	private int mEachItemHeight;

	public ProportionLayout(Context context) {
		this(context, null);
	}

	public ProportionLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ProportionLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr) {

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProportionLayout);
		mColumn = typedArray.getInt(R.styleable.ProportionLayout_columnNum, DEFAULT_COLUMN);
		mRow = typedArray.getInt(R.styleable.ProportionLayout_rowNum, DEFAULT_ROW);
		typedArray.recycle();

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (getChildCount() > mRow * mColumn) {
			throw new RuntimeException("ProportionLayout count error");
		}

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 确定自身的大小
		setMeasuredDimension(widthSize, heightSize);
		// 确定子View的大小
		mEachItemWidth = (int) (widthSize * 1.0f / mColumn);
		mEachItemHeight = (int) (heightSize * 1.0f / mRow);
		for (int index = 0; index < getChildCount(); index++) {
			View childView = getChildAt(index);
			childView.measure(MeasureSpec.makeMeasureSpec(mEachItemWidth, MeasureSpec.EXACTLY),
							  MeasureSpec.makeMeasureSpec(mEachItemHeight, MeasureSpec.EXACTLY));
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childViewCount = getChildCount();
		for (int index = 0; index < childViewCount; index++) {
			View childView = getChildAt(index);
			int row = index / mRow;
			int column = index % mColumn;
			int left = column * mEachItemWidth;
			int top = row * mEachItemHeight;
			childView.layout(left, top, left + mEachItemWidth, top + mEachItemHeight);
		}
	}
}
