package in.thread.demo14;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Runner {
	private Account account1 = new Account();
	private Account account2 = new Account();
	private Lock lock1 = new ReentrantLock();
	private Lock lock2 = new ReentrantLock();

	private void acquireLocks(Lock firstLock, Lock secondLock) {
		boolean gotFirstLock = false;
		boolean gotSecondLock = true;
		while (true) {
			gotFirstLock = firstLock.tryLock();
			gotSecondLock = secondLock.tryLock();
			if (gotFirstLock && gotSecondLock)
				return;
			else {
				if (gotFirstLock)
					firstLock.unlock();
				else if (gotSecondLock)
					secondLock.unlock();
			}
		}

	}

	public void firstThread() {
		Random random = new Random();
		for (int i = 0; i < 10000; i++) {
			try {
				acquireLocks(lock1, lock2);
				Account.transfer(account1, account2, random.nextInt(100));
			} finally {
				lock1.unlock();
				lock2.unlock();
			}

		}
	}

	public void secondThread() {
		Random random = new Random();
		for (int i = 0; i < 10000; i++) {
			try {
				acquireLocks(lock2, lock1);
				Account.transfer(account2, account1, random.nextInt(100));
			} finally {
				lock2.unlock();
				lock1.unlock();
			}

		}
	}

	public void finished() {
		System.out.println("Account 1:" + account1.getBalance());
		System.out.println("Account 2:" + account2.getBalance());
		System.out.println("Total Balance"
				+ (account1.getBalance() + account2.getBalance()));
	}

}

public class DeadlockPrevention {
	public static void main(String[] args) {
		new DeadlockPrevention().runApp();
	}

	private void runApp() {
		final Runner runner = new Runner();
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				runner.firstThread();
			}
		});

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				runner.secondThread();
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

		runner.finished();
	}

}
