package com.tuacy.common.base.properties;

public interface BaseProperties {

	boolean getBoolean(String key);

	boolean getBoolean(String key, boolean defVal);

	int getInteger(String key);

	int getInteger(String key, int defVal);

	short getShort(String key);

	short getShort(String key, int defVal);

	String getString(String key);

	String getString(String key, String defVal);
}
