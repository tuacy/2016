package com.tuacy.common.base.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;

import java.lang.reflect.Field;

/**
 * used in pager + fragment
 */
public abstract class BaseLazyFragment extends Fragment {

	protected Context mContext         = null;
	private   boolean isFirstResume    = true;
	private   boolean isFirstVisible   = true;
	private   boolean isFirstInvisible = true;
	private   boolean isPrepared       = false;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		// for bug ---> java.lang.IllegalStateException: Activity has been destroyed
		try {
			Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);

		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initPrepare();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (isFirstResume) {
			isFirstResume = false;
			return;
		}
		if (getUserVisibleHint()) {
			onUserVisible();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (getUserVisibleHint()) {
			onUserInvisible();
		}
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			if (isFirstVisible) {
				isFirstVisible = false;
				initPrepare();
			} else {
				onUserVisible();
			}
		} else {
			if (isFirstInvisible) {
				isFirstInvisible = false;
				onFirstUserInvisible();
			} else {
				onUserInvisible();
			}
		}
	}

	private synchronized void initPrepare() {
		if (isPrepared) {
			onFirstUserVisible();
		} else {
			isPrepared = true;
		}
	}

	/**
	 * get the support fragment manager
	 */
	protected FragmentManager getSupportFragmentManager() {
		return getActivity().getFragmentManager();
	}

	/**
	 * when fragment is visible for the first time, here we can do some initialized work or refresh data only once
	 */
	protected abstract void onFirstUserVisible();

	/**
	 * this method like the fragment's lifecycle method onResume()
	 */
	protected abstract void onUserVisible();

	/**
	 * when fragment is invisible for the first time
	 */
	private void onFirstUserInvisible() {
		// here we do not recommend do something
	}

	/**
	 * this method like the fragment's lifecycle method onPause()
	 */
	protected abstract void onUserInvisible();

}
