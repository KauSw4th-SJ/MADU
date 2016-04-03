package SW4th;

import sensor.Accelerometer;
import filter.*;


public class App 
{
    public static void main( String[] args ){
    	
    	/* generate instances */
    	Accelerometer acc = new Accelerometer();
    	Matrices mat = new Matrices(0.8, 0.05, 0.005);
    	Kalman kalman = new Kalman(mat, acc.getCalData());
    	
		/* initialize some params */
		/* construct a filter instance and start filtering */
		
    	
		
    	/* rendering several graphs on JFrame */
    }
}
