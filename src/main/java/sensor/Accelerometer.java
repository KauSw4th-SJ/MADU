package sensor;

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import au.com.bytecode.opencsv.CSVReader;

public class Accelerometer extends Thread{
	// field variables 
	double[] sd;
	private double[] acc_val;
	private double[] mean_val;
	private Queue<Double>[] sync;
	/* < temporary code */
	private Queue<Double>[] acc;
	private Queue<Double>[] cal;
	/* temporary code > */
	private int lineC;
	
	// constructor
	public Accelerometer(Queue[] aq, double[] sd){ 
		acc_val = new double[2];
		mean_val = new double[2];
		sync = aq;
		this.sd = sd;
		lineC = 0;
		/* < temporary code */
		try{
			readCSV();
			calibration();
		} catch (IOException e){
			System.out.println(e + " == by Acc constructor ==");
		}
		/* temporary code > */
	}
	// thread run method
	@Override
    public void run() {
		while(true){
			try{
				Thread.sleep(10);
			}catch(Exception e){
				e.printStackTrace();
			}
			synchronized(sync){
				if(sensing()){
					sync[0].offer(acc_val[0]);
					sync[1].offer(acc_val[1]);
				}
			}
		}
	}
	// get single sensor data
	public boolean sensing(){
		/* < temporary code */
		if(acc[0].peek() != null){
			acc_val[0] = acc[0].remove() - mean_val[0]; 
			acc_val[1] = acc[1].remove() - mean_val[1];
			return true;
		} else return false;
		/* temporary code > */
	}
	// get calibration data
	/* < temporary code */
	public void readCSV()  throws IOException {
		/* read c.s.v file and get x, y axis acceleration vectors */
		CSVReader reader = new CSVReader(new FileReader("src/resources/PSH_Accelerometer_raw.csv"));
		String[] nextLine;
		acc = new Queue[2];
		acc[0] = new LinkedList<Double>();
		acc[1] = new LinkedList<Double>();
		while ((nextLine = reader.readNext()) != null) {
			acc[0].offer(Double.parseDouble(nextLine[3]));
			acc[1].offer(Double.parseDouble(nextLine[4]));
		}
		
	} 
	public void calibration() throws IOException{
		// declare locals and initialize
		CSVReader reader = new CSVReader(new FileReader("src/resources/PSH_Accelerometer_cal.csv"));
		String[] nextLine;
		int idx;
		double d1, d2, v1, v2;
		cal = new Queue[2];
		cal[0] = new LinkedList<Double>();
		cal[1] = new LinkedList<Double>();
		
		mean_val[0] = mean_val[1] = 0;
		// get mean values
		for (idx=0 ; (nextLine = reader.readNext()) != null; idx++) {
			d1 = Double.parseDouble(nextLine[3]);
			d2 = Double.parseDouble(nextLine[4]);
			cal[0].offer(d1);
			cal[1].offer(d2);
			mean_val[0] += d1;
			mean_val[1] += d2;
		}
		mean_val[0] /= idx;
		mean_val[1] /= idx;
		// get standard deviation values
		v1 = v2 = 0;
		for(int i=0;i<idx; i++){
			d1 = cal[0].remove();
			d2 = cal[1].remove();
			v1 += (mean_val[0]-d1) * (mean_val[0]-d1);
			v2 += (mean_val[1]-d2) * (mean_val[1]-d2);
		}
		sd[0] = Math.sqrt(v1/idx);
		sd[1] = Math.sqrt(v2/idx);
	}
	/* temporary code > */
}
