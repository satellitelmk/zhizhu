package com.ZhiYe.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.CookieStore;

public class DefaultCookieStores {

	private static Map<String, CookieStore> cookieStores;
	
	static{
		
		cookieStores = new HashMap<String, CookieStore>();
	}
	
	
	public static CookieStore getCookieStoreByJessionId(String JsessionId){
		
		return cookieStores.get(JsessionId);
	}
	
	
	
	public static void saveCookieStore(String JsessionId,CookieStore cookieStore){
		
		cookieStores.put(JsessionId, cookieStore);
		
		
	}
	
	public static Integer getCookieStoresNum(){
		
		return cookieStores.size();
		
		
		
	}
	
	
	public static void clearCookieStore(){
		
		cookieStores.clear();
		
		
	}
	
	
	
	
	
}
