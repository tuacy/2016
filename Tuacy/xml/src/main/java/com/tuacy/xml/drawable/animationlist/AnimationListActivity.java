package com.tuacy.xml.drawable.animationlist;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.tuacy.common.base.activity.BaseActivity;
import com.tuacy.xml.R;

public class AnimationListActivity extends BaseActivity{

	private ImageView mAnimationList;
	private Button mStartAnimationList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animation_list);
		mAnimationList = findView(R.id.img_animation_list);
		mStartAnimationList = findView(R.id.btn_start_animation_list);
		mStartAnimationList.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AnimationDrawable animationDrawable = (AnimationDrawable) mAnimationList.getDrawable();
				animationDrawable.start();
			}
		});
	}
}
