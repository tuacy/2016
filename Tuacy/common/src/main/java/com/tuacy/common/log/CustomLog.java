package com.tuacy.common.log;

import android.util.Log;


public class CustomLog {

	private static CustomLogger mLogger       = new DefaultLogger();
	private static boolean      mDebugEnabled = true;

	public static void setCustomLogger(CustomLogger logger) {
		CustomLog.mLogger = logger;
	}

	public static void setDebugEnabled(boolean enable) {
		mDebugEnabled = enable;
	}

	public static boolean isDebugEnabled() {
		return mDebugEnabled;
	}

	public static void d(Class<?> clz, String format, Object... args) {
		if (isDebugEnabled()) {
			mLogger.d(clz, format, args);
		}
	}

	public static void e(Class<?> clz, Throwable t, String format, Object... args) {
		if (isDebugEnabled()) {
			mLogger.e(clz, t, format, args);
		}
	}

	public static void e(Class<?> clz, String format, Object... args) {
		if (isDebugEnabled()) {
			mLogger.e(clz, format, args);
		}
	}

	public static void d(String tag, String msg) {
		if (isDebugEnabled()) {
			mLogger.d(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (isDebugEnabled()) {
			mLogger.e(tag, msg);
		}
	}

	public static void tuacy(String msg) {
		if (isDebugEnabled()) {
			mLogger.d("tuacy", msg);
		}
	}

	private static class DefaultLogger implements CustomLogger {

		@Override
		public void d(Class<?> clazz, String format, Object... args) {
			Log.d(clazz.getSimpleName(), String.format(format, args));
		}

		@Override
		public void e(Class<?> clazz, String format, Object... args) {
			Log.e(clazz.getSimpleName(), String.format(format, args));
		}

		@Override
		public void e(Class<?> clazz, Throwable t, String format, Object... args) {
			Log.e(clazz.getSimpleName(), String.format(format, args), t);
		}

		@Override
		public void d(String tag, String msg) {
			Log.d(tag, msg);
		}

		@Override
		public void e(String tag, String msg) {
			Log.e(tag, msg);
		}
	}
}
