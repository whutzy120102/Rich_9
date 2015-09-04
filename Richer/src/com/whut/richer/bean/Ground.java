package com.whut.richer.bean;

/*
 * 描述土地状态
 */
public class Ground {
	private int position;	//土地的位置
	private char symbol;	//在控制台显示的当前标识
	private char preSymbol;	//原始的状态,即除了玩家之外不可动的
	private boolean bomb;	//该位置是否有炸弹
	private boolean block;	//该位置是否有路障
	private House house;	//该位置是否有房产
	private Mine mine;		//该位置是否有矿地
	private boolean tool;	//该位置是否有道具屋
	private boolean hospital;//该位置是否有医院
	private boolean jail;	//该位置是否有监狱
	private boolean giftHouse;	//该位置是否有礼品屋
	private boolean magicHouse;	//该位置是否有魔法屋
	private Player player;		//该位置上是否有玩家
	
	public Ground(){
		this.symbol = '0';
		this.preSymbol='0';
		this.bomb = false;
		this.block = false;
		this.house = null;
		this.mine = null;
		this.tool = false;
		this.hospital = false;
		this.jail = false;
		this.giftHouse = false;
		this.magicHouse = false;
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
	
	public char getPreSymbol() {
		return preSymbol;
	}

	public void setPreSymbol(char preSymbol) {
		this.preSymbol = preSymbol;
	}

	public boolean isBomb() {
		return bomb;
	}
	public void setBomb(boolean bomb) {
		this.bomb = bomb;
	}
	public boolean isBlock() {
		return block;
	}
	public void setBlock(boolean block) {
		this.block = block;
	}
	public House getHouse() {
		return house;
	}
	public void setHouse(House house) {
		this.house = house;
	}
	public Mine getMine() {
		return mine;
	}
	public void setMine(Mine mine) {
		this.mine = mine;
	}
	public boolean isTool() {
		return tool;
	}
	public void setTool(boolean tool) {
		this.tool = tool;
	}
	public boolean isHospital() {
		return hospital;
	}
	public void setHospital(boolean hospital) {
		this.hospital = hospital;
	}
	public boolean isJail() {
		return jail;
	}
	public void setJail(boolean jail) {
		this.jail = jail;
	}
	public boolean isGiftHouse() {
		return giftHouse;
	}
	public void setGiftHouse(boolean giftHouse) {
		this.giftHouse = giftHouse;
	}

	public boolean isMagicHouse() {
		return magicHouse;
	}

	public void setMagicHouse(boolean magicHouse) {
		this.magicHouse = magicHouse;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
