package com.ZhiYe.dataUtil.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class GetGml {

	public void writeGml(String filePath , String edgeUri , String pointUri){
		
		File edgeFile = new File(edgeUri);
		File pointFie = new File(pointUri);
		
		
		File gml = new File(filePath+"zhihuGml.gml");
		
		try {
			InputStreamReader readerEdge= new InputStreamReader(new FileInputStream(edgeFile),"GBK");
			InputStreamReader readerPoint = new InputStreamReader(new FileInputStream(pointFie),"GBK");
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(gml,true),"GBK");
			
			
			
			BufferedReader bufferEdge= new BufferedReader(readerEdge);
			BufferedReader bufferPoint = new BufferedReader(readerPoint);
			
			BufferedWriter bufferGml = new BufferedWriter(writer);
			
			
			String lineText = "";
			writer.write("graph\r\n[\r\ndirected 1\r\n");
			
			
			
			boolean flag1 = true;
			while((lineText = bufferPoint.readLine())!= null){
				
				if(flag1){
					
					flag1 = false;
					continue;
					
					
				}
				
				
				String[] list = lineText.split(",");
				writer.write("node\r\n[\r\nid \""+list[0]+"\"\r\nlabel \""+list[1]+"\"\r\nvote-number "+list[2]+"\r\ntextLength "+list[3]+"\r\n]\r\n");
				
				
				//link \""+getLink(list, answerId)+"\"\r\n
				
				
				
				
			}
			
			boolean flag2 = true;
			while((lineText = bufferEdge.readLine())!= null){
				
				if(flag2){
					
					flag2 = false;
					continue;
					
					
				}
				
				
				String[] list = lineText.split(",");
				writer.write("edge\r\n[\r\nsource \""+list[0]+"\"\r\ntarget \""+list[1]+"\"\r\n]\r\n");
				
				
				
				
				
				
				
			}
			
			
			writer.write("]");
			
			readerEdge.close();
			readerPoint.close();
			writer.close();
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		
		
		
		
		
		
		
		
		
	}
	
	public String getLink(String[] in ,String questionId ){
		
		if(Integer.parseInt(in[0])==1){
			
			return "<a href=\"https://www.zhihu.com/question/"+questionId+"\"  target=_blank>"+"点击查看</a>";
			
		}else{
			
			return "<a href=\"https://www.zhihu.com/question/"+questionId+"/answer/"+in[0]+"\"  target=_blank>"+"点击查看</a>";
			
			
		}
		
		
		
		
	}
	
	
	
}
