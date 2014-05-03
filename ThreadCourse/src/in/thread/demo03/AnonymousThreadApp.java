package in.thread.demo03;

public class AnonymousThreadApp {
	public static void main(String[] args) {
		Thread t = new Thread() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					System.out.println("Hello" + i);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		};
		t.start();		
		
	}
}
