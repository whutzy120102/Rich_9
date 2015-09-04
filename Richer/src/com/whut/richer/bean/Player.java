package com.whut.richer.bean;

import java.util.ArrayList;
import java.util.List;


/*
 *该类描述玩家的属性和操作动作 
 */
public class Player {
	private int blockNum;	//玩家拥有的路障数
	private int bombNum;	//玩家的炸弹数
	private int robotNum;	//玩家的机器人数
	private int position;	//玩家当前位置
	private char symbol;	//玩家地图标识
	private String name;		//玩家姓名
	private int cash;	//玩家现金数
	private int point;	//玩家购买道具是用的点数
	private int stayBout;	//进医院、监狱等停留回合数
	private int mammon;	//财神状态
	private List<House> property = new ArrayList<House>();	//玩家房产
	
	public Player(){
		cash=10000;
		point=0;
		stayBout=0;
		position=0;
		mammon=0;
	}
	
	public int getBlockNum() {
		return blockNum;
	}

	public void setBlockNum(int blockNum) {
		this.blockNum = blockNum;
	}

	public int getBombNum() {
		return bombNum;
	}

	public void setBombNum(int bombNum) {
		this.bombNum = bombNum;
	}

	public int getRobotNum() {
		return robotNum;
	}

	public void setRobotNum(int robotNum) {
		this.robotNum = robotNum;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCash() {
		return cash;
	}
	public void setCash(int cash) {
		this.cash = cash;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getStayBout() {
		return stayBout;
	}
	public void setStayBout(int stayBout) {
		this.stayBout = stayBout;
	}
	
	public int getMammon() {
		return mammon;
	}

	public void setMammon(int mammon) {
		this.mammon = mammon;
	}

	public List<House> getProperty() {
		return property;
	}
	public void setProperty(List<House> property) {
		this.property = property;
	}
	
}
