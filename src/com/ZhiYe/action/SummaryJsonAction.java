package com.ZhiYe.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.catalina.tribes.util.Arrays;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.junit.Test;

import com.ZhiYe.entity.Question;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SummaryJsonAction  extends ActionSupport implements SessionAware{

	private static final long serialVersionUID = 1L;

	private Map<String, Object> dataMap = null;
	
	private String id;
	
	
	private Map<String ,Object > sessionMap = null;
	
	
	

	@Override
	public void setSession(Map<String, Object> arg0) {

		this.sessionMap = arg0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}
	
	public String sentJson(){
		
		Question questionPage = (Question) this.sessionMap.get("questionPage");
		String questionId = questionPage.getQuestionId();
		
		
		this.dataMap = new HashMap<String, Object>();
		
		String root = this.theRoot();
		String filePath = root +questionId +"\\zhihuSummary.csv";
		File file= new File(filePath);
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(file),"GBK");
			
			BufferedReader bufferedReader  = new BufferedReader(reader);
			
			String lineText = "";
			int num = 0;
			boolean flag = false;
			while((lineText = bufferedReader.readLine())!=null){
				
				if(num  > 0){
					
					String[] texts = lineText.split(",");
					if(texts[0].equals(id)){
						
						int length = texts[2].length();
						
						char last = texts[2].charAt(length-1);
						if(last == '#')texts[2].substring(0, length-1);
						
						String[] keyWords = texts[2].split("#");
						this.dataMap.put("keywords", keyWords);
						
						this.dataMap.put("summary", texts[3]);
						
						
						length = texts[4].length();
						texts[4].substring(0, length-1);
						
						
						String[] emotions = texts[4].split("/");
						
						String[] emotionText = {"Happy","Good","Anger","Sorrow","Fear","Evil","Suprise"}; 
						
						Map<String ,Object> emotionMap = new HashMap<String,Object>();
						
						double sum = 0;
						for(int i = 0;i<7;i++){
							
							sum+=Integer.parseInt(emotions[i]);
							
						}
						
						int emotionNum = 0;
						for(int i = 0 ;i<7; i++){
							
							int temp = Integer.parseInt(emotions[i]);
							double tempNum = ((int)((temp/sum)*100))/100.0;
							if(tempNum != 0.0){
								
								emotionMap.put(emotionText[i], tempNum);
								emotionNum++;
								
							}
							
						}
						 
						
						
						
						
						
						if(emotionNum != 0){
							
							this.dataMap.put("emotions", emotionMap);
							
						}else{
							
							this.dataMap.put("emotions", "noEmotion");
							
							
						}
						
						
						this.dataMap.put("msg", "success");
						
						flag = true;
						
						
					}
					
					
					
					
					
				}
				
				num++;
				
			}
			
			if(!flag){
				
				this.dataMap.put("msg", "fail");
				
			}
			
			
			
			
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
	
	@Test
	public void testspilt(){
		
		String ss= "nihao#jj";
		
		
		System.out.println(ss.substring(0,ss.length()-1));
		
		
		
	}

	
	
	
	
}
