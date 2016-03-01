package com.tuacy.xml.drawable.rotate;

import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.tuacy.common.base.activity.BaseActivity;
import com.tuacy.xml.R;

public class RotateActivity extends BaseActivity{

	private ImageView mRotateImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rotate);
		mRotateImage = findView(R.id.img_rotate);
		RotateDrawable drawable = (RotateDrawable) mRotateImage.getDrawable();
		drawable.setLevel(drawable.getLevel() + 5000);
	}
}
