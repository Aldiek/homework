// 10 marks
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		BlockingQueue q1 = new BlockingQueue(100); // 1/2 marks
		BlockingQueue q2 = new BlockingQueue(100);

		FirstThread fg = new FirstThread(q1);
		Thread th1 = new Thread(fg);

		SecondThread th2[] = new SecondThread[8]; // 1/2 marks

		ThirdThread TT = new ThirdThread(q2);
		Thread th3 = new Thread(TT); // 1/2 marks




		th1.start();
			for(int i=0;i<8;i++) {
				 th2[i] = new SecondThread(q1,q2);
				 th2[i].start();


			}











		th3.start();



		th1.join();

		for(int i=0;i<8;i++) { // 1/2 marks

			 th2[i].join();


		}



		th3.join();

		System.out.println("THE END");

	}

}

class BlockingQueue {
	private List queue;
	private Integer limit;
	private int counter=0;
	public synchronized int count() {
		counter++;

		return counter;

	}


	public BlockingQueue(Integer limit) {
		this.limit = limit;
		queue = new LinkedList();
	}

	public synchronized Boolean isEmpty() {
		return this.queue.size() == 0;
	}

	public synchronized void add(Object o) throws InterruptedException {
		while (this.queue.size() == this.limit) {
			wait();
		}
		if (this.queue.size() == 0) {
			notifyAll();
		}
		this.queue.add(o);
	}

	public synchronized Object pop() throws InterruptedException {
		while (this.queue.size() == 0) {
			wait();
		}
		if (this.queue.size() == this.limit) {
			notifyAll();
		}

		return this.queue.remove(0);
	}

}

class FirstThread extends Thread {
	BlockingQueue q1; // 1/2 marks

	public FirstThread(BlockingQueue q) { // 1/2 marks
		this.q1 = q;
	}

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) { // 1/2 marks
			try {
				q1.add("f" + i + ".txt"); // 1/2 marks
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}

class SecondThread extends Thread {
	BlockingQueue q1;
	BlockingQueue q2; // 1/2 marks
	private String rawDataFromFile;
	private String processedData;
	private String filename;

	public SecondThread(BlockingQueue q1, BlockingQueue q2) { // 1/2 marks
		this.q1 = q1;
		this.q2 = q2;
	}

	@Override
	public void run() {
		while(q2.count()<=100) { // 2 marks
			try {
				filename = q1.pop().toString(); // 1/2 marks
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				rawDataFromFile = FileUtils.readFileAsString("data\\" + filename);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			processedData = filename + FileUtils.countchars(rawDataFromFile);
			try {
				q2.add(processedData); // 1/2 marks
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}

class ThirdThread extends Thread {
	BlockingQueue q2; // 1/2 marks
	private String processedData;

	public ThirdThread(BlockingQueue q2) { // 1/2 marks
		this.q2 = q2;
	}

	@Override
	public void run() {
		for (int i = 0; i < 100;i++) { // 1/2 marks
			try {
				processedData = (String) q2.pop(); // 1/2 marks
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				FileUtils.appendStringToFile("processedData.txt", processedData);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}

class FileUtils {

	public static String readFileAsString(String name) throws IOException {
		return new String(Files.readAllBytes(Paths.get(name)));
	}

	public static void appendStringToFile(String name, String line) throws IOException {
		File file = new File(name);
		FileWriter fw = new FileWriter(file, true);
		fw.write(line + "\r\n");
		fw.close();
	}

	public static String countchars(String s) {
		int numL = 0;
		int numD = 0;
		int rest = 0;
		char[] ch = s.toCharArray();
		for (char c : ch) {
			if (Character.isLetter(c)) {
				numL++;
			} else if (Character.isDigit(c)) {
				numD++;
			} else
				rest++;
		}
		String p = String.format(numL + "ofleters" + numD + "ofdigits" + rest + "rest");
		return p;

	}

}
