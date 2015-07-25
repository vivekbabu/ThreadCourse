package in.thread.executorservice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class ExecutorService {
	int threadPoolSize = 0;
	@SuppressWarnings("rawtypes")
	BlockingQueue<CallableRecord> queue = new LinkedBlockingQueue<CallableRecord>();
	List<Thread> threads = new ArrayList<Thread>();

	Semaphore safeToShutDownSemaphore;
	private boolean takeMoreJobs = true;

	public ExecutorService(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
		safeToShutDownSemaphore = new Semaphore(threadPoolSize);
		initializeThreadPool(threadPoolSize);

	}

	private void initializeThreadPool(int threadPoolSize) {
		for (int i = 0; i < threadPoolSize; i++) {
			Thread thread = new Thread() {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public void run() {
					while (true) {
						CallableRecord callableRecord = null;
						try {
							callableRecord = queue.take();
							safeToShutDownSemaphore.acquire();
						} catch (InterruptedException e) {
							// e.printStackTrace();
							System.out.println("Shutting down thread");
							break;
						}
						Object call = null;
						try {
							call = callableRecord.getCallable().call();
						} catch (Exception e) {
							e.printStackTrace();
						}
						callableRecord.getFuture().setResult(call);
						callableRecord.getFuture().getSemaphore().release();
						safeToShutDownSemaphore.release();
					}

				};
			};
			threads.add(thread);
			thread.start();
		}
	}

	public <T> Future<T> submit(Callable<T> task) throws Exception {
		if (takeMoreJobs) {
			Semaphore semaphore = new Semaphore(0);
			CallableRecord<T> callableRecord = new CallableRecord<T>();
			callableRecord.setCallable(task);

			Future<T> future = new Future<T>();
			future.setSemaphore(semaphore);

			callableRecord.setFuture(future);

			queue.offer(callableRecord);
			return future;
		}

		else {
			throw new Exception(
					"Service already shutdown. No more jobs will be taken. Already submitted jobs will be completed");
		}

	}

	public void shutdown() {
		takeMoreJobs = false;
		Thread thread = new Thread() {
			public void run() {

				while (true) {
					try {
						safeToShutDownSemaphore.acquire(threadPoolSize);
						if (queue.isEmpty()) {
							break;
						} else {
							safeToShutDownSemaphore.release(threadPoolSize);
							Thread.sleep(10000);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				for (Thread thread : threads) {
					thread.interrupt();
				}
				System.out.println("Exector service shutdown");
			};
		};
		thread.start();
		System.out.println("Exector service shutdown called");
	}
}
