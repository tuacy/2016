package com.tuacy.xml.customerview;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.tuacy.common.base.activity.BaseActivity;
import com.tuacy.xml.R;
import com.tuacy.xml.customerview.view.PullScrollView;

public class CustomerViewActivity extends BaseActivity{

	private TableLayout mMainLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer_view);
		mMainLayout = (TableLayout) findViewById(R.id.table_layout);
		ImageView headerView = (ImageView)findViewById(R.id.background_img);
		PullScrollView pullScrollView = (PullScrollView) findViewById(R.id.pullscrollview);
		pullScrollView.setHeaderView(headerView);
		showTable();
	}

	public void showTable() {
		TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
																	   TableRow.LayoutParams.WRAP_CONTENT);
		layoutParams.gravity = Gravity.CENTER;
		layoutParams.leftMargin = 30;
		layoutParams.bottomMargin = 10;
		layoutParams.topMargin = 10;

		for (int i = 0; i < 30; i++) {
			TableRow tableRow = new TableRow(this);
			TextView textView = new TextView(this);
			textView.setText("Test pull down scroll view " + i);
			textView.setTextSize(20);
			textView.setPadding(15, 15, 15, 15);

			tableRow.addView(textView, layoutParams);
			if (i % 2 != 0) {
				tableRow.setBackgroundColor(Color.LTGRAY);
			} else {
				tableRow.setBackgroundColor(Color.WHITE);
			}

			mMainLayout.addView(tableRow);
		}
	}
}
