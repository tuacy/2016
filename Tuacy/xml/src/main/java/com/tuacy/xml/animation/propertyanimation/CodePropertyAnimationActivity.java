package com.tuacy.xml.animation.propertyanimation;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.widget.ImageView;

import com.tuacy.common.base.activity.BaseActivity;
import com.tuacy.xml.R;

public class CodePropertyAnimationActivity extends BaseActivity {

	private ImageView mValue;
	private ImageView mObject;
	private ImageView mSet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_code_property_animation);
		mValue = findView(R.id.img_property_code_animation_animation);
		mObject = findView(R.id.img_property_code_animation_object);
		mSet = findView(R.id.img_property_code_animation_set);

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

	}
}
