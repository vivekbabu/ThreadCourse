package in.thread.oddevenprinter;

public class OddEvenPrinter extends Thread {

private int value;
private Object lock;

OddEvenPrinter(int startValue, Object lock) {
  this.value = startValue;
  this.lock = lock;
}

@Override
public void run() {
  while (true) {
    synchronized (lock) {
      System.out.print(value + " ");
      try {
        value += 2;
        Thread.sleep(100);
        lock.notify();
        lock.wait();
      } catch (InterruptedException e) {
      }

    }
  }

}

public static void main(String[] args) throws InterruptedException {
  Object lock = new Object();
  new OddEvenPrinter(1, lock).start();
  Thread.sleep(100);
  new OddEvenPrinter(2, lock).start();
}
}
