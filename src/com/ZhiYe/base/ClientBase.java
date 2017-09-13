package com.ZhiYe.base;

import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class ClientBase {

	public static CloseableHttpClient getClient(CookieStore cookieStore){
		
		 RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
		
		 CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).setDefaultRequestConfig(requestConfig).build();
		
		return httpClient;
		
		
		
	}
	
	
	
	
	
	
}
