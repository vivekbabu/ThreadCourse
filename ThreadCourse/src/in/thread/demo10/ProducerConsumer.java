package in.thread.demo10;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

class Producer implements Runnable {
	BlockingQueue<Integer> queue;

	public Producer(BlockingQueue<Integer> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(new Random().nextInt(2)*1000);
				Integer integer = new Random().nextInt(10);
				System.out.println(integer + "produced"+ ". Size is now "
						+ queue.size());
				queue.put(integer);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

class Consumer implements Runnable {
	BlockingQueue<Integer> queue;

	public Consumer(BlockingQueue<Integer> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(new Random().nextInt(2)*1000);
				Integer integer = queue.take();
				System.out.println(integer + "consumed" + ". Size is now "
						+ queue.size());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		}
		

}

public class ProducerConsumer {
	public static void main(String[] args) {
		new ProducerConsumer().runApp();
	}

	private void runApp() {
		BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(10);
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		executorService.submit(new Consumer(queue));
		executorService.submit(new Producer(queue));
	}

}
