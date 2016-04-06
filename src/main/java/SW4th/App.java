package SW4th;

import sensor.Accelerometer;
import visualize.Show;
import java.util.LinkedList;
import java.util.Queue;

import org.ejml.simple.SimpleMatrix;

import filter.*;
import org.ejml.simple.SimpleMatrix;

public class App 
{
    public static void main( String[] args ){
    	/* shared variables */
    	double[] sd;
    	sd = new double[2];
    	Queue<Double>[] acc_data, kal_data;
    	acc_data = new Queue[2];
    	kal_data = new Queue[2];
    	acc_data[0] = new LinkedList<Double>();
    	acc_data[1] = new LinkedList<Double>();
    	kal_data[0] = new LinkedList<Double>();
    	kal_data[1] = new LinkedList<Double>();
    	
    	/* generate instances */
    	Matrices mat = new Matrices(1, 24, 0.0625);
    	Accelerometer acc = new Accelerometer(acc_data, sd);
    	Kalman kalman = new Kalman(mat, acc_data, kal_data, 5, sd, 0.05);
		Show show = new Show(kal_data);
		
		acc.start();
    	kalman.start();
    	show.start();
    	
    	/* rendering several graphs on JFrame */
    }
}
