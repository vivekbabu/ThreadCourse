package in.vmw.answer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

class FileReaderThread extends Thread {
	Semaphore semaphore = new Semaphore(0);
	List<FileReaderThread> threads;
	private int threadId;
	private int noOfThreads;
	private int times = 0;
	public FileReaderThread(List<FileReaderThread> threads, int threadId, int noOfThreads) {
		this.threads = threads;
		this.threadId =  threadId;
		this.noOfThreads = noOfThreads;
	}

	@Override
	public void run() {
		while (true) {
			try {
				semaphore.acquire();
				System.out.println("Hello world from thread " + threadId);
				Thread.sleep(new Random().nextInt(2) * 1000);
				times++;
				if(times == 10) {
					for(FileReaderThread thread : threads) 
						thread.cancel();
				}
				threads.get((threadId + 1)% noOfThreads).semaphore.release();
			} catch (InterruptedException e) {
				System.out.println("Bye bye from thread " + threadId);
				break;
			}
		}
	}
	
	public void cancel() { 
		interrupt();
	}

}

public class FileReaderThreads {
	
	public static void main(String[] args) throws InterruptedException {
		List<FileReaderThread> threads = new ArrayList<FileReaderThread>();

		try {
			FileInputStream fileInputStream = new FileInputStream(new File("D:\\vivek.txt"));
			FileOutputStream fileOutputStream = new FileOutputStream(new File("D:\\vivek.txt"));	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		for(int i = 0; i< 10; i++) {
			FileReaderThread fileReaderThread = new FileReaderThread(threads, i, 10);
			threads.add(fileReaderThread);
			fileReaderThread.start();
		}
		System.out.println("All threads submitted");
		Thread.sleep(4000);
		System.out.println("Releasing first thread");
		threads.get(0).semaphore.release();
		
	}

}
