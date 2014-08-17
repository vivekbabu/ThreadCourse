package in.apg.answer;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

class ThreadToRun implements Runnable {
	private int i;
	private CountDownLatch startLatch;
	private CountDownLatch endLatch;
	private CountDownLatch releaserLatch;

	public ThreadToRun(int i, CountDownLatch startLatch, CountDownLatch endLatch,CountDownLatch releaserLatch) {
		this.i = i;
		this.startLatch = startLatch;
		this.endLatch = endLatch;
		this.releaserLatch = releaserLatch;
	}

	@Override
	public void run() {
		try {
			System.out.println("Thread " + i + " started");
			Thread.sleep(Math.abs(new Random().nextInt(5) * 1000));
			releaserLatch.countDown();
			startLatch.await();
			Thread.sleep(Math.abs(new Random().nextInt(5) * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Thread " + i + " finished");
			endLatch.countDown();
		}

	}

}

public class LoadTester {
	private static final int MAX_THREADS = 10;
	private static final CountDownLatch startLatch = new CountDownLatch(1);
	private static final CountDownLatch endLatch = new CountDownLatch(
			MAX_THREADS);
	private static final CountDownLatch releaserLatch = new CountDownLatch(
			MAX_THREADS);

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < MAX_THREADS; i++) {
			Thread t = new Thread(new ThreadToRun(i, startLatch, endLatch,releaserLatch));
			t.start();
		}

		long startTimeMillis = System.currentTimeMillis();
		System.out.println("Waiting for all threads to start");
		releaserLatch.await();
		System.out.println("Releasing all threads");
		startLatch.countDown();
		try {
			System.out.println("Waiting for all threads to end");
			endLatch.await();
			System.out.println("All threads finished");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long endTimeMillis = System.currentTimeMillis();
		System.out.println("Total time taken  :  "
				+ (endTimeMillis - startTimeMillis));

	}
}
