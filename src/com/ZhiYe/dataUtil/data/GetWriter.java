package com.ZhiYe.dataUtil.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;


public class GetWriter {

	private OutputStreamWriter writer ;
	private BufferedWriter bufferedWriter;
	public OutputStreamWriter getWriter() {
		return writer;
	}
	public void setWriter(OutputStreamWriter writer) {
		this.writer = writer;
	}
	public BufferedWriter getBufferedWriter() {
		return bufferedWriter;
	}
	public void setBufferedWriter(BufferedWriter bufferedWriter) {
		this.bufferedWriter = bufferedWriter;
	}
	
	
	public OutputStreamWriter getNewOut(String uri){
		
		File file = new File(uri);
		
		OutputStreamWriter out = null;
		try {
			out = new OutputStreamWriter(new FileOutputStream(file,true),"GBK");
			BufferedWriter bufferWriter = new BufferedWriter(out);		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		this.writer= out;
		this.bufferedWriter  =bufferedWriter;
		return out;
		
		
	}
	
	
	public void closeOut(){
		
		try {
			this.writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
