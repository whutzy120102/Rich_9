package com.whut.richer.main;

import java.util.Scanner;

import com.whut.richer.bean.Ground;
import com.whut.richer.bean.Player;
import com.whut.richer.map.Map;
import com.whut.richer.util.Control;

public class Test {
	private static int member;	//玩家数量
	private static String[] names ={"","钱夫人","阿土伯","孙小美","金贝贝"};
	private static char[] ids = {'Q','A','S','J'};
	private static Player[] players;
	public static void main(String[] args) {	
		init();
		new Map();
		Map.printMap(Map.map);
		do{
			ing();
		}while(true);
	}
	
	/*
	 * 初始化游戏
	 */
	public static void init(){
		System.out.print("请选择2-4位不重复玩家，输入编号即可（1.钱夫人；2.阿土伯；3.孙小美；4.金贝贝）:");
		Scanner input = new Scanner(System.in);
		String m = input.nextLine();
		member = m.length();
		
		players = new Player[member];
		if(member<2||member>4){
			System.out.println("输入人数不对。");
			init();
		}
		System.out.print("设置玩家初始资金，范围1000~50000（默认10000）:");
		String money = input.nextLine();
		if(money.trim().length()==0){
			for(int i=0;i<member;i++){
				players[i] = new Player();
				int id = Integer.parseInt(m.charAt(i)+"");
				players[i].setName(names[id]);		
				players[i].setId(ids[id-1]);
			}
		}else{
			for(int i=0;i<member;i++){
				players[i] = new Player();
				int id = Integer.parseInt(m.charAt(i)+"");
				players[i].setName(names[id]);	
				players[i].setCash(Integer.parseInt(money));
				players[i].setId(ids[id-1]);
			}
		}
	}
	
	public static void ing(){
		for(int i=0;i<member;i++){
			System.out.print(players[i].getName()+">:");
			Scanner input = new Scanner(System.in);
			String order = input.nextLine();
			if(order.equals("Roll")){
				int n = Control.roll();
				System.out.println("骰子的点数是"+n);
				int current = players[i].getPosition();
				players[i].setPosition(n+current);
				Ground ground = Map.map.get(n+current);
				ground.setSymbol(players[i].getId());
				Map.printMap(Map.map);
			}
		}
	}
}
