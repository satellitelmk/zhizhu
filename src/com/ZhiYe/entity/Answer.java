package com.ZhiYe.entity;

public class Answer {

	private String answerId;
	private String author;
	private String upCount;
	private String  commentCount;
	private String summary;
	private String keywords;
	private String sentiments;
	private String time;
	public String getAnswerId() {
		return answerId;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getUpCount() {
		return upCount;
	}
	public void setUpCount(String upCount) {
		this.upCount = upCount;
	}
	public String getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getSentiments() {
		return sentiments;
	}
	public void setSentiments(String sentiments) {
		this.sentiments = sentiments;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "Answer [answerId=" + answerId + ", author=" + author
				+ ", upCount=" + upCount + ", commentCount=" + commentCount
				+ ", summary=" + summary + ", keywords=" + keywords
				+ ", sentiments=" + sentiments + ", time=" + time + "]";
	}
	
	
	
	
	
	
	
	
}
