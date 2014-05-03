package in.thread.demo4;

import java.util.Scanner;

class Runner extends Thread {
	private volatile boolean alive = true;

	@Override
	public void run() {
		System.out.println("Press any key to stop");
		while (alive) {
			System.out.println("Hello");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void shutdown() {
		alive = false;
	}

}

public class ThreadStopper {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Runner runner = new Runner();
		runner.start();
		new Scanner(System.in).nextLine();
		runner.shutdown();
		
	}

}
