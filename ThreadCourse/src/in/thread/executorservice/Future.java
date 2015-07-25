package in.thread.executorservice;

import java.util.concurrent.Semaphore;

public class Future<T extends Object> {
	
	private Semaphore semaphore;
	T result;
	
	public void setSemaphore(Semaphore semaphore) {
		this.semaphore = semaphore;
	}
	
	public Semaphore getSemaphore() {
		return semaphore;
	}
	
	void setResult(T result) {
		this.result = result;
	}
	
	public T get() throws InterruptedException {
		try {
			semaphore.acquire();
			return result;
		} catch (InterruptedException e) {
			throw e;
		}
		
	}

}
