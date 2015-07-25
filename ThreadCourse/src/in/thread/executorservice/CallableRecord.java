package in.thread.executorservice;

import java.util.concurrent.Callable;

public class CallableRecord<T> {
	private Callable<T> callable;
	private Future<T> future;
	
	public void setFuture(Future<T> future) {
		this.future = future;
	}
	
	public Future<T> getFuture() {
		return future;
	}
	
	public void setCallable(Callable<T> callable) {
		this.callable = callable;
	}
	
	
	public Callable<T> getCallable() {
		return callable;
	}
}
