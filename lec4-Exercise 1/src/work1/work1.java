package work1;

import java.lang.Thread;
//Create a Java Thread that prints numbers from n1 to n2 and then stop.
class PrintsNumbers extends Thread{
	int n1,n2;
	public PrintsNumbers(int n1,int n2) {
		this.n1=n1;
		this.n2=n2;
	}
	public void run() {
		for(int i=n1;i<n2;i++) {
			System.out.println(i);
		}
	}
	
}

//Create a main program to create 4 threads and pass different numbers to them then make sure all threads finish before the main thread prints “done”
public class work1 {

	public static void main(String[] args) throws InterruptedException {
		PrintsNumbers [] pN =new PrintsNumbers[4];
		for(int i=0;i<4;i++) {
			pN[i]=new PrintsNumbers(i,i*10+9);
			pN[i].start();
		}
		
		for(int i=0;i<4;i++) {
			pN[i].join();
		}
		/*
		 * Change the number of threads to 8 rather than 4.
		 
		PrintsNumbers [] pN =new PrintsNumbers[8];
		for(int i=0;i<8;i++) {
			pN[i]=new PrintsNumbers(i,i*10+9);
			pN[i].start();
		}
		
		for(int i=0;i<8;i++) {
			pN[i].join();
		}
		
		*/
		
		
}
}
