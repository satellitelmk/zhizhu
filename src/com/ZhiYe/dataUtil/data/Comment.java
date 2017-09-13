package com.ZhiYe.dataUtil.data;

public class Comment {

	
	private String answerId ;
	private int offset;
	public Comment(String answerId, int count) {
		super();
		this.answerId = answerId;
		this.offset = count;
	}
	public String getAnswerId() {
		return answerId;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
	
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}

	
	
	@Override
	public String toString() {
		return "Comment [answerId=" + answerId + ", offset=" + offset + "]";
	}
	public Comment(){};
	
	
	
	
	
	
	
	
}
