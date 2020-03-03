package com.yun.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskExecutorUtils {

	private final static TaskExecutorUtils instance = new TaskExecutorUtils();
	
	private int executorLimit = 1;

	private final AtomicInteger counter = new AtomicInteger();
	
	private int maxItemId = 0;

	private ExecutorService executorService;

	private TaskExecutorUtils() {

	}

	public void init() {
		System.out.println("时间调度级初始化, 执行线程数为executorLimit=" + this.executorLimit);
		this.executorService = Executors.newFixedThreadPool(this.executorLimit);
	}
	

	public int getTaskCount() {
		return counter.get();
	}

	class MyTask implements Runnable {
		private final Runnable task;

		public MyTask(Runnable task) {
			this.task = task;
			counter.incrementAndGet();
		}

		@Override
		public void run() {
			long t1 = System.currentTimeMillis();
			try {
				this.task.run();
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
			counter.decrementAndGet();
			long t2 = System.currentTimeMillis() - t1;
			System.out.println("TaskExecutorUtils: 任务执行时长:" + t2 + "ms");
		}
	}

	public void shutdown() {
		System.out.println("TaskExecutorUtils begin shutdown, 剩余任务数量:"
				+ this.counter.get());

		this.executorService.shutdown();
		try {
			// 5分钟如果不能停止就强行停止
			if (!this.executorService.awaitTermination(10, TimeUnit.MINUTES)) {
				this.executorService.shutdownNow();
				if (!this.executorService.awaitTermination(1, TimeUnit.MINUTES)) {
					System.out.println("TaskExecutorUtils shutdown 失败");
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			this.executorService.shutdownNow();
			Thread.currentThread().interrupt();
		}
		System.out.println("TaskExecutorUtils shutdown complete");
	}

	public void executeTask(Runnable task) {
		System.out.println("executeTask(). task=" + task.toString());
		this.executorService.execute(new MyTask(task));
	}

	public void setExecutorLimit(int executorLimit) {
		this.executorLimit = executorLimit;
	}

	public static TaskExecutorUtils getInstance() {
		return instance;
	}

}