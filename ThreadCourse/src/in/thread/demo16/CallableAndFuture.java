package in.thread.demo16;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableAndFuture {
	
	public static void main(String[] args) throws InterruptedException {
		new CallableAndFuture().runApp();
	}

	private void runApp() throws InterruptedException {
		ExecutorService service = Executors.newSingleThreadExecutor();
		Future<Integer> submit = service.submit(new Callable<Integer>()  {

			@Override
			public Integer call() throws Exception {
				Random random = new Random();
				Integer value = random.nextInt(4000);
				System.out.println("Sleeping for " +value+" milliseconds");
				try {
					Thread.sleep(value);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Awake");
				if(value < 3000)
					throw new IllegalArgumentException("Nice try dude");
				else
					return value;
			}
			
			
		});
		
		service.shutdown();
		try {
			System.out.println("The thread slept for" + submit.get()+" milliseconds");
		} catch (ExecutionException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Program exited");
	}
}
