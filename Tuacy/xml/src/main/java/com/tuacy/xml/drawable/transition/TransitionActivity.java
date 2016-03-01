package com.tuacy.xml.drawable.transition;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.tuacy.common.base.activity.BaseActivity;
import com.tuacy.xml.R;

public class TransitionActivity extends BaseActivity{

	private ImageView mTransitionImage;
	private Button mTransitionButton;
	private boolean mOn = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transition);
		mTransitionImage = (ImageView) findViewById(R.id.img_transition);
		mTransitionButton = (Button) findViewById(R.id.btn_transition);
		mTransitionButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mOn) {
					mOn = false;
					TransitionDrawable drawable = (TransitionDrawable) mTransitionImage.getDrawable();
					drawable.startTransition(500);
				} else {
					mOn = true;
					TransitionDrawable drawable = (TransitionDrawable) mTransitionImage.getDrawable();
					drawable.reverseTransition(500);
				}

			}
		});
	}
}
