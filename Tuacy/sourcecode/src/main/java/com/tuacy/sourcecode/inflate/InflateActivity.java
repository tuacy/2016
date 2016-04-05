package com.tuacy.sourcecode.inflate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.tuacy.common.base.activity.BaseActivity;
import com.tuacy.sourcecode.R;

public class InflateActivity extends BaseActivity {

	public static void startUp(Context context) {
		Intent intent = new Intent(context, InflateActivity.class);
		context.startActivity(intent);
	}

	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inflate);
		mListView = findView(R.id.list_view_test);
		AdapterInflate adapter = new AdapterInflate(mContext);
		mListView.setAdapter(adapter);

	}
}
