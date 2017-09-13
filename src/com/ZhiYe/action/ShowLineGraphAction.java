package com.ZhiYe.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;


import com.ZhiYe.entity.Question;
import com.opensymphony.xwork2.ActionContext;

public class ShowLineGraphAction implements SessionAware {

	private Map<String , Object> sessionMap = null;

	@Override
	public void setSession(Map<String, Object> arg0) {

		this.sessionMap =arg0;
		
	}
	
	
	private Map<String,Object> dataMap;

	public Map<String, Object> getDataMap() {
		return dataMap;
	}


	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}
	
	
	
	
	public String lineGraphJson(){
		
		
		this.dataMap = new HashMap<String, Object>();
		Question questionPage = (Question) this.sessionMap.get("questionPage");
		
		String questionId = questionPage.getQuestionId();
		
		String filePath = this.theRoot()+questionId+"//zhihuBefore.csv";
		
		File file = new File(filePath);
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(file),"GBK");
			
			BufferedReader bufferedReader = new BufferedReader(reader);
			
			
			
			
			String lineText = "";
			List<Long> list = new ArrayList<Long>();
			
			
			
			long max =0;
			long min = 2000000000;
			boolean flag = true;
			while((lineText = bufferedReader.readLine())!=null){
				
				if(flag){
					
					flag = false;
					continue;
					
				}
				
				
				long num = Long.parseLong(lineText.split(",")[3]);
				
				
				if(num > max)max = num;
				if(num < min )min = num;
				
				
				list.add(num);
			}
			
			
			int arrayNum = (int) ((max-min)/3600)+1;
//			
//			System.out.println(max);
//			System.out.println(min);
//			
//			System.out.println(arrayNum);
			int[] data = new int[arrayNum];
			String[] date = new String[arrayNum];
			
			
			for(long i : list){
				
				data[(int) ((i-min)/3600)]++;
				
				
				
				
			}
			

			long begin = (min/3600)*3600*1000;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			for(int i = 0;i<arrayNum;i++){
				
				
				
				date[i] = sdf.format(begin);
				begin+=3600000;
				
				
			}
			
			
			bufferedReader.close();
			reader.close();
			
			
			
			this.dataMap.put("date", date);
			this.dataMap.put("data", data);
			
			
			
			
			
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		return "success";
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	public String theRoot(){
		
		ActionContext actionContext = ActionContext.getContext();
		
		ServletContext servletContext = (ServletContext) actionContext.get(ServletActionContext.SERVLET_CONTEXT);
		
		return servletContext.getRealPath("/");
		
		
		
		
		
		
	}
	
	
	
	
}
