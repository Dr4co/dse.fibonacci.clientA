package dse.fibonacciclient;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import dse.fibonacciservice.service.FibonacciService;

public class FibonacciActivator implements BundleActivator {

    ServiceReference fibonacciServiceReference;
    public static BundleContext bc = null;    
    private FibonacciConsumerThread thread = null;
    FibonacciService fibonacciService;
    
    public void start(BundleContext context) throws Exception {
        System.out.println("Fibonacci Client-A!!");
        FibonacciActivator.bc = context;
        fibonacciServiceReference= context.getServiceReference(FibonacciService.class.getName());
//        FibonacciService 
        fibonacciService =(FibonacciService)context.getService(fibonacciServiceReference);
  
//        Debug: All numbers...
//        System.out.println(fibonacciService.getFibonacci());        

        this.thread = new FibonacciConsumerThread();
        this.thread.start();
//        for(int i=0; i < 10; i++){
//        	System.out.println("Client-A: " + fibonacciService.getNextFib());
//        	sleepThread.sleep(1000);
//        }
        
//        this.stop(context);
    }
    
    public void stop(BundleContext context) throws Exception {
        System.out.println("Goodbye Fibonacci Client-A!!");
        
        this.thread.stopThread();
        this.thread.join();
//        FibonacciActivator.bc = null;        
        context.ungetService(fibonacciServiceReference);
    }
}

class FibonacciConsumerThread extends Thread {

	private boolean running = true;
	public FibonacciConsumerThread() {}
	
	public void run() {
		while (running) {

		    ServiceReference fibonacciServiceReference;
			fibonacciServiceReference = FibonacciActivator.bc.getServiceReference(FibonacciService.class.getName());
			
		    FibonacciService fibonacciService;					
			fibonacciService =(FibonacciService)FibonacciActivator.bc.getService(fibonacciServiceReference);
			
			System.out.println("Client-A: " + fibonacciService.getNextFib()); 
//			fibonacciService.getFibonacci());  
					
					
//			System.out.println("Hello World!");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				System.out.println("HelloWorldThread ERROR: " + e);
			}
		}
	}
	
	public void stopThread() {
		this.running = false;
	}

}