package com.tuacy.xml.animation.layoutanimation;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.tuacy.common.base.activity.BaseActivity;
import com.tuacy.xml.R;

import java.util.ArrayList;
import java.util.List;

public class CodeGridLayoutAnimationActivity extends BaseActivity {


	private GridView mGridView;
	private GridAdapter mGrideAdapter;
	private List<String> mDatas = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_code_grid_layout_animation);
		mGridView = findView(R.id.gv_layout_animation);

		mDatas.addAll(getData());
		mGrideAdapter = new GridAdapter();
		mGridView.setAdapter(mGrideAdapter);

		Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_left);
		GridLayoutAnimationController controller = new GridLayoutAnimationController(animation);
		controller.setColumnDelay(0.75f);
		controller.setRowDelay(0.5f);
		controller.setDirection(GridLayoutAnimationController.DIRECTION_BOTTOM_TO_TOP|GridLayoutAnimationController.DIRECTION_LEFT_TO_RIGHT);
		controller.setDirectionPriority(GridLayoutAnimationController.PRIORITY_NONE);
		mGridView.setLayoutAnimation(controller);
		mGridView.startLayoutAnimation();

	}

	private List<String> getData() {

		List<String> data = new ArrayList<String>();
		for (int i = 1; i < 35; i++) {
			data.add("DATA " + i);
		}
		return data;
	}

	public class GridAdapter extends BaseAdapter {

		public View getView(int position, View convertView, ViewGroup parent) {
			TextView i = new TextView(CodeGridLayoutAnimationActivity.this);
			i.setText(mDatas.get(position));
			i.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.WRAP_CONTENT, GridView.LayoutParams.WRAP_CONTENT));
			return i;
		}

		public final int getCount() {
			return mDatas.size();
		}

		public final Object getItem(int position) {
			return null;
		}

		public final long getItemId(int position) {
			return position;
		}
	}
}