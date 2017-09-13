package com.ZhiYe.action;

import java.io.IOException;
import java.util.Map;

import org.apache.catalina.tribes.util.Arrays;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import com.ZhiYe.base.ClientBase;
import com.ZhiYe.entity.Question;
import com.ZhiYe.util.DefaultCookieStores;

public class ShowQuestionBasicAction implements SessionAware,RequestAware {

	
	private Map<String, Object> sessionMap = null;
	private Map<String,Object> requestMap = null;

	
	
	
	
	@Override
	public void setRequest(Map<String, Object> arg0) {

		this.requestMap = arg0;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {

		this.sessionMap = arg0;
	}

	
	private String questionId;





	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	
	public String showQuBasic(){
		
		

		Question questionPage = new Question(questionId);
		
		if(questionPage.isSuccess() == false){
			
			requestMap.put("msg", "fail");
			
			
		}else{
			
			if(sessionMap.get("questionPage") != null){
				
				sessionMap.remove("questionPage");
			}
			sessionMap.put("questionPage", questionPage);
			requestMap.put("msg", "success");
		}
		
		
		
		
//		Connection.Response response = null;
//		 try {
//		  response = Jsoup.connect("https://www.zhihu.com/question/"+questionId)
//		    .timeout(10000)
//		    .execute();
//		 } catch (IOException e) {
//		  System.out.println("io - "+e);
//		 }
//
//		Integer codeNum = response.statusCode();
//		Document doc = null;
//		try {
//			doc = response.parse();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		if(codeNum  == 404){
//			
//			requestMap.put("msg", "fail");
//			return "success";
//		}else{
//			
//			requestMap.put("msg", "success");
//			
//		}
//		
//		
//		String questionTitle = "";
//		String questionDes = "";
//		Integer answerNum = null;
//		Integer careNum = null;
//		Integer commentNum = null;
//		Integer scanNum = null;
//		
//		
//		org.jsoup.select.Elements eles = doc.select("a.TopicLink > div.Popover > div");
//		String[] tags = new String[eles.size()];
//		
//		for(int i = 0 ;i<eles.size();i++){
//			
//			tags[i] = eles.get(i).text();
//			
//			
//		}
//		
//		
//		eles = doc.select("a.TopicLink");
//		String[] tagsId = new String[eles.size()];
//		
//		for(int i = 0 ;i<eles.size();i++){
//			
//			tagsId[i] = eles.get(i).attr("href");
//			
//			
//		}
//		
//		
//		
//		
//		eles = doc.select("h3[data-num]#zh-question-answer-num");
//		
//		
//		if(eles.size()==0){
//			eles =  doc.select("h4.List-headerText>span:contains( 个回答)");
//		}
//		
//		String answerStr = (((org.jsoup.select.Elements)eles).get(0)).text();
//		answerNum = Integer.parseInt(answerStr.substring(0, answerStr.indexOf(' ')));
//		
//		questionTitle = doc.select("h1.QuestionHeader-title").get(0).text();
//		
//		questionDes = doc.select("span.RichText").get(0).text();
//		
//		scanNum = Integer.parseInt(doc.select("div.NumberBoard-name:contains(被浏览)~div").get(0).text());
//		careNum = Integer.parseInt(doc.select("div.NumberBoard-name:contains(关注者)~div").get(0).text());
//		
//		String commentStr = doc.select("button.Button--plain:contains( 条评论)").get(0).text();
//		commentNum = Integer.parseInt(commentStr.substring(0, commentStr.indexOf(' ')));
		
//		sessionMap.put("answerNum", answerNum);
//		sessionMap.put("questionId", questionId);
//		requestMap.put("questionTitle", questionTitle);
//		requestMap.put("questionDes", questionDes);
//		requestMap.put("answerNum", answerNum);
//		requestMap.put("careNum", careNum);
//		requestMap.put("commentNum", commentNum);
//		requestMap.put("scanNum", scanNum);
//		requestMap.put("tags", tags);
//		requestMap.put("tagsId", tagsId);
		
		return "success";
	}
	
	
	
	
	
	@Test
	public void uBasic(){
		
		
		Connection.Response response = null;
		 try {
		  response = Jsoup.connect("https://www.zhihu.com/question/36603744")
		    .timeout(10000)
		    .execute();
		 } catch (IOException e) {
		  System.out.println("io - "+e);
		 }

		Integer codeNum = response.statusCode();
		Document doc = null;
		try {
			doc = response.parse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(codeNum == 404){
			System.out.println("codeNum="+codeNum);
			return;
		}
		
		System.out.println("codeNum="+codeNum);
		
		String questionTitle = "";
		String questionDes = "";
		Integer answerNum = null;
		Integer careNum = null;
		Integer commentNum = null;
		Integer scanNum = null;
		
		
		org.jsoup.select.Elements eles = doc.select("a.TopicLink > div.Popover > div");
		String[] tags = new String[eles.size()];
		
		
		
		for(int i = 0 ;i<eles.size();i++){
			
			tags[i] = eles.get(i).text();
			
			
		}
		
		System.out.println(Arrays.toString(tags));
		
		eles = doc.select("h3[data-num]#zh-question-answer-num");
		
		
		if(eles.size()==0){
			eles =  doc.select("h4.List-headerText>span:contains( 个回答)");
		}
		
		String answerStr = (((org.jsoup.select.Elements)eles).get(0)).text();
		answerNum = Integer.parseInt(answerStr.substring(0, answerStr.indexOf(' ')));
		
		questionTitle = doc.select("h1.QuestionHeader-title").get(0).text();
		
		questionDes = doc.select("span.RichText").get(0).text();
		
		scanNum = Integer.parseInt(doc.select("div.NumberBoard-name:contains(被浏览)~div").get(0).text());
		careNum = Integer.parseInt(doc.select("div.NumberBoard-name:contains(关注者)~div").get(0).text());
		
		String commentStr = doc.select("button.Button--plain:contains( 条评论)").get(0).text();
		commentNum = Integer.parseInt(commentStr.substring(0, commentStr.indexOf(' ')));
		
		eles = doc.select("a.TopicLink");
		String[] tagsId = new String[eles.size()];
		
		for(int i = 0 ;i<eles.size();i++){
			
			tagsId[i] = eles.get(i).attr("href");
			
			
		}
		
		System.out.println(Arrays.toString(tagsId));

		
		
		
		
	}
	
	
	
	
	
	
}
