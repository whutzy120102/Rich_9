package com.whut.richer.bean;

/*
 * 该类描述房产信息
 */
public class House {
	private int position;	//房子的位置
	private String name;	//拥有该房产的玩家
	private int toll;		//过路费
	private int price;		//房子买进的价格(包括升级费用)，卖出是两倍
	private int update_price;	//房产升级价格
	private int grade;		//房子的级别  最高3级
	
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getToll() {
		return toll;
	}
	public void setToll(int toll) {
		this.toll = toll;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getUpdate_price() {
		return update_price;
	}
	public void setUpdate_price(int update_price) {
		this.update_price = update_price;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
}