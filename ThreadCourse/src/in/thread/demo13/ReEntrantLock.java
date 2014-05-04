package in.thread.demo13;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Runner {
	private ReentrantLock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	private int count = 0;

	public void increment() {
		for (int i = 0; i < 10000; i++) {
			count++;

		}
	}

	public void firstThread() throws InterruptedException {
		System.out.println("First Thread Started");
		lock.lock();
		System.out.println("First Thread gave away the lock");
		condition.await();
		System.out.println("First Thread Resumed");
		try {
			increment();
		} finally {
			lock.unlock();
		}

	}

	public void secondThread() throws InterruptedException {
		Thread.sleep(2000);
		lock.lock();
		System.out.println("Press any key to continue");
		new Scanner(System.in).nextLine();
		System.out.println("Key pressed");
		System.out.println("Signalled");
		condition.signal();
		Thread.sleep(3000);
		try {
			increment();
		} finally {
			lock.unlock();
		}

	}

	public void finalPrint() {
		System.out.println("Count is " + count);
	}
}

public class ReEntrantLock {
	public static void main(String[] args) {
		new ReEntrantLock().runApp();
	}

	private void runApp() {
		final Runner runner = new Runner();
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					runner.firstThread();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					runner.secondThread();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		runner.finalPrint();
	}
}
