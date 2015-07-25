package in.thread.executorservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class ExecutorServiceClient {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = new ExecutorService(2);
		List<Future<Long>> futures = new ArrayList<>();
		final Random random = new Random();
		for (long i = 1; i <= 20; i++) {
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
			Future<Long> future;
			try {
				future = executorService.submit(callable);
				futures.add(future);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}

		executorService.shutdown();

		for (int i = 0; i < 10; i++) {
			Thread.sleep(2000);
			try {
				executorService.submit(new Callable<String>() {
					@Override
					public String call() throws Exception {
						System.out.println("Run");
						return "";
					}
				});
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		for (int i = 1; i <= 20; i++) {
			try {
				Long value = futures.get(i - 1).get();
				System.out.println(value + " returned to client by job " + i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("All jobs done");

	}
}
