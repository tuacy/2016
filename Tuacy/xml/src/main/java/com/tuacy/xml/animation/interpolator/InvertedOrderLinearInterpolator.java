package com.tuacy.xml.animation.interpolator;

import android.animation.TimeInterpolator;

public class InvertedOrderLinearInterpolator implements TimeInterpolator {

	@Override
	public float getInterpolation(float input) {
		return 1 - input;
	}
}
