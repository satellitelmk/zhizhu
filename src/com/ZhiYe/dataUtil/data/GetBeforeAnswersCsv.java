package com.ZhiYe.dataUtil.data;

import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class GetBeforeAnswersCsv {

	private String questionId;
	
	public GetBeforeAnswersCsv(String questionId){
		
		this.questionId = questionId;
		
	}
	
	
	
	
	
	public String path(String questionId , int offset ){
		
		String pathStr1 = "https://www.zhihu.com/api/v4/questions/";
		String pathStr2 = "/answers?include=data%5B*%5D.is_normal%2Cis_sticky%2Ccollapsed_by%2Csuggest_edit%2Ccomment_count"
				+ "%2Ccollapsed_counts%2Creviewing_comments_count%2Ccan_comment%2Ccontent%2Ceditable_content"
				+ "%2Cvoteup_count%2Creshipment_settings%2Ccomment_permission%2Cmark_infos%2Ccreated_time"
				+ "%2Cupdated_time%2Crelationship.is_author%2Cvoting%2Cis_thanked%2Cis_nothelp%2Cupvoted_followees"
				+ "%3Bdata%5B*%5D.author.is_blocking%2Cis_blocked%2Cis_followed%2Cvoteup_count%2Cmessage_thread_token"
				+ "%2Cbadge%5B%3F(type%3Dbest_answerer)%5D.topics&offset=";
		String pathStr3 = "&limit=20&sort_by=default";
		
		
		return pathStr1+questionId+pathStr2+offset+pathStr3;
		
		
	}
	
	public void writeHeader(OutputStreamWriter out){
		
		
		try {
			
			out.write("source,answerId,idName,time,upCount,commentCount,content"+"\r\n");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public void write(CloseableHttpClient httpClient, OutputStreamWriter out ,Integer offset){
		
		
		HttpGet g = new HttpGet(path(this.questionId, offset));
		

		CloseableHttpResponse r;
		try {
			r = httpClient.execute(g);
			String firstJsonStr = EntityUtils.toString(r.getEntity());
			Map<String, Object> map1 = 
					(Map<String, Object>) JSON.parseObject(firstJsonStr, new TypeReference<Map<String, Object>>() {});
			
			List<Map<String, Object>> list1 = (List<Map<String, Object>>) map1.get("data");
			
			System.out.println("共有：" + list1.size());
			
			for(Map<String, Object> item :list1 ){
				
				String info = "";
				
				
				Map<String, Object> author = (Map<String, Object>) item.get("author");
				
				info =info+"1,"+ item.get("id")+","+author.get("name")+","+item.get("created_time")+
						","+item.get("voteup_count")+","+item.get("comment_count")+","+//this.convert(item.get("content").toString());
						item.get("content").toString().replaceAll("<[^\u4e00-\u9fa5]*>", "").replace(',', '，').replace('\n', '，').replace('\r', '，').replace("\"", " ").trim();
				
					out.write(info+"\r\n");
				
			}
			
			g.releaseConnection();
			r.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		}
		
	
	
	
}
