package com.tuacy.xml.customerview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.tuacy.common.base.activity.BaseActivity;
import com.tuacy.xml.R;
import com.tuacy.xml.customerview.view.ScrollListView;

import java.util.Arrays;
import java.util.LinkedList;

public class ScrollListViewActivity extends BaseActivity{

	private String[] mStrings = {"Abbaye de Belloc",
								 "Abbaye du Mont des Cats",
								 "Abertam",
								 "Abondance",
								 "Ackawi",
								 "Acorn",
								 "Adelost",
								 "Affidelice au Chablis",
								 "Afuega'l Pitu",
								 "Airag",
								 "Airedale",
								 "Aisy Cendre",
								 "Allgauer Emmentaler",
								 "Abbaye de Belloc",
								 "Abbaye du Mont des Cats",
								 "Abertam",
								 "Abondance",
								 "Ackawi",
								 "Acorn",
								 "Adelost",
								 "Affidelice au Chablis",
								 "Afuega'l Pitu",
								 "Airag",
								 "Airedale",
								 "Aisy Cendre",
								 "Allgauer Emmentaler"};
	private LinkedList<String>   mListItems;
	private ScrollListView       mListView;
	private ArrayAdapter<String> mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scroll_list_view);
		mListView = (ScrollListView) findViewById(R.id.pull_list_view);
		mListItems = new LinkedList<>();
		mListItems.addAll(Arrays.asList(mStrings));
		mAdapter = new ArrayAdapter<>(this,R.layout.item_scroll_list_view, mListItems);

		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.view_header,mListView,false);
		//设置headerview不可点击
		mListView.addHeaderView(view,null,false);

		mListView.setAdapter(mAdapter);

		ImageView headerView = (ImageView)findViewById(R.id.background_img);
		mListView.setTopView(headerView);
	}

}
