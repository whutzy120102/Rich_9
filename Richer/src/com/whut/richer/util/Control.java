package com.whut.richer.util;

import java.util.List;

import com.whut.richer.bean.Ground;
import com.whut.richer.bean.House;
import com.whut.richer.bean.Player;
import com.whut.richer.map.Map;

/*
 * 此类描述玩家操作命令
 */
public class Control {
	
	//掷骰子，返回点数
	public static int roll(){
		return (int)(Math.random()*6+1);
	}
	
	//轮到玩家时，可以出售任意位置N的自己的房产
	public static void sell(int n,Player player){
		boolean flag = false;
		List<House> property = player.getProperty();
		for(House h:property){
			if(h.getPosition()==n){
				int price = player.getCash();
				price+=h.getPrice()*2;
				player.setCash(price);
				Ground ground = Map.map.get(n);
				ground.setHouse(null);
				ground.setPreSymbol('0');
				flag=true;
			}		
		}
		if(!flag){
			System.out.println("这块地不是你的，无权卖出");
		}
	}
	
	//放路障 N为前后相对距离 负数为表示后方
	public static void block(int n,Player player){
		int blockNum = player.getBlockNum();
		if(blockNum!=0){
			int position = player.getPosition();
			if(n>=-10&&n<=10){
				Ground ground = Map.map.get(position+n);
				if(ground.getPreSymbol()!='H'&&ground.getPreSymbol()!='P'
						&&ground.getPlayer()==null&&ground.isBlock()==false
						&&ground.isBomb()==false){
					ground.setBlock(true);
					ground.setSymbol('#');
					player.setBlockNum(--blockNum);
				}else{
					if((ground.getPreSymbol()=='H'||ground.getPreSymbol()=='P')
							&&ground.isBlock()==false&&ground.isBomb()==false)
						ground.setBlock(true);
						ground.setSymbol('#');
						player.setBlockNum(--blockNum);
				}

			}else{
				System.out.println("您放置的位置过远，请放在10格以内！");
			}
		}else{
			System.out.println("抱歉，您没有路障卡！");		
		}
	}
	
	//放炸弹  与路障相同  被炸的住院3天
	public static void bomb(int n,Player player){
		int bombNum = player.getBombNum();
		if(bombNum!=0){
			int position = player.getPosition();
			if(n>=-10&&n<=10){
				Ground ground = Map.map.get(position+n);
				if(ground.getPreSymbol()!='H'&&ground.getPreSymbol()!='P'
						&&ground.getPlayer()==null&&ground.isBlock()==false
						&&ground.isBomb()==false){
					ground.setBomb(true);
					ground.setSymbol('@');
					player.setBombNum(--bombNum);
				}else{
					if((ground.getPreSymbol()=='H'||ground.getPreSymbol()=='P')
							&&ground.isBlock()==false&&ground.isBomb()==false)
						ground.setBomb(true);
						ground.setSymbol('@');
						player.setBombNum(--bombNum);
				}
			}else{
				System.out.println("您放置的位置过远，请放在10格以内！");
			}
		}else{
			System.out.println("抱歉，您没有炸弹卡！");
		}
	}
	
	//该道具可以清扫前方10步内的其他道具
	public static void robot(Player player){
		int robot = player.getRobotNum();
		if(robot!=0){
			int position = player.getPosition();
			for(int i=0;i<10;i++){
				Ground ground = Map.map.get(position+i+1);
				if(ground.isBlock()==true){			
					ground.setBlock(false);
					ground.setSymbol(ground.getPreSymbol());
				}
				if(ground.isBomb()==true){				
					ground.setBomb(false);
					ground.setSymbol(ground.getPreSymbol());
				}
			}
			player.setRobotNum(--robot);
		}else{
			System.out.println("抱歉，您没有机器人！");
		}
	}
	
	//显示自家资产信息
	public static void query(Player player){
		int cash = player.getCash();
		int point = player.getPoint();
		int blockNum = player.getBlockNum();
		int bombNum = player.getBombNum();
		int robot = player.getRobotNum();
		List<House> property = player.getProperty();
		StringBuilder sb = new StringBuilder();
		String hp = null;
		for(House h:property){
			int position = h.getPosition();
			int price = h.getPrice()*2;
			hp = position+":"+price+"  ";
			sb.append(hp);
		}
		System.out.println(player.getName()+",以下是您的所有资产：");
		System.out.println("您的现金："+cash);
		System.out.println("您的点数："+point);
		System.out.println("您的道具："+"路障："+blockNum+" 炸弹："+bombNum+" 机器人："+robot);
		if(sb.length()!=0)
			System.out.println("您的房产有："+sb.toString());
		else
			System.out.println("您没有房产");
	}
	
	//Help命令  查看命令帮助
	public static void help(){
		System.out.println("命令		功能说明							参数说明");
		System.out.println("Roll		掷骰子命令。行走1-6步，步数由随机算法产生。 ");
		System.out.println("Sell n		轮到玩家时，可出售自己的任意房产，出售价格为投资总		n 为房产在地图上的绝对位置");
		System.out.println("		成本的2倍。		");
		System.out.println("Block n		玩家可将路障放置到离当前位置的前后10步的任何位置，		n为前后的相对距离，负数表示后方。");
		System.out.println("		任一玩家经过路障，将被拦截。该道具一次有效。		");
		System.out.println("Bomb n		玩家可将路障放置到离当前位置前后10步的任意位置，任一	n为前后的相对距离，负数表示后方。");
		System.out.println("		玩家经过路障，将被炸伤，送往医院，住院三天。		");
		System.out.println("Robot		使用该道具，可清扫前方路面上10步内的任何其他道具，如");
		System.out.println("		炸弹、路障。");
		System.out.println("Query		显示自家资产信息");
		System.out.println("Help		查看命令帮助");
		System.out.println("quit		强制退出");
		System.out.println("Step n		遥控骰子							n为指定的步数");
	}
	
	//强制退出
	public static void quit(){
		System.out.println("游戏结束");
		System.exit(0);
	}
	
	//遥控骰子
	public static int step(int n){
		return n;
	}
}
