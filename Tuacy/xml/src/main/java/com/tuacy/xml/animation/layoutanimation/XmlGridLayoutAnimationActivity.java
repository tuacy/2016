package com.tuacy.xml.animation.layoutanimation;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.tuacy.common.base.activity.BaseActivity;
import com.tuacy.xml.R;

import java.util.ArrayList;
import java.util.List;

public class XmlGridLayoutAnimationActivity extends BaseActivity {

	private Button mButtonAdd;
	private ListView mListView;
	private ArrayAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xml_layout_animation);
		mButtonAdd = findView(R.id.btn_add);
		mListView = findView(R.id.lv_layout_animation);
		mAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_expandable_list_item_1, getData());
		mListView.setAdapter(mAdapter);

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
