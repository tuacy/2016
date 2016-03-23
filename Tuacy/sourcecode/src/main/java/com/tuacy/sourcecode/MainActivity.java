package com.tuacy.sourcecode;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tuacy.common.base.activity.BaseActivity;
import com.tuacy.sourcecode.asynctask.AsyncTaskActivity;
import com.tuacy.sourcecode.concurrent.ConcurrentActivity;
import com.tuacy.sourcecode.handler.HandlerActivity;
import com.tuacy.sourcecode.handlerthread.HandlerThreadActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

	private ListView     mMainTitle;
	private List<String> mData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initListener();
		initData();
	}

	private void initData() {
		mData = new ArrayList<>();
		mData.add("Handler");
		mData.add("AsyncTask");
		mData.add("HandlerThread");
		mData.add("Concurrent");

		MainTitleAdapter adapter = new MainTitleAdapter(mContext, R.layout.item_main_title, mData);
		mMainTitle.setAdapter(adapter);
		mMainTitle.setOnItemClickListener(this);
	}

	private void initListener() {

	}

	private void initView() {
		mMainTitle = findView(R.id.lv_main_title);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch (position) {
			case 0:
				HandlerActivity.startUp(mContext);
				break;
			case 1:
				AsyncTaskActivity.startUp(mContext);
				break;
			case 2:
				HandlerThreadActivity.startUp(mContext);
				break;
			case 3:
				ConcurrentActivity.startUp(mContext);
				break;
		}
	}
}
