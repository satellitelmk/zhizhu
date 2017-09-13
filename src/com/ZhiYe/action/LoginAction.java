package com.ZhiYe.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.ZhiYe.base.ClientBase;
import com.ZhiYe.util.DefaultCookieStores;

public class LoginAction {

	private String jsessionId;
	
	private CookieStore cookieStore;
	
	private CloseableHttpClient httpClient;

	



	
	
	
	public String getJessionId() {
		
		
		return jsessionId;
	}

	public void setJessionId(String jessionId) {
		this.jsessionId = jessionId;
	}

	public CookieStore getCookieStore() {
		return cookieStore;
	}

	public void setCookieStore(CookieStore cookieStore) {
		this.cookieStore = cookieStore;
	}


	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	};
	
	
	
	public  void showXsrf(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		for(javax.servlet.http.Cookie c: request.getCookies()){
			
			if(c.getName().equals("JSESSIONID")){
				
				this.jsessionId = c.getValue();
				
			}
		}
			
		
		
		
		CookieStore storeInThis = DefaultCookieStores.getCookieStoreByJessionId(jsessionId);
		
		if(storeInThis == null){
			
			this.cookieStore = new BasicCookieStore();
			this.httpClient = ClientBase.getClient(cookieStore);
			
			DefaultCookieStores.saveCookieStore(jsessionId, cookieStore);			
			
			
		}else{
			
			this.cookieStore = storeInThis;
			this.httpClient = ClientBase.getClient(storeInThis);
		}
			
			
		
		HttpGet getHomePage = new HttpGet("http://www.zhihu.com/");
		CloseableHttpResponse res = null;
		
		try {
			
			
			
			res = httpClient.execute(getHomePage);
			
			String responseHtml = EntityUtils.toString(res.getEntity());
			
			String xsrfValue = responseHtml
					.split("<input type=\"hidden\" name=\"_xsrf\" value=\"")[1].split("\"/>")[0];
			
			res.close();
			
			
			HttpServletResponse response = ServletActionContext.getResponse();      
	        response.setCharacterEncoding("UTF-8");      
	        response.getWriter().write(xsrfValue);    
			
	        
	        res.close();
	        httpClient.close();
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
			
		
		
	}
	
	public void showCapcha(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		for(javax.servlet.http.Cookie c: request.getCookies()){
			
			if(c.getName().equals("JSESSIONID")){
				
				this.jsessionId = c.getValue();
				
			}
		}
			
		
		
		
		CookieStore storeInThis = DefaultCookieStores.getCookieStoreByJessionId(jsessionId);
		
		if(storeInThis == null){
			
			this.cookieStore = new BasicCookieStore();
			this.httpClient = ClientBase.getClient(cookieStore);
			
			DefaultCookieStores.saveCookieStore(jsessionId, cookieStore);			
			
			
		}else{
			
			this.cookieStore = storeInThis;
			
			
			
			this.httpClient = ClientBase.getClient(storeInThis);
		}
		
		
		
		HttpServletResponse response = null;
		ServletOutputStream out = null;
		
		
		HttpGet getCaptcha = new HttpGet("https://www.zhihu.com/captcha.gif?r=" + System.currentTimeMillis() + "&type=login");
		
		try {
        	
        	response = ServletActionContext.getResponse();
        	response.setContentType("multipart/form-data");
        	
        	out = response.getOutputStream();
        	
			CloseableHttpResponse imageResponse = httpClient.execute(getCaptcha);
			
			byte[] bytes = new byte[8192];
			int len;
			while((len = imageResponse.getEntity().getContent().read(bytes))!=-1){
				
				out.write(bytes, 0, len);
				
			}
			
			
			out.flush();
			imageResponse.close();
			
		     httpClient.close();
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
        
        
		
	}
	
	
	
	
	
}
