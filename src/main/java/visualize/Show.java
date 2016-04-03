package visualize;

import java.util.Queue;

public class Show extends Thread{
	private int a =0;
	Queue<Double>[] sync_k;
	public Show(Queue[] sync){
		sync_k = sync;
	}
	@Override
	public void run(){
		while(true){
			try{
				Thread.sleep(10);
			}catch(Exception e){
				e.printStackTrace();
			}
			synchronized(sync_k){
				if(!sync_k[0].isEmpty()){
//					System.out.println("get kal data");
//					System.out.println((++a)+" "+sync_k[0].remove()+" "+sync_k[1].remove());
				}
			}
		}
	}
}
