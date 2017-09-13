package com.ZhiYe.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.junit.Test;

import com.ZhiYe.dataUtil.summary.nlpir.CLibrary;
import com.ZhiYe.dataUtil.summary.nlpir.CLibraryDS;
import com.ZhiYe.entity.Question;
import com.ZhiYe.entity.WordsCons;
import com.opensymphony.xwork2.ActionContext;

public class ShowWordsCloudAction implements SessionAware {

	
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
	
	
	
	
	public String wordsCloudJson(){
		
		
		this.dataMap = new HashMap<String, Object>();
		Question questionPage = (Question) this.sessionMap.get("questionPage");
		
		String questionId = questionPage.getQuestionId();
		
		String filePath = this.theRoot()+questionId+"//zhihuBefore.csv";
		
		List<WordsCons> list = new ArrayList<WordsCons>();
		
		
		File file = new File(filePath);
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(file),"GBK");
			
			BufferedReader bufferedReader = new BufferedReader(reader);
			
			
			
			
			String lineText = "";
			
			boolean flag = true;
			
			String content = "";
			
			
			
			while((lineText = bufferedReader.readLine())!=null){
				
				if(flag){
					
					flag = false;
					continue;
					
				}
				
				String[] texts = lineText.split(",");
				
				
				if(texts.length<7)continue;
				
				content = content + texts[6];
				
				
					
					
					
				
				
			}
			
			bufferedReader.close();
			reader.close();
			
			
			String argu = "";
			String system_charset = "UTF-8";
			int charset_type = 1;

			try {
				if (!CLibrary.Instance.NLPIR_Init(argu.getBytes(system_charset),
						charset_type, "0".getBytes(system_charset))) {
					System.err.println("初始化失败！");
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			String nativeByte = CLibrary.Instance.NLPIR_GetKeyWords(content,40,true);
			
			nativeByte.substring(0, nativeByte.length()-1);
			String[] words =nativeByte.split("#");
			
			
			for(String item:words){
				
				String[] word = item.split("/");
				
				list.add(new WordsCons(Double.parseDouble(word[2]), word[0]));
				
				
			}
			
			
			dataMap.put("words", list);
			
			
			

			
			

			
			
			
			
			
			
			
			
			
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
	public void testClouds(){
		
		File file = new File("C:\\Users\\asus\\Desktop\\zhihuAfter.csv");
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(file),"GBK");
			
			BufferedReader bufferedReader = new BufferedReader(reader);
			
			
			
			
			String lineText = "";
			
			boolean flag = true;
			
			String content = "";
			
			
			
			while((lineText = bufferedReader.readLine())!=null){
				
				if(flag){
					
					flag = false;
					continue;
					
				}
				
				String[] texts = lineText.split(",");
				
				
				if(texts.length<7)continue;
				
				content = content + texts[6];
				
				
					
					
					
				
				
			}
			
			System.out.println(content);

			String argu = "";
			String system_charset = "UTF-8";
			int charset_type = 1;

			try {
				if (!CLibrary.Instance.NLPIR_Init(argu.getBytes(system_charset),
						charset_type, "0".getBytes(system_charset))) {
					System.err.println("初始化失败！");
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			String nativeByte = CLibrary.Instance.NLPIR_GetKeyWords(content,40,true);
			
			
			System.out.println(nativeByte);
			
			
			
			
			bufferedReader.close();
			reader.close();
			
			
			

			
			
			
			
			
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		
	}
	
	
	
	
}
