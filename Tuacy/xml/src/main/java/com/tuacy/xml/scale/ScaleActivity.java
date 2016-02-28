package com.tuacy.xml.scale;

import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.tuacy.common.base.activity.BaseActivity;
import com.tuacy.xml.R;

public class ScaleActivity extends BaseActivity{

	private ImageView mScaleImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scale);
		mScaleImage = findView(R.id.img_scale);
		ScaleDrawable drawable = (ScaleDrawable) mScaleImage.getDrawable();
		drawable.setLevel(drawable.getLevel() + 5000);
	}
}
