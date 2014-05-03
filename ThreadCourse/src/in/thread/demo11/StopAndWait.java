package in.thread.demo11;

import java.util.Scanner;

class Runner {
	public  void produce() throws InterruptedException {
		synchronized (this) {
			System.out.println("Producer Started");
			System.out.println("Producer waiting");
			wait();
			System.out.println("Producer resumed");
		}
	}
	
	public  void consume() throws InterruptedException {
		synchronized (this) {
			System.out.println("Consumer Started");
			Thread.sleep(3000);
			System.out.println("Press any key");
			new Scanner(System.in).nextLine();
			System.out.println("Consumer stopped");
			notify();
			Thread.sleep(6000);
			
		}
	}
	
}
public class StopAndWait {
	public static void main(String[] args) {
		new StopAndWait().runApp();
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
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		
		System.out.println("System exitted");
	}

}
