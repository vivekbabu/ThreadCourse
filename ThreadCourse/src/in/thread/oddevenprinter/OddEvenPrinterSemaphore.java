package in.thread.oddevenprinter;

import java.util.concurrent.Semaphore;

public class OddEvenPrinterSemaphore extends Thread {

private int value;
private Semaphore semaphore;
private Semaphore otherSemaphore;

OddEvenPrinterSemaphore(int startValue, Semaphore semaphore,
    Semaphore otherSemaphore) {
  this.value = startValue;
  this.semaphore = semaphore;
  this.otherSemaphore = otherSemaphore;
}

@Override
public void run() {
  while (true) {

    try {
      semaphore.acquire();
      System.out.println(value + " ");
      Thread.sleep(100);
      value += 2;
      otherSemaphore.release();

    } catch (InterruptedException e) {
    }

  }
}

public static void main(String[] args) throws InterruptedException {
  Semaphore oddSemaphore = new Semaphore(0);
  Semaphore evenSemaphore = new Semaphore(0);
  new OddEvenPrinterSemaphore(1, oddSemaphore, evenSemaphore).start();
  new OddEvenPrinterSemaphore(2, evenSemaphore, oddSemaphore).start();
  oddSemaphore.release();
}
}