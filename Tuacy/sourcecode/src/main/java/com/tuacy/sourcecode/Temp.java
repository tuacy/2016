package com.tuacy.sourcecode;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface Temp {


	// 不让再submit新的任务了，但是之前提交的还是会继续执行。
	void shutdown();

	// 不让再submit新的任务了，停止线程池里面所有的任务，不管是正在执行的还是没有执行的，并且返回没有执行的任务列表
	List<Runnable> shutdownNow();

	// 线程池是否shut down了
	boolean isShutdown();

	boolean isTerminated();

	boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException;

	<T> Future<T> submit(Callable<T> task);

	<T> Future<T> submit(Runnable task, T result);

	Future<?> submit(Runnable task);

	// 执行tasks里面所有的callable，返回所有的情况
	<T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException;

	<T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException;

	// 启动参数tasks里面所有的callable，当有一个处理完了就结束
	<T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException;

	<T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException;

}
