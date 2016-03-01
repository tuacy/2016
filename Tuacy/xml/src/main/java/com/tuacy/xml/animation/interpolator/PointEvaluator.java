package com.tuacy.xml.animation.interpolator;

import android.animation.TypeEvaluator;

public class PointEvaluator implements TypeEvaluator<Point> {

	@Override
	public Point evaluate(float fraction, Point startValue, Point endValue) {
		int startRadius = startValue.getRadius();
		int endRadius = endValue.getRadius();
		int currentRadius = (int) (startRadius + fraction * (endRadius - startRadius));
		return new Point(currentRadius);
	}
}
