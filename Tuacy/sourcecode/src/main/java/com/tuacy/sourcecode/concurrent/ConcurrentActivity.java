package com.tuacy.sourcecode.concurrent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tuacy.common.base.activity.BaseActivity;
import com.tuacy.common.log.CustomLog;
import com.tuacy.sourcecode.R;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;

public class ConcurrentActivity extends BaseActivity implements View.OnClickListener {

	public static void startUp(Context context) {
		Intent intent = new Intent(context, ConcurrentActivity.class);
		context.startActivity(intent);
	}

	private Button mThread;
	private Button mRunnable;
	private Button mFutureTask;
	private Button mCompletionService;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_concurrent);
		mThread = findView(R.id.btn_test_thread);
		mThread.setOnClickListener(this);
		mRunnable = findView(R.id.btn_test_runnable);
		mRunnable.setOnClickListener(this);
		mFutureTask = findView(R.id.btn_test_future_task);
		mFutureTask.setOnClickListener(this);
		mCompletionService = findView(R.id.btn_test_completionService);
		mCompletionService.setOnClickListener(this);
	}

	private void testThread() {
		Thread thread1 = new Thread() {
			@Override
			public void run() {
				for (int index = 0; index < 10; index++) {
					CustomLog.tuacy("A index =" + index);
				}
			}
		};
		thread1.start();

		Thread thread2 = new Thread() {
			@Override
			public void run() {
				for (int index = 0; index < 10; index++) {
					CustomLog.tuacy("B index =" + index);
				}
			}
		};
		thread2.start();

		Thread thread3 = new Thread() {
			@Override
			public void run() {
				for (int index = 0; index < 10; index++) {
					CustomLog.tuacy("C index =" + index);
				}
			}
		};
		thread3.start();
	}

	private void testRunnable() {

		Runnable runnable = new Runnable() {
			int ticket = 10;

			@Override
			public void run() {
				for (int index = 0; index < 10; index++) {
					ticket--;
					if (ticket < 0) {
						break;
					}
					CustomLog.tuacy("Runnable ticket =" + ticket);
				}
			}
		};

		Thread thread1 = new Thread(runnable) {
			@Override
			public void run() {
				for (int index = 0; index < 10; index++) {
					CustomLog.tuacy("Runnable A index =" + index);
				}
			}
		};
		thread1.start();

		Thread thread2 = new Thread(runnable);
		thread2.start();

		Thread thread3 = new Thread(runnable);
		thread3.start();

	}

	private void testFutureTask() {
		// Callable+Future
		futureTask1();

		// Callable+FutureTask
		futureTask2();

	}

	private void futureTask1() {
		ExecutorService executor = Executors.newCachedThreadPool();
		Task task = new Task();
		Future<Integer> result = executor.submit(task);
		executor.shutdown();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		CustomLog.tuacy("主线程在执行任务");

		try {
			CustomLog.tuacy("task运行结果" + result.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		CustomLog.tuacy("所有任务执行完毕");
	}

	private void futureTask2() {
		ExecutorService executor = Executors.newCachedThreadPool();
		Task task = new Task();
		FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
		executor.submit(futureTask);
		executor.shutdown();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		CustomLog.tuacy("主线程在执行任务");

		try {
			CustomLog.tuacy("task运行结果" + futureTask.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		CustomLog.tuacy("所有任务执行完毕");
	}

	private void testCompletionService() {
		//使用阻塞容器保存每次Executor处理的结果，在后面进行统一处理
		completionService1();

		//使用CompletionService(完成服务)保持Executor处理的结果
		completionService2();
	}

	private void completionService1() {
		ExecutorService executor = Executors.newCachedThreadPool();
		BlockingQueue<Future<Integer>> queue = new LinkedBlockingQueue<Future<Integer>>();
		for (int i = 0; i < 10; i++) {
			FutureTask<Integer> futureTask = new FutureTask<Integer>(getTask());
			executor.submit(futureTask);
			queue.add(futureTask);
		}

		int sum = 0;
		int queueSize = queue.size();
		for (int i = 0; i < queueSize; i++) {
			try {
				sum += queue.take().get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		CustomLog.tuacy("总数为：" + sum);
		executor.shutdown();
	}

	private void completionService2() {
		ExecutorService executor = Executors.newCachedThreadPool();
		CompletionService<Integer> execcomp = new ExecutorCompletionService<Integer>(executor);
		for (int i = 0; i < 10; i++) {
			execcomp.submit(getTask());
		}
		int sum = 0;
		for (int i = 0; i < 10; i++) {
			//检索并移除表示下一个已完成任务的 Future，如果目前不存在这样的任务，则等待。
			Future<Integer> future = null;
			try {
				future = execcomp.take();
				sum += future.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		CustomLog.tuacy("总数为：" + sum);
		executor.shutdown();
	}

	public Callable<Integer> getTask() {
		final Random rand = new Random();
		Callable<Integer> task = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				int i = rand.nextInt(10);
				int j = rand.nextInt(10);
				int sum = i * j;
				System.out.print(sum + "\t");
				return sum;
			}
		};
		return task;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_test_thread:
				testThread();
				break;
			case R.id.btn_test_runnable:
				testRunnable();
				break;
			case R.id.btn_test_future_task:
				testFutureTask();
				break;
			case R.id.btn_test_completionService:
				testCompletionService();
				break;
		}
	}

	class Task implements Callable<Integer> {

		@Override
		public Integer call() throws Exception {
			System.out.println("子线程在进行计算");
			Thread.sleep(3000);
			int sum = 0;
			for (int i = 0; i < 100; i++) {
				sum += i;
			}
			return sum;
		}
	}
}
