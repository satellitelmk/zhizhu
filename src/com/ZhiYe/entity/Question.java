package com.ZhiYe.entity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class Question {

	
	private boolean isSuccess;
	private String questionId;
	private Integer answerNum;
	private String questionTitle;
	private String questionDes;
	private Integer careNum;
	private Integer commentNum;
	private Integer scanNum;
	private String[] tags;
	private String[] tagsId;
	private String dataTime;
	
	
	
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public Integer getAnswerNum() {
		return answerNum;
	}
	public void setAnswerNum(Integer answerNum) {
		this.answerNum = answerNum;
	}
	public String getQuestionTitle() {
		return questionTitle;
	}
	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}
	public String getQuestionDes() {
		return questionDes;
	}
	public void setQuestionDes(String questionDes) {
		this.questionDes = questionDes;
	}
	public Integer getCareNum() {
		return careNum;
	}
	public void setCareNum(Integer careNum) {
		this.careNum = careNum;
	}
	public Integer getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}
	public Integer getScanNum() {
		return scanNum;
	}
	public void setScanNum(Integer scanNum) {
		this.scanNum = scanNum;
	}
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	public String[] getTagsId() {
		return tagsId;
	}
	public void setTagsId(String[] tagsId) {
		this.tagsId = tagsId;
	}

	
	
	
	public String getDataTime() {
		return dataTime;
	}
	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}
	public Question(String questionId){
		
		this.questionId = questionId;
		
		Connection.Response response = null;
		 try {
		  response = Jsoup.connect("https://www.zhihu.com/question/"+questionId)
		    .timeout(10000)
		    .execute();
		 } catch (IOException e) {
		  System.out.println("io - "+e);
		 }

		Document doc = null;
		
		if(response == null ||response.statusCode()  == 404){
			
			this.isSuccess = false;
			return;
			
		}else{
			
			this.isSuccess = true;
			try {
				doc = response.parse();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		

		
		
		org.jsoup.select.Elements eles = doc.select("a.TopicLink > div.Popover > div");
		this.tags = new String[eles.size()];
		
		for(int i = 0 ;i<eles.size();i++){
			
			tags[i] = eles.get(i).text();
			
			
		}
		
		
		eles = doc.select("a.TopicLink");
		this.tagsId = new String[eles.size()];
		
		for(int i = 0 ;i<eles.size();i++){
			
			tagsId[i] = eles.get(i).attr("href");
			
			
		}
		
		
		
		
		eles = doc.select("h3[data-num]#zh-question-answer-num");
		
		
		if(eles.size()==0){
			eles =  doc.select("h4.List-headerText>span:contains( 个回答)");
		}
		
		String answerStr = (((org.jsoup.select.Elements)eles).get(0)).text();
		this.answerNum = Integer.parseInt(answerStr.substring(0, answerStr.indexOf(' ')));
		
		this.questionTitle = doc.select("h1.QuestionHeader-title").get(0).text();
		
		this.questionDes = doc.select("span.RichText").get(0).text();
		
		this.scanNum = Integer.parseInt(doc.select("div.NumberBoard-name:contains(被浏览)~div").get(0).text());
		this.careNum = Integer.parseInt(doc.select("div.NumberBoard-name:contains(关注者)~div").get(0).text());
		
		String commentStr = doc.select("button.Button--plain:contains( 条评论)").get(0).text();
		this.commentNum = Integer.parseInt(commentStr.substring(0, commentStr.indexOf(' ')));
		this.dataTime = String.valueOf(System.currentTimeMillis());
		
		
		
		
		
		
		
		
		
	}
	
	
	public void writeBasicToFile(String filePath) throws IOException{
		
		File file = new File(filePath);
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file,true),"GBK");
		
		BufferedWriter bufferedWrier = new BufferedWriter(writer);
		
		writer.write("saveTime:"+this.dataTime+"\r\n");
		writer.write("questionId:"+this.questionId+"\r\n");
		writer.write("questionTitle:"+this.questionTitle+"\r\n");
		writer.write("questionDes:"+this.questionDes+"\r\n");
		writer.write("answerNum:"+this.answerNum+"\r\n");
		writer.write("careNum:"+this.careNum+"\r\n");
		writer.write("commentNum:"+this.commentNum+"\r\n");
		writer.write("scanNum:"+this.scanNum+"\r\n");
		
		bufferedWrier.flush();
		bufferedWrier.close();
		writer.close();
		
		
	}
	

	public Question(File file) throws IOException{
		
		InputStreamReader reader = new InputStreamReader(new FileInputStream(file),"GBK");
		BufferedReader bufferedReader = new BufferedReader(reader);
		
		int num = 0;
		String lineText = "";
		while((lineText = bufferedReader.readLine())!=null){
			
			if(num == 0){
				
				String[] temp = lineText.split(":");
				
				Date date = new Date( Long.parseLong(temp[1]));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String dateStr = sdf.format(date);
				this.dataTime = dateStr;
				
				
				
				
			}
			if(num == 1){
				
				String[] temp = lineText.split(":");
				this.questionId = temp[1];
				
			}
			if(num == 2){
				
				String[] temp = lineText.split(":");
				this.questionTitle = temp[1];
				
			}
			if(num == 3){
				
				String[] temp = lineText.split(":");
				this.questionDes = temp[1];
				
			}
			if(num == 4){
				
				String[] temp = lineText.split(":");
				this.answerNum = Integer.parseInt(temp[1]);
				
			}
			if(num == 5){
				
				String[] temp = lineText.split(":");
				this.careNum = Integer.parseInt(temp[1]);
				
			}
			if(num == 6){
				
				String[] temp = lineText.split(":");
				this.commentNum = Integer.parseInt(temp[1]);
				
			}
			if(num == 7){
				
				String[] temp = lineText.split(":");
				this.scanNum = Integer.parseInt(temp[1]);
				
			}
			
			
			
			num++;
		}
		
		
		bufferedReader.close();
		reader.close();
		
		
		
		
	}
	
	@Override
	public String toString() {
		return "Question [isSuccess=" + isSuccess + ", questionId="
				+ questionId + ", answerNum=" + answerNum + ", questionTitle="
				+ questionTitle + ", questionDes=" + questionDes + ", careNum="
				+ careNum + ", commentNum=" + commentNum + ", scanNum="
				+ scanNum + ", tags=" + Arrays.toString(tags) + ", tagsId="
				+ Arrays.toString(tagsId) + ", dataTime=" + dataTime + "]";
	}
	
	
	
	
	
	
	
	
	
	
}
