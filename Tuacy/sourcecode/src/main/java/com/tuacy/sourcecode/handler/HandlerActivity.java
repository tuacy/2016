package com.tuacy.sourcecode.handler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.tuacy.common.base.activity.BaseActivity;
import com.tuacy.common.log.CustomLog;
import com.tuacy.sourcecode.R;

public class HandlerActivity extends BaseActivity implements View.OnClickListener {

	public static void startUp(Context context) {
		Intent intent = new Intent(context, HandlerActivity.class);
		context.startActivity(intent);
	}


	private Handler mMainThreadHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			CustomLog.tuacy("main thread 收到消息");
		}
	};
	private ChildThread1 mChildThread1;
	private Handler      mChild1ThreadHandler;
	private ChildThread2 mChildThread2;

	private Handler mChild2ThreadHandler;

	private Button mMainChild;
	private Button mChildMain;
	private Button mChildChild;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_handler);

		mMainChild = findView(R.id.btn_main_child);
		mMainChild.setOnClickListener(this);
		mChildMain = findView(R.id.btn_child_main);
		mChildMain.setOnClickListener(this);
		mChildChild = findView(R.id.btn_child_child);
		mChildChild.setOnClickListener(this);

		mChildThread1 = new ChildThread1();
		mChildThread1.start();

		mChildThread2 = new ChildThread2();
		mChildThread2.start();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_main_child:
				//主线程中发给线程1
				mChild1ThreadHandler.sendEmptyMessage(0x001);
				break;
			case R.id.btn_child_main:
				//主线程告诉线程1 让线程1发消息给主线程
				mChild1ThreadHandler.sendEmptyMessage(0x002);
				break;
			case R.id.btn_child_child:
				//主线程告诉线程1 让线程1发消息给线程2
				mChild1ThreadHandler.sendEmptyMessage(0x003);
				break;
		}
	}

	//主线程->子线程

	//子线程->主线程

	//子线程->子线程

	class ChildThread1 extends Thread {

		@Override
		public void run() {
			Looper.prepare();
			mChild1ThreadHandler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					switch (msg.what) {
						case 0x001:
							CustomLog.tuacy("收到主线程发过来的消息");
							break;
						case 0x002:
							CustomLog.tuacy("开始发消息给主线程");
							mMainThreadHandler.sendEmptyMessage(0);
							break;
						case 0x003:
							CustomLog.tuacy("开始发消息给子线程");
							mChild2ThreadHandler.sendEmptyMessage(0);
							break;
					}
				}
			};

			Looper.loop();
		}
	}

	class ChildThread2 extends Thread {

		@Override
		public void run() {
			Looper.prepare();
			mChild2ThreadHandler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					CustomLog.tuacy("thread2 收到消息");
				}
			};

			Looper.loop();
		}
	}
}
