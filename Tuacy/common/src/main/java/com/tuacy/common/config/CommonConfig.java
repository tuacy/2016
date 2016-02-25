package com.tuacy.common.config;

import android.content.Context;

import com.tuacy.common.base.application.BaseApplication;
import com.tuacy.common.base.config.BasePropertiesConfig;


public class CommonConfig extends BasePropertiesConfig {

	private static final String PROPERTIES_NAME = "common-configs.properties";
	public static final  String VERSION         = "VERSION";

	private static CommonConfig sInstance = null;

	private CommonConfig() {
		super();
	}

	@Override
	protected Context getContext() {
		return BaseApplication.getBaseApplication();
	}

	@Override
	protected String getPropertyFileName() {
		return PROPERTIES_NAME;
	}

	public synchronized static CommonConfig getInstance() {
		if (sInstance == null) {
			synchronized (CommonConfig.class) {
				if (sInstance == null) {
					sInstance = new CommonConfig();
				}
			}
		}
		return sInstance;
	}
}
