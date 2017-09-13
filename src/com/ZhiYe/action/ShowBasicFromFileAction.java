package com.ZhiYe.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.junit.Test;

import com.ZhiYe.entity.Question;
import com.opensymphony.xwork2.ActionContext;

public class ShowBasicFromFileAction implements RequestAware {

	private Map<String ,Object> requestMap = null;
	
	
	@Override
	public void setRequest(Map<String, Object> arg0) {

		this.requestMap = arg0;
	}
	
	
	
	
	
	public String virtualQuestion() throws IOException{
		
		List<Question> list = new ArrayList<Question>();
		String[] ids = {"20106496","23743302","41263158"};
		
		for(int i = 0;i<ids.length;i++){
			
			String filePath = this.theRoot()+ids[i]+"\\basic.txt";
			File file = new File(filePath);
			Question question = new Question(file);
			
			list.add(question);
			
			
		}
		
		requestMap.put("questionList", list);
		
		return "success";
		
		
	}
	
	public String theRoot(){
		
		ActionContext actionContext = ActionContext.getContext();
		
		ServletContext servletContext = (ServletContext) actionContext.get(ServletActionContext.SERVLET_CONTEXT);
		
		return servletContext.getRealPath("/");
		
		
		
		
		
		
	}


	@Test
	public void testSave() throws IOException{
		
		String[] ids = {"20106496","23743302","41263158","24096619","36349460","58836898","58897929"};
		String root = "D:\\eclipse-j2ee\\eclipse-jee-kepler-SR2-win32-x86_64\\项目\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0"
				+ "\\wtpwebapps\\ZhiYe\\";
				
		for(int i = 0;i<ids.length;i++){
			
			File file = new File(root+ids[i]+"\\basic.txt");
			Question question = new Question(file);
			System.out.println(question);
			
		}
		
		
		
		
		
		
		
		
	}
	
	
	
}
