package visualize;

import java.awt.Dimension;
import java.util.Queue;
import javax.swing.JFrame;
import visualize.CsvWriter;

public class Show extends Thread{
	private Queue<Double>[] sync_k;
	public Show(Queue[] sync){
		sync_k = sync;
	}
	@Override
	public void run(){
		/* JFrame이 생성되는 위치 */
//		Graph mainPanel = new Graph();
//		JFrame frame = new JFrame("DrawGraph");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	    frame.getContentPane().add(mainPanel);	// 프레임에 패널을 넣는다.
//	    Dimension d =new Dimension(500,500);	// 프레임 사이즈 조
//	    frame.setPreferredSize(d);
//	    frame.pack();
//	    frame.setLocationByPlatform(true);
//	    frame.setVisible(true);					// 프레임을 보여줌 
		
//		String[] toks = new String[2];
	    while(true){
//			try{
//				Thread.sleep(10);
//			}catch(Exception e){
//				e.printStackTrace();
//			}
			synchronized(sync_k){
				if(!sync_k[0].isEmpty()){
//					toks[0] = String.valueOf(sync_k[0].remove());
//					toks[1] = String.valueOf(sync_k[1].remove());
//					System.out.println(toks[1]);
					/* 안 되면 어쩔 수 없지만 이 쪽에서 받은 데이터 값으로 계속 그래프를 수정하고 싶어 */
				}
			}
		}
	    
	}
}
