package SW4th;

import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import org.ejml.simple.SimpleMatrix;
import au.com.bytecode.opencsv.CSVReader;

import filter.Kalman;

public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	/* read c.s.v file and get x, y axis acceleration vectors */
    	CSVReader reader = new CSVReader(new FileReader("src/resources/PSH_Accelerometer.csv"));
		String[] nextLine;
		Vector<Double> acc_x = new Vector<Double>(550);
		Vector<Double> acc_y = new Vector<Double>(550);
		Vector<Double>[] recVec;
		while ((nextLine = reader.readNext()) != null) {
			acc_x.addElement(Double.parseDouble(nextLine[3]));
			acc_y.addElement(Double.parseDouble(nextLine[4]));
		}
		int nrow = acc_x.size();
		
		/* initialize some params */
		/* construct a filter instance and start filtering */
		SimpleMatrix P, Q, R;
		
		Kalman kalman = new Kalman();
		recVec = kalman.filtering();
		
    	
    	/* rendering several graphs on JFrame */
    }
}
