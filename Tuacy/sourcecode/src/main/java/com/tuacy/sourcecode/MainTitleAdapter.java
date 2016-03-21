package com.tuacy.sourcecode;

import android.content.Context;
import android.widget.TextView;

import com.tuacy.common.base.adapter.BaseAdapterRefine;
import com.tuacy.common.base.adapter.ViewHolder;

import java.util.List;

public class MainTitleAdapter extends BaseAdapterRefine<String> {

	public MainTitleAdapter(Context context, int layoutId, List<String> data) {
		super(context, layoutId, data);
	}

	@Override
	public void convert(ViewHolder viewHolder, String s) {
		TextView titleView = viewHolder.getView(R.id.tv_title);
		titleView.setText(s);
	}
}
