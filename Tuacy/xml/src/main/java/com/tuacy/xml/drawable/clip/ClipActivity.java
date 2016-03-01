package com.tuacy.xml.drawable.clip;

import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.tuacy.common.base.activity.BaseActivity;
import com.tuacy.xml.R;

public class ClipActivity extends BaseActivity{

	private ImageView mClipImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clip);
		mClipImage = (ImageView) findViewById(R.id.img_clip);
		ClipDrawable drawable = (ClipDrawable) mClipImage.getDrawable();
		drawable.setLevel(drawable.getLevel() + 5000);
	}
}
