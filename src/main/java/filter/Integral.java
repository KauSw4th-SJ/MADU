package filter;

public class Integral {
	public double accum, sec, post;
	public Integral(double sec){
		accum=0;
		post = 0;
		this.sec = sec;
	}
	public void setZ(){
		accum=0;
		post=0;
	}
	public double accumulate(double input){
		accum += sec*(post+input)/2;
		post = input;
		return accum;
	}
	public double getAccum(){
		return accum;
	}
}
