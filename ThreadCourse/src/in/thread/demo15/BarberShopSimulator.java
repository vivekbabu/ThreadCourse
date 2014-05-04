package in.thread.demo15;

import java.util.Random;
import java.util.concurrent.Semaphore;

class BarberShop {
	
	Semaphore barberWaitingRoomSemaphore;
	Semaphore barberSemaphor = new Semaphore(1, true);
	int waitingRoomCapacity = 0;
	public BarberShop(int waitingRoomCapacity) {
		barberWaitingRoomSemaphore = new Semaphore(waitingRoomCapacity,true);
		this.waitingRoomCapacity = waitingRoomCapacity;
	}
	
	
	public void enterTheQueue(int customerId) throws InterruptedException {
		System.out.println(customerId + " entered the queue");
	}
	
	public void enterTheRoom(int customerId) throws InterruptedException {
		barberWaitingRoomSemaphore.acquire();
		System.out.println(customerId + " entered the waitingRoom.");
	}
	
	
	public void haveHairCut(int customerId) throws InterruptedException {
		barberSemaphor.acquire();
		barberWaitingRoomSemaphore.release();
		System.out.println(customerId + " entered the barber room");
		System.out.println(customerId + " having a hair cut");
		Thread.sleep(new Random().nextInt(3)*1000);
		System.out.println(customerId + " finished having cut");
		barberSemaphor.release();
	}
	
	public void customerHaveAHairCut(int customerId) throws InterruptedException {
		enterTheQueue(customerId);
		enterTheRoom(customerId);
		haveHairCut(customerId);
	}
	
	
	
}
public class BarberShopSimulator {
	
		public static void main(String[] args) throws InterruptedException {
			new BarberShopSimulator().runApp();
		}

		private void runApp() throws InterruptedException {
			final BarberShop barberShop = new BarberShop(10);
			for(int i = 0; i < 100; i++) {
				final int threadId = i;
				Thread thread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							barberShop.customerHaveAHairCut(threadId);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}						
					}
				});
				thread.start();
				Thread.sleep(500);
			}
		}
	
}
