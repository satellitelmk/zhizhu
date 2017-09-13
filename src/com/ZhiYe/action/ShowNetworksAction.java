package com.ZhiYe.action;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.CookieStore;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.junit.Test;

import com.ZhiYe.dataUtil.data.DataMineTask;
import com.ZhiYe.dataUtil.summary.nlpir.SummaryTask;
import com.ZhiYe.entity.Question;
import com.ZhiYe.util.DefaultCookieStores;
import com.opensymphony.xwork2.ActionContext;

public class ShowNetworksAction implements SessionAware{

	private Map<String , Object > sessionMap = null;
	
	
	@Override
	public void setSession(Map<String, Object> arg0) {

		this.sessionMap = arg0;
	}

	
	public String showNetwork(){
		
		Question questionPage = (Question) this.sessionMap.get("questionPage");
		
		if(questionPage == null  )return "error";
		
		String questionId = questionPage.getQuestionId();
		Integer answerNum = questionPage.getAnswerNum();
		
		if(answerNum<100) return "refuse";
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String jsessionId = null;
		
		
		for(javax.servlet.http.Cookie c: request.getCookies()){
			
			if(c.getName().equals("JSESSIONID")){
				
				jsessionId = c.getValue();
				
			}
		}
			
		CookieStore storeInThis = DefaultCookieStores.getCookieStoreByJessionId(jsessionId);
		
		String theRoot = this.theRoot();
		
		File file = new File(theRoot+questionId);
		
		if(file.exists()){
			
			
//			deleteDir(file);
//			new File(theRoot+questionId).mkdirs();
			return "success";
			
		}else{
			
			file.mkdirs();
			
		}
		
		String filePath = theRoot+questionId+"\\";
		
		
		
		
		
		
		
		new DataMineTask().beginTask(filePath, storeInThis, questionId, answerNum);
		new SummaryTask().beginSummary(filePath);
		
		
		try {
			questionPage.writeBasicToFile(theRoot+questionId+"\\basic.txt");
		} catch (IOException e) {
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
	
	

	    /**
	     * 递归删除目录下的所有文件及子目录下所有文件
	     * @param dir 将要删除的文件目录
	     * @return boolean Returns "true" if all deletions were successful.
	     *                 If a deletion fails, the method stops attempting to
	     *                 delete and returns "false".
	     */
	   public  boolean deleteDir(File dir) {
	        if (dir.isDirectory()) {
	            String[] children = dir.list();
	            //递归删除目录中的子目录下
	            for (int i=0; i<children.length; i++) {
	                boolean success = deleteDir(new File(dir, children[i]));
	                if (!success) {
	                    return false;
	                }
	            }
	        }
	        // 目录此时为空，可以删除
	        return dir.delete();
	    }
	
	
	   @Test
	   public void testDelete(){
		   
		   String path = "C:\\Users\\asus\\Desktop\\12";
		   File file = new File(path);
			if(file.exists()){
				
				
				deleteDir(file);
				
			}else{
				
				file.mkdirs();
				System.out.println("fail");
			}
		   
		   
	   }
	   
	   
	   
	
	
}
