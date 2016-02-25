package com.tuacy.common.concurrent;

import java.util.concurrent.ExecutorService;

public interface PausableExecutorService extends ExecutorService {

	void pause();

	void resume();

	boolean isPause();

}
