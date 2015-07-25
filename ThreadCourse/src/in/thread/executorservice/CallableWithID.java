package in.thread.executorservice;

import java.util.concurrent.Callable;

public abstract class CallableWithID<T> implements Callable<T>{
	private Long id;
	public CallableWithID(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

}
