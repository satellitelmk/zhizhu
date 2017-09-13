package com.ZhiYe.entity;

public class WordsCons {

	private double value;
	private String name;

	
	
	
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "WordsCons [value=" + value + ", name=" + name + "]";
	}

	
	
	public WordsCons(double value, String name) {
		super();
		this.value = value;
		this.name = name;
	}
	public WordsCons(){};
	
	
}
