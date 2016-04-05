package filter;

import java.util.Queue;
import java.util.Vector;
import sensor.Accelerometer;
import filter.Matrices;
import org.ejml.simple.SimpleMatrix;

public class Kalman extends Thread {
	private double[] curAcc;
	private double[] sd_x, sd_y;
	private Matrices mat;
	private Queue<Double>[] sync_a;
	private Queue<Double>[] sync_k;
	private SM sm;
	private double[] sd;
	
	public Kalman (Matrices mat, Queue[] sa, Queue[] sk, int sdCount, double[] sd) {
		this.mat = mat;
		curAcc = new double[2];
		sd_x = new double[sdCount];
		sd_y = new double[sdCount];
		sync_a = sa;
		sync_k = sk;
		sm = new SM(sdCount);
		this.sd = sd;
	}
	@Override
    public void run() {
		int sdc = 0;
		while(true){
			synchronized(sync_a){
				if(!sync_a[0].isEmpty()){
//					System.out.println("get acc data");
					sd_x[sdc%5] = curAcc[0] = sync_a[0].remove();
					sd_y[sdc%5] = curAcc[1] = sync_a[1].remove();
					sdc++;
					prediction();
					correction(sdc);
					synchronized(sync_k){
//						System.out.println("set kal data");
						sync_k[0].offer(mat.gX().get(0, 0));
						sync_k[1].offer(mat.gX().get(1, 0));
					}
				} 
			}
		}
	}
	
	public void prediction(){
		// A = I and there are nothing to control  
		// X = AX + Bu 	= X
		mat.sP(mat.gP().plus(mat.gQ())); 	// P = AP + Q = P + Q
		
//		System.out.println("P "+mat.gP().toString());
	}
	public void correction(int sdc){
		
		double sds[] = new double[2];
		mat.sK(mat.gP().elementDiv(mat.gP().plus(mat.gR())));	// K = P / ( P + R )
		
		mat.gK().set(0, 1, 0);		// compensate NA -> 0
		mat.gK().set(1, 0, 0);		// compensate NA -> 0
//		System.out.println("K "+mat.gK().toString());
// 		X = X + K ( Z - K )
		mat.sX(mat.gX().plus(mat.gK().mult(new SimpleMatrix(2,1,true,new double[]{curAcc[0],curAcc[1]}).minus(mat.gX()))));
		sds[0] = sm.sd(sd_x, sdc);
		sds[1] = sm.sd(sd_y, sdc);
		if(sds[0] <sd[0]) mat.gX().set(0, 0, 0); 
		if(sds[1] <sd[1]) mat.gX().set(1, 0, 0);
//		System.out.println("X "+mat.gX().toString());
		mat.sP(mat.gAmK().mult(mat.gP()));		// P = ( A - K ) * P
//		System.out.println("P "+mat.gP().toString());
	}
}
