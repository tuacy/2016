package com.tuacy.sourcecode.handlerthread;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.HandlerThread;

import com.tuacy.common.base.activity.BaseActivity;
import com.tuacy.sourcecode.R;

import java.util.concurrent.Executors;

public class HandlerThreadActivity extends BaseActivity {


	public static void startUp(Context context) {
		Intent intent = new Intent(context, HandlerThreadActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_handler_thread);
	}
}
