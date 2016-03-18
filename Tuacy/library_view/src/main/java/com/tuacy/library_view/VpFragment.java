package com.tuacy.library_view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class VpFragment extends Fragment {

	public static final String BUNDLE_TITLE = "title";
	private             String mTitle       = "DefaultValue";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Bundle arguments = getArguments();
		if (arguments != null) {
			mTitle = arguments.getString(BUNDLE_TITLE);
		}

		TextView tv = new TextView(getActivity());
		tv.setText(mTitle);
		tv.setGravity(Gravity.CENTER);

		return tv;
	}

	public static VpFragment newInstance(String title) {
		Bundle bundle = new Bundle();
		bundle.putString(BUNDLE_TITLE, title);
		VpFragment fragment = new VpFragment();
		fragment.setArguments(bundle);
		return fragment;
	}
}
