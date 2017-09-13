package com.ZhiYe.entity;

public class PieCons{
	
	private Integer value;
	private String name;
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public PieCons(Integer value, String name) {
		super();
		this.value = value;
		this.name = name;
	}

	
	public PieCons(){};
	
	
}
