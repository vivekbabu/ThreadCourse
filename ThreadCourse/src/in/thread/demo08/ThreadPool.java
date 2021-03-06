package in.thread.demo08;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Runner implements Runnable {
	private int id;

	public Runner(int id) {
		this.id = id;
	}

	@Override
	public void run() {
		System.out.println(id + " Started");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(id + " Completed");
	}

}

public class ThreadPool {
	public static void main(String[] args) {
		new ThreadPool().runTheApp();
	}

	private void runTheApp() {
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		for (int i = 0; i < 5; i++) {
			System.out.println(i + " submitted");
			executorService.submit(new Runner(i));
		}
		System.out.println("All Tasks Submitted");
		executorService.shutdown();
		
		try {
			executorService.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("All Tasks Completed");
	}

}
