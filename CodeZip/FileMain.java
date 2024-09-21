import java.util.Date;
import javax.swing.*; 

class FileClock implements Runnable {
	JTextField jtf;
	public FileClock(JTextField jtf){
		this.jtf = jtf;
	}
	
  @Override
  public void run() {
      for (int i = 0; i < 10; i++) {
      System.out.printf("%s\n", new Date());
      Date d1 = new Date();
      jtf.setText(d1.toString());
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        System.out.printf("The FileClock has been interrupted");
      }
    }
  }
 }
  
public class FileMain {
  public static void main(String[] args) {
  	JTextField jtf = new JTextField();
      FileClock clock1=new FileClock(jtf);
      FileClock clock2=new FileClock(jtf);
    Thread thread1=new Thread(clock1);
    thread1.start();
    Thread thread2=new Thread(clock2);
    thread2.start();
    
    NewThread nt1 = new NewThread("One");
    NewThread nt2 = new NewThread("Two");
    NewThread nt3 = new NewThread("Three");

    // Start the threads.
    nt1.t.start(); 
    nt2.t.start(); 
    nt3.t.start(); 
    
    
        try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    };
    thread1.interrupt();
    thread2.interrupt();
}
}
          