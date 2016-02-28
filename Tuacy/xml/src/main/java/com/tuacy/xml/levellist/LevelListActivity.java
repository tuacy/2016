package com.tuacy.xml.levellist;

import android.os.Bundle;
import android.widget.ImageView;

import com.tuacy.common.base.activity.BaseActivity;
import com.tuacy.xml.R;

public class LevelListActivity extends BaseActivity{

	private ImageView mLevelImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level_list);
		mLevelImage = (ImageView) findViewById(R.id.img_level);
		mLevelImage.getDrawable().setLevel(10);
	}
}
