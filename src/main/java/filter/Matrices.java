package filter;

import org.ejml.simple.SimpleMatrix;

public class Matrices {
	private SimpleMatrix Q, R, P;
	private SimpleMatrix X, K;
	
	public Matrices(double pe, double oe, double no){
		P = new SimpleMatrix(2,2,true,new double[]{pe,0,0,pe});
		R = new SimpleMatrix(2,2,true,new double[]{oe,0,0,oe});
		Q = new SimpleMatrix(2,2,true,new double[]{no,0,0,no});
		X = new SimpleMatrix(2,1);
		K = new SimpleMatrix(2,2);
	}
	public void setM(){
		
	}
}
