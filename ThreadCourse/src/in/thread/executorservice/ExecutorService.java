package in.thread.executorservice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class ExecutorService {

	@SuppressWarnings("rawtypes")
	BlockingQueue<CallableRecord> queue = new LinkedBlockingQueue<CallableRecord>();
	List<Thread> threads = new ArrayList<Thread>();
	
	public ExecutorService(int threadPoolSize) {
		for (int i = 0; i < threadPoolSize; i++) {
			Thread thread = new Thread() {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public void run() {
					while (true) {
						CallableRecord callableRecord = null;
						try {
							callableRecord = queue.take();
						} catch (InterruptedException e) {
							//e.printStackTrace();
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
					}

				};
			};
			threads.add(thread);
			thread.start();
		}

	}

	public <T> Future<T> submit(Callable<T> task) {
		Semaphore semaphore = new Semaphore(0);
		CallableRecord<T> callableRecord = new CallableRecord<T>();
		callableRecord.setCallable(task);

		Future<T> future = new Future<T>();
		future.setSemaphore(semaphore);

		callableRecord.setFuture(future);

		queue.offer(callableRecord);

		return future;
	}
	
	public void shutdown() {
		for (Thread thread : threads) {
			thread.interrupt();
		}
	}
	
}
