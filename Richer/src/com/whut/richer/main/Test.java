package com.whut.richer.main;

import java.util.Scanner;

import com.whut.richer.bean.Ground;
import com.whut.richer.bean.Player;
import com.whut.richer.map.Map;
import com.whut.richer.util.Control;
import com.whut.richer.util.Operation;

/*
 * 程序流程
 */
public class Test {
	private static int member;	//玩家数量
	private static String[] names ={"","钱夫人","阿土伯","孙小美","金贝贝"};//玩家姓名
	private static char[] symbols = {'Q','A','S','J'};//玩家在地图上的标识
	private static Player[] players;//当前玩家
	private static Scanner input = new Scanner(System.in);
	private static int step;	//命令带的参数
	private static boolean flag;	//判断当前是否跳过当前玩家
	private static int tiao=0;	//本轮跳过了多少人
	public static void main(String[] args) {	
		init();
		new Map();
//		Map.printMap(Map.map);
		Map.map.get(1).setBomb(true);
		Map.map.get(1).setSymbol('@');
		Map.map.get(2).setBomb(true);
		Map.map.get(2).setSymbol('@');
		Map.map.get(3).setBomb(true);
		Map.map.get(3).setSymbol('@');
		Map.map.get(4).setBomb(true);
		Map.map.get(4).setSymbol('@');
		Map.map.get(5).setBomb(true);
		Map.map.get(5).setSymbol('@');
		Map.map.get(6).setBomb(true);
		Map.map.get(6).setSymbol('@');
		do{
			for(int i=0;i<member;i++){
				ing(i);
//				if(flag){
//					i=i+tiao;
//					flag=false;
//					tiao=0;
//				}
			}
		}while(true);
	}
	
	/*
	 * 初始化游戏
	 */
	public static void init(){
	
		System.out.print("请选择2-4位不重复玩家，"
				+ "输入编号即可（1.钱夫人；2.阿土伯；3.孙小美；4.金贝贝）:");
		
		String m = input.nextLine();
		member = m.length();
		
		players = new Player[member];
		if(member<2||member>4){
			System.out.println("输入人数不对。");
			init();
			return;
		}
		System.out.print("设置玩家初始资金，范围1000~50000（默认10000）:");
		String money = input.nextLine();
		for(int i=0; i<member; i++){
			players[i] = new Player();
			int id = Integer.parseInt(m.charAt(i)+"");
			players[i].setName(names[id]);		
			players[i].setSymbol(symbols[id-1]);
			if(money.trim().length()!=0){
				players[i].setCash(Integer.parseInt(money));
			}
		}
	}
	
	/*
	 * 游戏进行
	 */
	public static void ing(int i){
		//打印地图
		Map.printMap(Map.map);
		//当前玩家是否跳过
		int stayBout = players[i].getStayBout();
		if(stayBout!=0){
//			flag=true;
//			tiao+=1;
			players[i].setStayBout(--stayBout);		
			System.out.println(players[i].getName()+"跳过本回合,还需跳过"+players[i].getStayBout()+"回合");
//			ing(++i);
			return;
		}
		//指定玩家输入指令
		System.out.print(players[i].getName()+">:");
		//读取控制台一行
		String[] orders = input.nextLine().split(" ");
		String order = orders[0];
		if(orders.length>1){
			step = Integer.parseInt(orders[1].trim());			
		}
		if("sell".equalsIgnoreCase(order)){
			exist_step(i);
			Control.sell(step, players[i]);
			ing(i);
			return;
		}else if("block".equalsIgnoreCase(order)){
			exist_step(i);
			Control.block(step, players[i]);
			ing(i);
			return;
		}else if("bomb".equalsIgnoreCase(order)){
			exist_step(i);
			Control.bomb(step, players[i]);
			ing(i);
			return;
		}else if("robot".equalsIgnoreCase(order)){
			Control.robot(players[i]);
			ing(i);
			return;
		}else if("query".equalsIgnoreCase(order)){
			Control.query(players[i]);
			ing(i);
			return;
		}else if("help".equalsIgnoreCase(order)){
			Control.help();
			ing(i);
			return;
		}else if("quit".equalsIgnoreCase(order)){
			Control.quit();
		}else if("roll".equalsIgnoreCase(order)){
			do_roll(i);
		}else{
				System.out.println("命令格式有错误，请输入Help命令查询或重新输入.");
				ing(i);
				return;
		}
		for(int j=0;j<member;j++){
			int cur_position = players[j].getPosition();
			Ground cur_ground = Map.map.get(cur_position);
			cur_ground.setSymbol(players[j].getSymbol());
		}		
	}
	
	public static void do_roll(int i){
		//调用Roll方法生成一个1~6的随机数
		int n = Control.roll();
		//显示骰子的数值
		System.out.println("骰子的点数是"+n);
		n = Operation.exist_block(n, players[i]);
		//获取当前玩家当前位置			
		int current = players[i].getPosition();
		//获取玩家目标位置的标识
		Ground aim_ground = Map.map.get(n+current);	
		//玩家走到目标位置
		players[i].setPosition(n+current);
		if(aim_ground.isBomb()){
//			flag=true;
//			tiao+=1;
			players[i].setStayBout(3);
			players[i].setPosition(14);
			aim_ground.setSymbol(aim_ground.getPreSymbol());
			aim_ground.setBomb(false);
			System.out.println("不幸踩到炸弹,进医院休息3天");
//			ing(++i);			
			return;
		}
		if(aim_ground.isJail()){
			players[i].setStayBout(2);
			System.out.println("被抓入监狱2天");
			return;
		}
		if(aim_ground.isGiftHouse()){
			System.out.println("欢迎光临礼品屋，请选择一件您喜欢的礼品：");
			System.out.println("通过输入编号选择礼品:1.奖金，2.点数卡，3.财神。");
			System.out.println("只能喝选择一件礼品，选择后，自动退出礼品屋（输入错误也退出，就当放弃此次机会）");
			String choose = input.nextLine();
			try{
				int c = Integer.parseInt(choose.trim());
				if(c==1){
					players[i].setCash(players[i].getCash()+2000);
				}else if(c==2){
					players[i].setPoint(players[i].getPoint()+200);
				}else if(c==3){
					players[i].setMammon(players[i].getMammon()+5);
				}else{
					System.out.println("输入错误,放弃资格");
					return;
				}
			}catch(Exception e){
				System.out.println("输入错误,放弃资格");
				return;
			}
		}
		if(aim_ground.isTool()){
			
			System.out.println("欢迎光临道具屋，请选择您所需要的道具.");
			if(players[i].getPoint()<50){
				System.out.println("您的点数不足，自动退出");
				return;
			}			
			System.out.println("通过输入道具的编号选择道具，每位玩家最多可以拥有10个道具");
			System.out.println("1.路障 50；2.机器娃娃 50；3.炸弹 50");
			System.out.println("购买格式（编号 数量）：如:1 2");
			System.out.println("按F键退出道具屋。");
			if(players[i].getBlockNum()+players[i].getBombNum()+players[i].getRobotNum()<10){
				String in = input.nextLine();
			}else{
				System.out.println("您的道具数量已满，自动退出");
				return;
			}			
		}
		//修改玩家目的地的标识
		aim_ground.setSymbol(players[i].getSymbol());
		//将玩家当前位置改回之前的状态
		Ground cur_ground = Map.map.get(current);
		cur_ground.setSymbol(cur_ground.getPreSymbol());
	}
	
	//判断命令输入格式
	public static void exist_step(int i){
		if(step==0){
			System.out.println("命令格式有错或参数不能为0,请输入Help命令查询或重新输入.");
			ing(i);
			return;
		}
	}
}