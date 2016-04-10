package visualize.Viewer;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import visualize.FramePanel.MADUJFrame;
import visualize.FramePanel.MADUJPanel;

public class MADUGraphViewer extends Thread{
	private static final int KALMAN_DATA_MAX_SIZE = 548;
	private Queue<Double>[] sync_k;
	private MADUJFrame maduFrame;
	private MADUJPanel maduPanel;
	
	//test_jaeshim
	public MADUGraphViewer(Queue<Double>[] sync){
		this.sync_k = sync;		
	}
	
	public void InitMaduPanel(){
		maduPanel = new MADUJPanel(new ArrayList<Double>(sync_k[1]));		
	}	
	public void InitMaduFrame(){
		maduFrame = new MADUJFrame("MADU Graph");
		maduFrame.setMyPanel(maduPanel);		
		maduFrame.createNewFrame();	
	}	
	@Override
	public void run(){		
		InitMaduPanel();
		InitMaduFrame();
		while(sync_k[1].size()<KALMAN_DATA_MAX_SIZE){			
			synchronized(sync_k){
				if(!sync_k[0].isEmpty()){					
//					toks[0] = String.valueOf(sync_k[0].remove());
//					toks[1] = String.valueOf(sync_k[1].remove());
//					System.out.println(" "+sync_k[0].remove()+" "+sync_k[1].remove());  				    
					
				    maduPanel.setKalmanDataList(new ArrayList<Double>(sync_k[1]));  					
					maduPanel.repaint();
				}			
			}
		}		
		maduFrame.dispose();
	}
}
