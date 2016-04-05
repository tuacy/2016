package com.tuacy.sourcecode.gesturedetector;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.tuacy.common.base.activity.BaseActivity;
import com.tuacy.common.log.CustomLog;
import com.tuacy.sourcecode.R;

public class GestureDetectorActivity extends BaseActivity {

	public static void startUp(Context context) {
		Intent intent = new Intent(context, GestureDetectorActivity.class);
		context.startActivity(intent);
	}

	private TextView        mTextView;
	private GestureDetector mGestureDetector;
	private GestureDetector.SimpleOnGestureListener mListener = new GestureDetector.SimpleOnGestureListener() {
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			CustomLog.tuacy("onSingleTapUp");
			return true;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			CustomLog.tuacy("onLongPress");
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			CustomLog.tuacy("onScroll");
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			CustomLog.tuacy("onFling");
			return true;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			CustomLog.tuacy("onShowPress");
		}

		@Override
		public boolean onDown(MotionEvent e) {
			CustomLog.tuacy("onDown");
			return true;
		}

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			CustomLog.tuacy("onDoubleTap");
			return true;
		}

		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			CustomLog.tuacy("MotionEvent" + (e.getAction() & MotionEvent.ACTION_MASK));
			return true;
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			CustomLog.tuacy("onSingleTapConfirmed");
			return true;
		}

		@Override
		public boolean onContextClick(MotionEvent e) {
			CustomLog.tuacy("onContextClick");
			return true;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gesturedetector);
		mTextView = findView(R.id.text_view_gestured_detector);
		mGestureDetector = new GestureDetector(mContext, mListener);
		mTextView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return mGestureDetector.onTouchEvent(event);
			}
		});
	}

}
