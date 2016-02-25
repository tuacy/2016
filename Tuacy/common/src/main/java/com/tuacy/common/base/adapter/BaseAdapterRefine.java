package com.tuacy.common.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;
import java.util.Set;

public abstract class BaseAdapterRefine<T> extends BaseAdapter {

	protected Context      mContext    = null;
	protected List<T>      mData       = null;
	protected Set<Integer> mSelections = null;
	protected boolean      mExpand     = true;
	private   int          mLayoutId   = 0;

	public BaseAdapterRefine(Context context, int layoutId) {
		this(context, layoutId, null);
	}

	public BaseAdapterRefine(Context context, int layoutId, List<T> data) {
		mContext = context;
		mData = data;
		mLayoutId = layoutId;
	}

	public void setData(List<T> data) {
		mData = data;
		notifyDataSetChanged();
	}

	public List<T> getData() {
		return mData;
	}

	public void setSelections(Set<Integer> selections) {
		mSelections = selections;
		notifyDataSetChanged();
	}

	public void clearSelections() {
		if (mSelections != null) {
			mSelections.clear();
			notifyDataSetChanged();
		}
	}

	public boolean isExpand() {
		return mExpand;
	}

	public void setExpand(boolean expand) {
		this.mExpand = expand;
	}

	@Override
	public int getCount() {
		if (mExpand) {
			return mData == null ? 0 : mData.size();
		} else {
			return 0;
		}
	}

	@Override
	public T getItem(int position) {
		return mData == null ? null : mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = ViewHolder.getViewHolder(mContext, convertView, parent, mLayoutId, position);
		convert(viewHolder, getItem(position));
		return viewHolder.getConvertView();
	}

	public abstract void convert(ViewHolder viewHolder, T t);
}
