package in.thread.demo7;

import java.util.ArrayList;
import java.util.List;

public class MultipleObjectSynchronization {
	private List<Double> list1 = new ArrayList<Double>();
	private List<Double> list2 = new ArrayList<Double>();
	private Object lock1 = new Object();
	private Object lock2 = new Object();

	public void stageOne() {
		synchronized (lock1) {
			try {
				Thread.sleep(1);
				list1.add(Math.random());
			} catch (InterruptedException e) {
				e.printStackTrace();

			}
		}

	}

	public void stageTwo() {
		synchronized (lock2) {
			try {
				Thread.sleep(1);
				list2.add(Math.random());
			} catch (InterruptedException e) {
				e.printStackTrace();

			}
		}

	}

	public void process() {
		for (int i = 0; i < 1000; i++) {
			stageOne();
			stageTwo();
		}

	}

	public static void main(String[] args) {
		new MultipleObjectSynchronization().startProcessing();
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
