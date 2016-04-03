package filter;

import java.util.Queue;
import java.util.Vector;
import sensor.Accelerometer;
import filter.Matrices;
import org.ejml.simple.SimpleMatrix;

public class Kalman extends Thread {
	private double[] cal;
	private double[] curAcc;
	private Matrices mat;
	private Queue<Double>[] sync_a;
	private Queue<Double>[] sync_k;
	
	public Kalman (Matrices mat, double[] cal, Queue[] sa, Queue[] sk) {
		this.mat = mat;
		this.cal = cal;
		curAcc = new double[2];
		sync_a = sa;
		sync_k = sk;
	}
	
	@Override
    public void run() {
		while(true){
			synchronized(sync_a){
				if(!sync_a[0].isEmpty()){
//					System.out.println("get acc data");
					curAcc[0] = sync_a[0].remove();
					curAcc[1] = sync_a[1].remove();
					prediction();
					correction();
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
		// X = AX + Bu 	= X
		mat.sP(mat.gP().plus(mat.gQ())); 	// P = AP + Q 	= P + Q
//		System.out.println("P "+mat.gP().toString());
	}
	public void correction(){
		mat.sK(mat.gP().elementDiv(mat.gP().plus(mat.gR())));	// K = P / ( P + R )
		mat.gK().set(0, 1, 0);
		mat.gK().set(1, 0, 0);
//		System.out.println("K "+mat.gK().toString());
		// X = X + K ( Z - K )
		mat.sX(mat.gX().plus(mat.gK().mult(new SimpleMatrix(2,1,true,new double[]{curAcc[0],curAcc[1]}).minus(mat.gX()))));
//		System.out.println("X "+mat.gX().toString());
		mat.sP(mat.gA().minus(mat.gK()).mult(mat.gP()));		// P = ( A - K ) * P
//		System.out.println("P "+mat.gP().toString());
	}
}
