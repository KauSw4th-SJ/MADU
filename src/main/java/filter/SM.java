package filter;

// 오해하지 마셈 이건 stationary & moving 의 준말
public class SM {
	private int sdc;
	public SM(int sdc){
		this.sdc = sdc;
	}
	public double sd(double[] arr, int n){
		double mean=0;
		double sd = 0;
		int len;
		//할당할 곳 = 비교문? 참일때값 : 거짓일때 값
		len = (n>sdc) ? sdc : n;
		for(int i=0;i<len;i++) mean += arr[i];
		mean /= len;
		for(int i=0;i<len;i++) sd += (arr[i]-mean)*(arr[i]-mean);
		sd = Math.sqrt(sd/len);
		return sd;
	}
}
