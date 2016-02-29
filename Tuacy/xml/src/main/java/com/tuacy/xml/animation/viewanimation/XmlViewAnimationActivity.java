package com.tuacy.xml.animation.viewanimation;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.tuacy.common.base.activity.BaseActivity;
import com.tuacy.xml.R;

public class XmlViewAnimationActivity extends BaseActivity {

	private ImageView mAlphaView;
	private ImageView mScaleView;
	private ImageView mTranslateView;
	private ImageView mRotateView;
	private ImageView mSetView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xml_view_animation);
		mAlphaView = findView(R.id.img_view_animation_alpha);
		mScaleView = findView(R.id.img_view_animation_scale);
		mTranslateView = findView(R.id.img_view_animation_translate);
		mRotateView = findView(R.id.img_view_animation_rotate);
		mSetView = findView(R.id.img_view_animation_set);
		mAlphaView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_alpha));
		mScaleView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_scale));
		mTranslateView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_translate));
		mRotateView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_rotate));
		mSetView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_set));
	}
}
