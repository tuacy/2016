package com.tuacy.xml.animation.propertyanimation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.tuacy.common.base.activity.BaseActivity;
import com.tuacy.xml.R;

public class CodePropertyAnimationActivity extends BaseActivity {

	private ImageView mValue;
	private ImageView mObject;
	private ImageView mSet;
	private ImageView mMultiple;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_code_property_animation);
		mValue = findView(R.id.img_property_code_animation_animation);
		mObject = findView(R.id.img_property_code_animation_object);
		mSet = findView(R.id.img_property_code_animation_set);
		mMultiple = findView(R.id.img_property_code_multiple_animation);

		// ValueAnimator
		ValueAnimator valueAnimator = ValueAnimator.ofInt(3000, 20);
		valueAnimator.setDuration(1000);
		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				// 当前动画值
				int currentValue = (Integer) animation.getAnimatedValue();
				// 根据比例更改目标view的宽度
				mValue.setTranslationX(currentValue);
			}
		});
		valueAnimator.setRepeatCount(-1);
		valueAnimator.start();

		// ObjectAnimator
		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mObject, "translationX", 100.0f, 20.0f);
		objectAnimator.setDuration(3000);
		objectAnimator.setRepeatCount(-1);
		objectAnimator.start();

		// Multiple PropertyValuesHolder
		PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f);
		PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f, 0, 1f);
		PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f, 0, 1f);
		ObjectAnimator.ofPropertyValuesHolder(mMultiple, pvhX, pvhY, pvhZ).setDuration(10000).start();

		//AnimatorSet
		AnimatorSet animSet = new AnimatorSet();
		ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mSet, "translationX", -100.0f, 0.0f);
		objectAnimator1.setRepeatCount(-1);
		ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(mSet, "alpha", 0.0f, 1.0f);
		objectAnimator2.setRepeatCount(-1);
		animSet.setDuration(3000);
		animSet.setInterpolator(new LinearInterpolator());
		animSet.playTogether(objectAnimator1, objectAnimator2);
		animSet.start();
	}
}
