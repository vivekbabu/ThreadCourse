package in.thread.demo06;

import java.util.ArrayList;
import java.util.List;

public class SynchronizedObject {
	private List<Double> list1 = new ArrayList<Double>();
	private List<Double> list2 = new ArrayList<Double>();

	public synchronized void stageOne() {
		try {
			Thread.sleep(1);
			list1.add(Math.random());
		} catch (InterruptedException e) {
			e.printStackTrace();

		}

	}

	public synchronized void stageTwo() {
		try {
			Thread.sleep(1);
			list2.add(Math.random());
		} catch (InterruptedException e) {
			e.printStackTrace();

		}
	}

	public void process() {
		for (int i = 0; i < 1000; i++) {
			stageOne();
			stageTwo();
		}

	}

	public static void main(String[] args) {
		new SynchronizedObject().startProcessing();
	}

	private void startProcessing() {
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				process();
			}
		});

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				process();
			}
		});
		long start = System.currentTimeMillis();
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long stop = System.currentTimeMillis();
		System.out.println("Total time taken=" + (stop - start));
		System.out.println("list1=" + list1.size() + " list2=" + list2.size());
	}
}
