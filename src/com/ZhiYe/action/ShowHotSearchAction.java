package com.ZhiYe.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;

import com.ZhiYe.entity.Question;
import com.opensymphony.xwork2.ActionContext;

public class ShowHotSearchAction implements RequestAware {

	private Map<String ,Object> requestMap = null;
	
	
	@Override
	public void setRequest(Map<String, Object> arg0) {

		this.requestMap = arg0;
	}
	
	
	
	
	
	public String virtualQuestion() throws IOException{
		
		List<Question> list = new ArrayList<Question>();
		String[] ids = {"20106496","23743302","41263158","24096619","36349460","58836898","58897929"};
		String[] searchNum = {"8","8","7","5","2","1","1"};
		
		
		for(int i = 0;i<ids.length;i++){
			
			String filePath = this.theRoot()+ids[i]+"\\basic.txt";
			File file = new File(filePath);
			Question question = new Question(file);
			question.setQuestionDes(searchNum[i]);
			
			
			
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
	
	
	
}
