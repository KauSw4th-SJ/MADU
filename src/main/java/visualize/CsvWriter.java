package visualize;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import au.com.bytecode.opencsv.CSVWriter;

public class CsvWriter {
	private CSVWriter cw;
	private String dir;
	
	public CsvWriter(String dir){
		try{
			cw = new CSVWriter(new OutputStreamWriter(new FileOutputStream(dir), "EUC-KR"),',', '"');
		}catch(Exception e){
			e.printStackTrace();
		}
		this.dir = dir;
	}
	public void write(String toks[]){
		try{
			cw.writeNext(toks);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void terminate(){
		try{
			cw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
