package in.thread.executorservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class ExecutorServiceClient {
	public static void main(String[] args) {
		ExecutorService executorService = new ExecutorService(2);
		List<Future<Long>> futures = new ArrayList<>();
		final Random random = new Random();
		for (long i = 1; i <= 5; i++) {
			CallableWithID<Long> callable = new CallableWithID<Long>(i) {

				@Override
				public Long call() throws Exception {
					System.out.println("Started job " + getId());
					Thread.sleep(random.nextInt(10) * 1000);
					System.out.println("Ended job " + getId());
					return getId();
				}
			};
			System.out.println("Submitted job " + i);
			Future<Long> future = executorService.submit(callable);
			futures.add(future);
		}
		
		for (int i = 1; i <= 5; i++) {
			try {
				Long value = futures.get(i-1).get();
				System.out.println(value + " returned to client by job " + i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("All jobs done");
		executorService.shutdown();
		System.out.println("Executor Service Shutdown");
	}
}
