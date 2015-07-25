package in.vmw.answer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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
private FileInputStream fileInputStream;
private FileOutputStream fileOutputStream;

public FileReaderThread(List<FileReaderThread> threads, int threadId,
    int noOfThreads, FileInputStream fileInputStream,
    FileOutputStream fileOutputStream) {
  this.threads = threads;
  this.threadId = threadId;
  this.noOfThreads = noOfThreads;
  this.fileInputStream = fileInputStream;
  this.fileOutputStream = fileOutputStream;
}

@Override
public void run() {
  while (true) {
    try {
      semaphore.acquire();
      char current;
      System.out.println();
      System.out.print("Thread " + threadId + " wrote ");
      while (fileInputStream.available() > 0) {
        current = (char) fileInputStream.read();
        fileOutputStream.write(current);
        System.out.print(current);
        if (current == ' ')
          break;

      }
      if (!(fileInputStream.available() > 0)) {
        fileInputStream.close();
        fileOutputStream.close();
        for (FileReaderThread thread : threads)
          thread.cancel();
      }
      threads.get((threadId + 1) % noOfThreads).semaphore.release();
    } catch (InterruptedException e) {
      System.out.println("Bye bye from thread " + threadId);
      break;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

public void cancel() {
  interrupt();
}

}

public class FileReaderThreads {

public static void main(String[] args) throws InterruptedException, IOException {
  List<FileReaderThread> threads = new ArrayList<FileReaderThread>();

  FileInputStream fileInputStream = new FileInputStream(new File(
      "D:\\vivek.txt"));

  File outputFile = new File("D:\\vivek1.txt");
  outputFile.createNewFile();
  FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

  for (int i = 0; i < 10; i++) {
    FileReaderThread fileReaderThread = new FileReaderThread(threads, i, 10,
        fileInputStream, fileOutputStream);
    threads.add(fileReaderThread);
    fileReaderThread.start();
  }
  System.out.println("All threads submitted");
  Thread.sleep(4000);
  System.out.println("Releasing first thread");
  threads.get(0).semaphore.release();

}

}
