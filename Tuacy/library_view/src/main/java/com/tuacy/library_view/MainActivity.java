package com.tuacy.library_view;

import android.os.Bundle;

import com.tuacy.common.base.activity.BaseActivity;
import com.tuacy.library_view.view.CircleMenuLayout;

public class MainActivity extends BaseActivity {

	private String[] mItemTexts = new String[]{"安全中心 ",
											   "特色服务",
											   "投资理财",
											   "转账汇款",
											   "我的账户",
											   "信用卡"};
	private int[]    mItemImgs  = new int[]{R.drawable.ic_safe_normal,
											R.drawable.ic_server_normal,
											R.drawable.ic_finance_normal,
											R.drawable.ic_money_normal,
											R.drawable.ic_user_normal,
											R.drawable.ic_credit_normal};

	private CircleMenuLayout mLayoutCircle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mLayoutCircle = findView(R.id.layout_circle_menu);
		mLayoutCircle.setItemMenuInfo(mItemImgs, mItemTexts);
	}

}
