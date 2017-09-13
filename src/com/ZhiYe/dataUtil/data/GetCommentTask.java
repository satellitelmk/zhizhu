package com.ZhiYe.dataUtil.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;







import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class GetCommentTask {

	public List<Comment> getTaskList(String uri , OutputStreamWriter out,Integer answerNum){
		
		File file = new File(uri);
		List<Comment> list = new ArrayList<Comment>();
		
		this.writeTo(out, "source,answerId,idName,time,upCount,commentCount,content"+"\r\n");
		
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(file),"GBK");
			BufferedReader bufferedReader = new BufferedReader(reader);
			
			String lineText= "";
			
			boolean flag =true;
			
			while((lineText = bufferedReader.readLine())!= null){
				
				String[] strArray = lineText.split(",");
				
				if(flag){
					flag = false;
					continue;
				}
				this.writeTo(out, lineText+"\r\n");
				
				if(strArray.length < 7)continue;
				
				
				int upNum = Integer.parseInt(strArray[4]);
				int commendNum =Integer.parseInt(strArray[5]);
				
				
				
				if(upNum > (commendNum/500)+1 || commendNum >(commendNum/500)+1 || strArray[5].length() > 100 ){
					
					
					
					
					
					if(commendNum>300){
						
						for(int i = 0;i<commendNum;i+=300){
							
							Comment getCommend = new Comment(strArray[1], i);
							list.add(getCommend);
							
							
						}
						
						
					}else{
						
						if(commendNum == 0){
							Comment getCommend = new Comment(strArray[1], -1);
							list.add(getCommend);
						}else{
							
							Comment getCommend = new Comment(strArray[1], 0);
							list.add(getCommend);
							
						}
						
					}
					
					
					
					
					
					
				}
				
			}
			
			
			reader.close();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		return list;
		
		
		
	}
		
		
		
		
	
	
	
	
	
	public String GetCommends(List<Map<String, Object>> data,String answerId){
		
		
		
		String info = "";
		try {
			
			for(Map<String, Object> item :data ){
				
				
				
				Map<String, Object> reply = (Map<String, Object>) item.get("reply_to_author");
				
				
				
				
				
				if(reply==null && Integer.parseInt(item.get("vote_count").toString())>0){
					
					Map<String, Object> author = (Map<String, Object>) ((Map<String, Object>) item.get("author")).get("member");
					
					info =info+answerId+","+ item.get("id")+","+author.get("name")+","+item.get("created_time")+
							","+item.get("vote_count")+","+item.get("can_recommend")+","+//this.convert(item.get("content").toString());
							item.get("content").toString().replaceAll("<[^\u4e00-\u9fa5]*>", "").replace(',', '，').replace('\n', '，').replace('\r', '，').replace("\"", " ").trim()+"\r\n";
					
					
					
					
				}
				
				
				
			}
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		return info;
		
		
	}
	
	
	public void writeTo(OutputStreamWriter writer ,String str){
		
		
		try {
			writer.write(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	public List<Map<String, Object>> getJsonData(Connection  connection){
		
		CloseableHttpResponse response = connection.getResponse();
		
		
		if(response == null|| response.getStatusLine().getStatusCode()!=200)return null;
		
		String firstJsonStr;
		List<Map<String, Object>> list = null;
		try {
			firstJsonStr = EntityUtils.toString(response.getEntity());
			
			
			Map<String, Object> map1 = 
					(Map<String, Object>) JSON.parseObject(firstJsonStr, new TypeReference<Map<String, Object>>() {});
			
			
			
			list = (List<Map<String, Object>>) map1.get("data");
			response.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		connection.getRequest().releaseConnection();
		
		return list;
		
		
	}
	
	
	public Connection getCommensResponse(CloseableHttpClient myClient , String answerId , Integer offset){
		
		HttpGet httpGet = new HttpGet(this.getCommensPath(answerId, offset));
		CloseableHttpResponse response = null;
		
		try {
		 response = myClient.execute(httpGet);
//		 httpGet.releaseConnection();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		Connection connection = new Connection(httpGet, response);
		
		return connection;
		
		
	}
	
	public String getCommensPath(String answerId , Integer offset){
		
		String str1 = "https://www.zhihu.com/api/v4/answers/";
		String str2 = "/comments?include=data%5B*%5D.author%2Ccollapsed%2Creply_to_author%2Cdisliked"
				+ "%2Ccontent%2Cvoting%2Cvote_count%2Cis_parent_author%2"
				+ "Cis_author&order=normal&limit=300&status=open&offset=";
		
		return str1+answerId+str2+offset;
	}
	
	
	
	public void doIterator(String uri , OutputStreamWriter out , CloseableHttpClient httpClient , Integer answerNum){
		
		
		List<Comment> list = this.getTaskList(uri , out ,answerNum);
		
		for(Comment get:list){System.out.print(get.getAnswerId()+"   ");}
		
		
		for(Comment commend : list){
			
			if(commend.getOffset() < 0)continue;
			
			List<Map<String, Object>> map = null;
			boolean flag = true;
			
			while(flag){
				 map = getJsonData(getCommensResponse
							(httpClient, commend.getAnswerId(), commend.getOffset()));
				
				 if(map!=null){flag = false;}
				 
			}
			
			
			
			String str = GetCommends(map, commend.getAnswerId());
			writeTo(out, str);
			
			
			
			
			
			
			System.out.println(commend);
			
		}
		
		
		
		
		
	}
			
	
	
		
		
		
		
		
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
