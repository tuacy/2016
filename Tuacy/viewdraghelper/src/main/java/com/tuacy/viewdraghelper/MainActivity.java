package com.tuacy.viewdraghelper;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.tuacy.common.base.activity.BaseActivity;
import com.tuacy.common.log.CustomLog;
import com.tuacy.viewdraghelper.widget.DrawerDragLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

	private ListView         mDrawerMenu;
	private DrawerDragLayout mDrawerView;
	private Button           mSwitch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();

	}

	private void initView() {
		mDrawerMenu = findView(R.id.lv_drawer_menu);
		mDrawerView = findView(R.id.drawer_view);
		mSwitch = findView(R.id.btn_switch);
	}

	private void initData() {
		mDrawerMenu.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getData()));
		mDrawerMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				CustomLog.tuacy("item click");
			}
		});
		mSwitch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDrawerView.switchDrawer();
			}
		});
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
