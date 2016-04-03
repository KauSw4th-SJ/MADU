package SW4th;

import sensor.Accelerometer;
import visualize.Show;
import java.util.LinkedList;
import java.util.Queue;

import org.ejml.simple.SimpleMatrix;

import filter.*;


public class App 
{
    public static void main( String[] args ){
    	/* shared variables */
    	Queue<Double>[] acc_data, kal_data;
    	acc_data = new Queue[2];
    	kal_data = new Queue[2];
    	acc_data[0] = new LinkedList<Double>();
    	acc_data[1] = new LinkedList<Double>();
    	kal_data[0] = new LinkedList<Double>();
    	kal_data[1] = new LinkedList<Double>();
    	/* generate instances */
    	Matrices mat = new Matrices(0.8, 0.05, 0.005);
    	Accelerometer acc = new Accelerometer(acc_data);
    	Kalman kalman = new Kalman(mat, acc.getCalData(), acc_data, kal_data);
		Show show = new Show(kal_data);
		
		acc.start();
    	kalman.start();
    	show.start();
    	/* rendering several graphs on JFrame */
    }
}
