package com.tuacy.xml.animation.viewanimation;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.tuacy.common.base.activity.BaseActivity;
import com.tuacy.xml.R;

public class CodeViewAnimationActivity extends BaseActivity {

	private ImageView mAlpha;
	private ImageView mTranslate;
	private ImageView mScale;
	private ImageView mRotate;
	private ImageView mSet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_code_view_animation);
		mAlpha = findView(R.id.img_view_animation_code_alpha);
		mTranslate = findView(R.id.img_view_animation_code_translate);
		mScale = findView(R.id.img_view_animation_code_scale);
		mRotate = findView(R.id.img_view_animation_code_rotate);
		mSet = findView(R.id.img_view_animation_code_set);
		// scale
		ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f, Animation.RELATIVE_TO_SELF, 0.0f,
														   Animation.RELATIVE_TO_SELF, 1.0f);
		scaleAnimation.setDuration(1000);
		scaleAnimation.setRepeatCount(-1);
		mScale.startAnimation(scaleAnimation);
		// translate
		TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f,
																	   Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		translateAnimation.setDuration(2000);
		translateAnimation.setRepeatCount(-1);
		mTranslate.startAnimation(translateAnimation);
		// rotate
		RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
															  0.5f);
		rotateAnimation.setDuration(2000);
		rotateAnimation.setRepeatCount(-1);
		mRotate.startAnimation(rotateAnimation);
		// alpha
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
		alphaAnimation.setDuration(1000);
		alphaAnimation.setRepeatCount(-1);
		mAlpha.startAnimation(alphaAnimation);
		// set
		AnimationSet animationSet = new AnimationSet(true);
		TranslateAnimation setTranslateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
																		  2.0f, Animation.RELATIVE_TO_SELF, 0.0f,
																		  Animation.RELATIVE_TO_SELF, 0.0f);
		setTranslateAnimation.setRepeatCount(-1);
		ScaleAnimation setScaleAnimation = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f, Animation.RELATIVE_TO_SELF, 0.0f,
															  Animation.RELATIVE_TO_SELF, 1.0f);
		setScaleAnimation.setRepeatCount(-1);
		animationSet.addAnimation(setScaleAnimation);
		animationSet.addAnimation(setTranslateAnimation);
		animationSet.setDuration(1000);
		mSet.startAnimation(animationSet);

	}
}
