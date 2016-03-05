package com.tuacy.xml.animation.layoutanimation;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.tuacy.common.base.activity.BaseActivity;
import com.tuacy.xml.R;

import java.util.ArrayList;
import java.util.List;

public class CodeLayoutAnimationActivity extends BaseActivity {

	private Button   mButtonAdd;
	private ListView mListView;
	private ArrayAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_code_layout_animation);
		mListView = findView(R.id.lv_layout_animation);
		mButtonAdd = findView(R.id.btn_add);

		mAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_expandable_list_item_1, getData());
		mListView.setAdapter(mAdapter);

		//代码设置通过加载XML动画设置文件来创建一个Animation对象；
		Animation animation= AnimationUtils.loadAnimation(this, R.anim.slide_in_left);   //得到一个LayoutAnimationController对象；
		LayoutAnimationController controller = new LayoutAnimationController(animation);   //设置控件显示的顺序；
		controller.setOrder(LayoutAnimationController.ORDER_REVERSE);   //设置控件显示间隔时间；
		controller.setDelay(0.3f);   //为ListView设置LayoutAnimationController属性；
		mListView.setLayoutAnimation(controller);
		mListView.startLayoutAnimation();

	}

	private List<String> getData() {

		List<String> data = new ArrayList<String>();
		data.add("测试数据1");
		data.add("测试数据2");
		data.add("测试数据3");
		data.add("测试数据4");

		return data;
	}
}
