package work2;

import java.lang.Thread;

/*
 * 
 * Create a counter class with a synchronized add method.
 * Create a thread class that calls add method of its counter attribute.
 * Create two threads with the same counter object.
 * Create two threads with two different counter objects.
 * 
 */




class Counter {
	int count;
	public synchronized void add() {
		count++;
	}
}

class ThreadClass extends Thread{
	Counter c=new Counter();
	public ThreadClass (Counter c) {
		this.c=c;
		
	}
	
	public void run() {
		for (int i=0;i<1000;i++)
		c.add();
	}
	
	
}

class ThreadClass2 extends Thread{
	Counter c=new Counter();
	public ThreadClass2 (Counter c) {
		this.c=c;
		
	}
	
	public void run() {
		
		for (int i=0;i<1000;i++)
		c.add();
	}
	
	
}

public class work2 {

	public static void main(String[] args) throws InterruptedException {
		Counter c=new Counter();
		ThreadClass tC=new ThreadClass(c);
		ThreadClass2 tC2=new ThreadClass2(c);
		tC.start();
		tC2.start();
		tC.join();
		tC2.join();
		System.out.println(c.count);
		
}
}
