package com.tuacy.library_view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.tuacy.common.base.activity.BaseActivity;
import com.tuacy.library_view.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity {

	private List<String> mDatas = Arrays.asList("短信1", "短信2", "短信3", "短信4", "短信5", "短信6", "短信7", "短信8", "短信9");

	private ViewPager            mViewPager;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment> mTabContents = new ArrayList<Fragment>();

	private ViewPagerIndicator mIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mViewPager = findView(R.id.viewpager_fragment);
		mIndicator = findView(R.id.layout_indicator);
		initData();
	}

	private void initData() {
		for (String data : mDatas) {
			VpFragment fragment = VpFragment.newInstance(data);
			mTabContents.add(fragment);
		}
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public int getCount() {
				return mTabContents.size();
			}

			@Override
			public Fragment getItem(int position) {
				return mTabContents.get(position);
			}
		};
		mIndicator.setTabDatas(mDatas);
		mViewPager.setAdapter(mAdapter);
		mIndicator.setViewPager(mViewPager, 0);
	}

}
