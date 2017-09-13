package com.ZhiYe.dataUtil.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class GetEdgeAndPoint {

	
	public void getEP(String uri , String filePath){
		
		GetWriter edge = new GetWriter();
		edge.getNewOut(filePath+"zhihuEdge.csv");
		
		GetWriter point = new GetWriter();
		point.getNewOut(filePath+"zhihuPoint.csv");
		
		try {
			edge.getWriter().write("Source,Target\r\n");
			point.getWriter().write("Id,Label,UpNum,TextLength\r\n");
			point.getWriter().write("1,问题,800,100\r\n");
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		File file = new File(uri);
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(file),"GBK");
			BufferedReader bufferedReader = new BufferedReader(reader);
			String lineText = "";
			
			boolean flag = true;
			
			while((lineText = bufferedReader.readLine())!=null){
				
				if(flag){
					
					flag= false;
					continue;
				}
				
				
				String[] list = lineText.split(",");
				
				int senLength =  0;
				
				if(list.length == 7)senLength = list[6].length();                                       
				
				
				edge.getWriter().write(list[0]+","+list[1]+"\r\n");
				point.getWriter().write(list[1]+","+list[2]+","+list[4]+","+senLength+"\r\n");
				
				
				
				
				
				
			}
			
			
			
			edge.closeOut();
			point.closeOut();
			
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
