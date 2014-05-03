package in.thread.demo09;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Runner implements Runnable {
	
	private CountDownLatch latch;
	private long id;
	public Runner(CountDownLatch latch, long id) {
		this.latch = latch;
		this.id = id;
		
	}
	@Override
	public void run() {
		System.out.println("Job started " + id);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Job completed " + id);
		latch.countDown();
	}
	
}

public class CountDownLatches {
	public static void main(String[] args) {
		new CountDownLatches().runApp();
	}

	private void runApp() {
		CountDownLatch latch = new CountDownLatch(5);
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 5; i++) {
			executorService.submit(new Runner(latch, i));
		}
		System.out.println("All jobs submitted");
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("All jobs completed");
	}

}
