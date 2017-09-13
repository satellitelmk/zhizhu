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
import org.junit.Test;

import com.ZhiYe.entity.Answer;
import com.ZhiYe.entity.Question;
import com.opensymphony.xwork2.ActionContext;

public class ShowEachPgeAction implements SessionAware{

	private Integer page;
	private Map<String ,Object > dataMap;
	
	
	
	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}



	private Map<String,Object> sessionMap = null;
	
	@Override
	public void setSession(Map<String, Object> arg0) {

		this.sessionMap  = arg0;
	}

	public String showPage(){
		
		Question question  = (Question)this.sessionMap.get("questionPage");
		String questionId = question.getQuestionId();
		this.dataMap = new HashMap<String, Object>();
		List<Answer> answerlist = new ArrayList<Answer>();
		
		String filePath = theRoot()+questionId+"\\zhihuSummary.csv";
		File file = new File(filePath);
		
		
		InputStreamReader reader;
		int num = 0;
		try {
			reader = new InputStreamReader(new FileInputStream(file),"GBK");
			BufferedReader bufferedReader = new BufferedReader(reader);
			
			String lineText = "";
			
			
			
			int begin = 30*(page-1)+1;
			int end = 30*page;
			
			
			
			while((lineText = bufferedReader.readLine())!=null){
				
				if(num == 0){
					num ++;
					continue;
					
				}
				
				String[] texts = lineText.split(",");
				
				
				if(texts[8].equals("true")){
					
					if(begin<=num && num<=end ){
					
					Answer answer = new Answer();
					answer.setAnswerId(texts[0]);
					
					answer.setAuthor(texts[1]);
					answer.setSummary(texts[3]);
					
					answer.setCommentCount(texts[6]);
					answer.setUpCount(texts[5]);
					answer.setKeywords(texts[2].substring(0, texts[2].length()-1));
					
					Date date = new Date(Long.parseLong(texts[7])*1000);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					String dateStr = sdf.format(date);
					answer.setTime(dateStr);
					
					
					
					int length = texts[4].length();
					texts[4].substring(0, length-1);
					
					
					String[] emotions = texts[4].split("/");
					
					String[] emotionText = {"Happy","Good","Anger","Sorrow","Fear","Evil","Suprise"};
					String result = "";
					
					
					double sum = 0;
					for(int i = 0;i<7;i++){
						
						sum+=Integer.parseInt(emotions[i]);
						
					}
					
					for(int i = 0 ;i<7; i++){
						
						int temp = Integer.parseInt(emotions[i]);
						int tempNum = (int)((temp/sum)*100+0.5);
						if(tempNum != 0){
							
							result+=emotionText[i]+":"+tempNum+"%; ";
							
						}
						
					}
					
					answer.setSentiments(result);
					
					answerlist.add(answer);
					
					
					
					
					}

					num++;
					
				}else{
					
					num--;
					break;
					
					
				}
				
				
			}
			
			reader.close();
			bufferedReader.close();
			
			
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		this.dataMap.put("pages",1+ num/30);
		this.dataMap.put("list", answerlist);
		
		
		return "success";
		
		
		
	}
	
	public String theRoot(){
		
		ActionContext actionContext = ActionContext.getContext();
		
		ServletContext servletContext = (ServletContext) actionContext.get(ServletActionContext.SERVLET_CONTEXT);
		
		return servletContext.getRealPath("/");
		
		
		
		
		
		
	}
	
	
	@Test
	public void testTime(){
		
		Date date = new Date(Long.parseLong("1456451824")*1000);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String dateStr = sdf.format(date);
		System.out.println(dateStr);
		
		
	}
	
	
	
	
	
	
	
	
	
}
