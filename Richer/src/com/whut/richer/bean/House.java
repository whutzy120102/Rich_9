package com.whut.richer.bean;

/*
 * 该类描述房产信息
 */
public class House {
	
	private int play_id;	//拥有该房产的玩家
	private int toll;		//过路费
	private int price;		//房子买进的价格(包括升级费用)，卖出是两倍
	private int update_price;	//房产升级价格
	
	public int getPlay_id() {
		return play_id;
	}
	public void setPlay_id(int play_id) {
		this.play_id = play_id;
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
	
}
