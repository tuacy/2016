package com.tuacy.xml.animation.layoutanimation;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.widget.ImageView;

import com.tuacy.common.base.activity.BaseActivity;
import com.tuacy.xml.R;

public class CodeLayoutAnimationActivity extends BaseActivity {

	private ImageView mAnimationView;
	private ImageView mObjectView;
	private ImageView mSetView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xml_property_animation);
		mAnimationView = findView(R.id.img_property_animation_animation);
		ValueAnimator valueAnimator = (ValueAnimator) AnimatorInflater.loadAnimator(this, R.animator.animator_animator);
		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animator) {
				// 当前动画值
				int currentValue = (Integer) animator.getAnimatedValue();
				// 根据比例更改目标view的宽度
				mAnimationView.setTranslationX(currentValue);
			}
		});
		valueAnimator.start();

		mObjectView = findView(R.id.img_property_animation_object);
		ObjectAnimator objectAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.animator_object_animator);
		objectAnimator.setTarget(mObjectView);
		objectAnimator.start();

		mSetView = findView(R.id.img_property_animation_set);
		AnimatorSet setAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.animator_set_animation);
		setAnimator.setTarget(mSetView);
		setAnimator.start();

	}
}
