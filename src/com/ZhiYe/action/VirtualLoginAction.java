package com.ZhiYe.action;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;

import com.ZhiYe.base.ClientBase;
import com.ZhiYe.util.DefaultCookieStores;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class VirtualLoginAction implements RequestAware{

	
	private String email;
	private String password;
	private String verify;
	private String xsrf;
	
	
	private Map<String, Object> requestMap = null;
	
	@Override
	public void setRequest(Map<String, Object> arg0) {
		
		this.requestMap = arg0;
	}
	
	
	
	
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getVerify() {
		return verify;
	}
	public void setVerify(String verify) {
		this.verify = verify;
	}
	public String getXsrf() {
		return xsrf;
	}
	public void setXsrf(String xsrf) {
		this.xsrf = xsrf;
	}
	
	
	
	public String testLogin(){
		
		String jsessionId = null;
		CookieStore cookieStore = null;
		CloseableHttpClient httpClient = null;
		String result = null;
		
		
		HttpServletRequest request = ServletActionContext.getRequest();
		for(javax.servlet.http.Cookie c: request.getCookies()){
			
			if(c.getName().equals("JSESSIONID")){
				
				jsessionId = c.getValue();
				
			}
		}
			
		
		
		
		CookieStore storeInThis = DefaultCookieStores.getCookieStoreByJessionId(jsessionId);
		
		if(storeInThis == null){
			
			result = "404";
			
			
		}else{
			
			cookieStore = storeInThis;
			httpClient = ClientBase.getClient(storeInThis);
			
			List<NameValuePair> valuePairs = new LinkedList<NameValuePair>();
			
			
			
			
			valuePairs.add(new BasicNameValuePair("_xsrf", xsrf));
			valuePairs.add(new BasicNameValuePair("email", email));
			valuePairs.add(new BasicNameValuePair("password", password));
			valuePairs.add(new BasicNameValuePair("remember_me", "true"));
			valuePairs.add(new BasicNameValuePair("captcha", verify));
			
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(valuePairs,Consts.UTF_8);
			HttpPost post = new HttpPost("https://www.zhihu.com/login/email");
			
			CloseableHttpResponse res = null;
			post.setEntity(entity);
			
			
			
			
			try {
				res = httpClient.execute(post);
				
				String msg =EntityUtils.toString(res.getEntity());
				Map<String, Object> map = 
						(Map<String, Object>) JSON.parseObject(msg, new TypeReference<Map<String, Object>>() {});
				
				Integer ss=  (Integer) map.get("r");
				String info = (String) map.get("msg");
				
				System.out.println(info);
				
				if(ss == 0){
					
					result = "200";
					
				}else{
					
					result = info;
				}
				
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
		
		
		if(result.equals("200")){
			
			return "success";
			
		}else{
			
			
			this.requestMap.put("msg", result);
			
			
			
			return "fail";
			
			
			
			
		}
		
		
		
		
		
		
	}

	
	
	
	
	
	
	
	
	
}
