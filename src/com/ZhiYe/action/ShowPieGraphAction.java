package com.ZhiYe.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.ZhiYe.dataUtil.summary.nlpir.CLibraryDS;
import com.ZhiYe.dataUtil.summary.nlpir.LJSentimentAnalysisLibrary.CLibrarySentimentAnalysis;
import com.ZhiYe.entity.Question;
import com.ZhiYe.entity.PieCons;
import com.opensymphony.xwork2.ActionContext;

public class ShowPieGraphAction implements SessionAware {

	
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
	
	
	
	
	public String pieGraphJson(){
		
		
		this.dataMap = new HashMap<String, Object>();
		Question questionPage = (Question) this.sessionMap.get("questionPage");
		
		String questionId = questionPage.getQuestionId();
		
		String filePath = this.theRoot()+questionId+"//zhihuSummary.csv";
		
		File file = new File(filePath);
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(file),"GBK");
			
			BufferedReader bufferedReader = new BufferedReader(reader);
			
			
			int[] emotionWordsNum = new int[7];
			List<PieCons> list = new ArrayList<PieCons>();
			String lineText = "";
			
			boolean flag = true;
			while((lineText = bufferedReader.readLine())!=null){
				
				if(flag){
					flag = false;
					continue;
					
				}
				String[] items = lineText.split(",");
				
				if(items[8].equals("true")){
					
					
					String emotionLine =  items[4];
					emotionLine.substring(0, emotionLine.length()-1);
					
					String[] emotionPerLine = emotionLine.split("/");
					
					for(int i = 0;i<7;i++){
						
						emotionWordsNum[i]+=Integer.parseInt(emotionPerLine[i]);
					}
					
					
				}
				
			}
			


			String[] emotionText = {"Happy","Good","Anger","Sorrow","Fear","Evil","Suprise"};
			
			for(int i = 0;i<7;i++){
				
				list.add(new PieCons((emotionWordsNum[i]), emotionText[i]));
				
				
				
				
			}
			
			
			this.dataMap.put("emotions", list);
			
			
			
			
			reader.close();
			bufferedReader.close();
			
			
			
			
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








