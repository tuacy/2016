package com.tuacy.xml.animation.interpolator;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.widget.TextView;

import com.tuacy.common.base.activity.BaseActivity;
import com.tuacy.xml.R;

public class InterpolatorActivity extends BaseActivity {

	private TextView mInterpolatorChar;
	private BouncePointView mPointView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_interpolator);
		mInterpolatorChar = findView(R.id.img_interpolator);
		mPointView = findView(R.id.view_bounce_point_view);

		// ValueAnimator
		ValueAnimator valueAnimator = ValueAnimator.ofObject(new CharEvaluator(), new Character('A'), new Character('Z'));
		valueAnimator.setDuration(10000);
		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				char currentValue = (char) animation.getAnimatedValue();
				mInterpolatorChar.setText(String.valueOf(currentValue));
			}
		});
		valueAnimator.setInterpolator(new InvertedOrderLinearInterpolator());
		valueAnimator.start();

		mPointView.doPointAnim();

	}
}
