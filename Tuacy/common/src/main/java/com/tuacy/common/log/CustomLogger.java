package com.tuacy.common.log;

public interface CustomLogger {

	void d(String tag, String msg);

	void e(String tag, String msg);

	void d(Class<?> clazz, String format, Object... args);

	void e(Class<?> clazz, String format, Object... args);

	void e(Class<?> clazz, Throwable t, String format, Object... args);
	
}
