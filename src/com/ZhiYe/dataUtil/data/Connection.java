package com.ZhiYe.dataUtil.data;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

public class Connection {

	private HttpGet request;
	private CloseableHttpResponse response;
	public Connection(HttpGet request, CloseableHttpResponse response) {
		super();
		this.request = request;
		this.response = response;
	}
	public HttpGet getRequest() {
		return request;
	}
	public void setRequest(HttpGet request) {
		this.request = request;
	}
	public CloseableHttpResponse getResponse() {
		return response;
	}
	public void setResponse(CloseableHttpResponse response) {
		this.response = response;
	}
	
	
	
	
	
	
	
	
	
	
	
}
