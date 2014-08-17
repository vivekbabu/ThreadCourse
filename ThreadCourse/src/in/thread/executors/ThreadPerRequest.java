package in.thread.executors;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadPerRequest {
	
	public static void main(String[] args) throws IOException {
		ServerSocket socket = new ServerSocket(80);
		System.out.println("Waiting for requests");
		final Socket connection = socket.accept();
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					handleConnection(connection);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				
			}

			private void handleConnection(Socket connection) throws InterruptedException {
				System.out.println("Handling the connection in a seperate thread");
				Thread.sleep(3000);
			}
		});
		t.start();	
	}
}
