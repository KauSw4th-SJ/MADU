package filter;

import java.util.Queue;
import filter.Matrices;
import filter.Integral;
import org.ejml.simple.SimpleMatrix;

public class Kalman extends Thread {
	private double[] curAcc;
	private double[] sd_x, sd_y;
	private Matrices mat;
	private Queue<Double>[] sync_a;
	private Queue<Double>[] sync_k;
	private SM sm;
	private double[] sd;
	Integral vel[], disp[];
	
	public Kalman (Matrices mat, Queue[] sa, Queue[] sk, int sdCount, double[] sd, double sec) {
		this.mat = mat;
		curAcc = new double[2];
		sd_x = new double[sdCount];
		sd_y = new double[sdCount];
		sync_a = sa;
		sync_k = sk;
		sm = new SM(sdCount);
		this.sd = sd;
		/* integrated values of velocity and displacement */
		vel = new Integral[2];
		disp = new Integral[2];
		vel[0]= new Integral(sec);
		vel[1]= new Integral(sec);
		disp[0] = new Integral(sec);
		disp[1] = new Integral(sec);
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
		
		mat.sK(mat.gP().elementDiv(mat.gP().plus(mat.gR())));	// K = P / ( P + R )
		
		mat.gK().set(0, 1, 0);		// compensate NA -> 0
		mat.gK().set(1, 0, 0);		// compensate NA -> 0
//		System.out.println("K "+mat.gK().toString());
// 		X = X + K ( Z - K )
		mat.sX(mat.gX().plus(mat.gK().mult(new SimpleMatrix(2,1,true,new double[]{curAcc[0],curAcc[1]}).minus(mat.gX()))));
		
		if(sm.sd(sd_x, sdc) <sd[0]){
			mat.gX().set(0, 0, 0);
			vel[0].setZ();
			disp[0].setZ();
		}
		if(sm.sd(sd_y, sdc) <sd[1]){
			mat.gX().set(1, 0, 0);
			vel[1].setZ();
			disp[1].setZ();
		}
		vel[0].accumulate(mat.gX().get(0, 0));
		disp[0].accumulate(vel[0].getAccum());
		vel[1].accumulate(mat.gX().get(1, 0));
		disp[1].accumulate(vel[1].getAccum());
		
//		System.out.println(mat.gX().get(1, 0));
//		System.out.println(vel[1].getAccum());
//		System.out.println(disp[1].getAccum());
		
//		System.out.println("X "+mat.gX().toString());
		mat.sP(mat.gAmK().mult(mat.gP()));		// P = ( A - K ) * P
//		System.out.println("P "+mat.gP().toString());
	}
}
