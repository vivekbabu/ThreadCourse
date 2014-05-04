package in.thread.demo12;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Runner {
	private List<Integer> integers = new ArrayList<Integer>();
	private int count = 0;
	private int MAX_COUNT = 10;

	public void produce() throws InterruptedException {
		while (true) {
			synchronized (this) {
				if (integers.size() == MAX_COUNT)
					wait();
				else {
					integers.add(count++);
					System.out.println(count - 1 + " added.Size is now "
							+ integers.size());

					notifyAll();
				}

			}
			Thread.sleep(new Random().nextInt(3) * 1000);

		}
	}

	public void consume() throws InterruptedException {
		while (true) {
			synchronized (this) {
				if (integers.isEmpty())
					wait();
				else {
					System.out.println(integers.remove(0)
							+ " removed.Size is now " + integers.size());
					notifyAll();
				}

			}
			Thread.sleep(new Random().nextInt(3) * 1000);
		}
	}

}

public class ProducerConsumerWithStopWait {
	public static void main(String[] args) {
		new ProducerConsumerWithStopWait().runApp();
	}

	private void runApp() {
		final Runner runner = new Runner();
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					runner.produce();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					runner.consume();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		Thread t3 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					runner.consume();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		t1.start();
		t2.start();
		t3.start();
		

	}
}
