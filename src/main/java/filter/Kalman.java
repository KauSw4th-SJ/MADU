package filter;

import java.util.Vector;
import sensor.Accelerometer;
import filter.Matrices;
import org.ejml.simple.SimpleMatrix;

public class Kalman {
	private double[] cal;
	private Matrices mat;
	private Accelerometer acc;
	private int row;
	
	public Kalman (Matrices mat, double[] cal) {
		this.mat = mat;
		this.cal = cal;
	}
	
	public void setMat(){
		
	}
	public Vector[] filtering(){
		return null;
	}
	public void initialize(){
		
	}
}
