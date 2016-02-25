package com.tuacy.common.base.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;


import com.tuacy.common.log.CustomLog;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseApplication extends Application
	implements Application.ActivityLifecycleCallbacks, BaseAppCrashHandler.CrashListener {

	private static  BaseApplication sBaseApplication = null;
	protected final List<Activity>  mActivities      = new ArrayList<>();
	private         int             mActivityCount   = 0;

	@Override
	public void onCreate() {
		super.onCreate();
		sBaseApplication = this;
		registerActivityLifecycleCallbacks(this);
		Thread.setDefaultUncaughtExceptionHandler(new BaseAppCrashHandler(this.getApplicationContext(), this));
	}

	protected abstract void initializeApplication();

	protected abstract void deInitializeApplication();

	protected abstract void onAppCrash(Context context, Thread thread, Throwable ex);

	public static BaseApplication getBaseApplication() {
		return sBaseApplication;
	}

	public List<Activity> getActivities() {
		return mActivities;
	}

	public Activity getTopActivity() {
		return mActivities.get(mActivities.size() - 1);
	}

	public void exit() {
		for (int i = mActivities.size() - 1; i >= 0; i--) {
			Activity activity = mActivities.get(i);
			activity.finish();
		}
	}

	@Override
	public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
		if (0 == mActivityCount) {
			initializeApplication();
		}
		mActivities.add(activity);
		mActivityCount++;
	}

	@Override
	public void onActivityStarted(Activity activity) {

	}

	@Override
	public void onActivityResumed(Activity activity) {

	}

	@Override
	public void onActivityPaused(Activity activity) {

	}

	@Override
	public void onActivityStopped(Activity activity) {

	}

	@Override
	public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

	}

	@Override
	public void onActivityDestroyed(Activity activity) {
		mActivities.remove(activity);
		mActivityCount--;
		if (0 == mActivityCount) {
			deInitializeApplication();
		}
	}

	@Override
	public void uncaughtException(Context context, Thread thread, Throwable ex) {
		printErrorInfo(context, thread, ex);
		onAppCrash(context, thread, ex);
	}

	private void printErrorInfo(Context context, Thread thread, Throwable ex) {
		CustomLog.e("onAppCrash", "Thread        = " + thread.getName());
		CustomLog.e("onAppCrash", "Throwable     = " + ex);
		CustomLog.e("onAppCrash", "Error message = " + ex.getMessage());

		CustomLog.e("onAppCrash", "StackTrace:");
		String temp;
		StackTraceElement[] elements = ex.getStackTrace();
		if (elements != null) {
			for (StackTraceElement element : elements) {
				temp = element.toString();
				CustomLog.e("onAppCrash", temp);
			}
		}

		Throwable theCause = ex.getCause();
		if (theCause != null) {
			temp = theCause.toString();
			CustomLog.e("onAppCrash", "Cause:");
			CustomLog.e("onAppCrash", temp);

			elements = theCause.getStackTrace();
			if (elements != null) {
				CustomLog.e("onAppCrash", "Cause Stack:");
				for (StackTraceElement element : elements) {
					temp = element.toString();
					CustomLog.e("onAppCrash", temp);
				}
			}
		}
	}
}
