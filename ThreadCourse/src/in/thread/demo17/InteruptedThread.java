package in.thread.demo17;

import java.util.Random;

public class InteruptedThread {
	public static void main(String[] args) throws InterruptedException {
		new InteruptedThread().runApp();
	}

	private void runApp() throws InterruptedException {
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				Random random = new Random();
				
				for(int i =0; i< 1E8;i++) {
					if(Thread.currentThread().isInterrupted()) {
						System.out.println("Interupted");
						break;
					}
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						System.out.println("Interrupted inside sleep");
					}
					Math.sin(random.nextDouble());
				}
			}
		});
		System.out.println("Thread started");
		thread.start();
		Thread.sleep(2000);
		thread.interrupt();
		thread.join();
		System.out.println("Thread ran");
	}
}
