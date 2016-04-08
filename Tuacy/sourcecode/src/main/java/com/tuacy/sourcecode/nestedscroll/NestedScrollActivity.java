package com.tuacy.sourcecode.nestedscroll;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tuacy.common.base.activity.BaseActivity;
import com.tuacy.sourcecode.R;

public class NestedScrollActivity extends BaseActivity {

	public static void startUp(Context context) {
		Intent intent = new Intent(context, NestedScrollActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nested_scroll);
	}
}
