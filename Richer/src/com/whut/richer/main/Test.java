package com.whut.richer.main;

import java.util.List;
import java.util.Scanner;

import com.whut.richer.bean.Ground;
import com.whut.richer.bean.House;
import com.whut.richer.bean.Player;
import com.whut.richer.map.Map;
import com.whut.richer.util.Control;
import com.whut.richer.util.Operation;

/*
 * 程序流程
 */
public class Test {
	private static int member;	//玩家数量
	private static int alive_member;	//存活玩家数量
	private static String[] names ={"","钱夫人","阿土伯","孙小美","金贝贝"};//玩家姓名
	private static char[] symbols = {'Q','A','S','J'};//玩家在地图上的标识
	private static Player[] players;//当前玩家
	private static Scanner input = new Scanner(System.in);
	private static int step;	//命令带的参数
	public static void main(String[] args) {	
		init();
		new Map();
		int i = 0;
		do{
			if(players[i].isAlive()){
				ing(i);
				i++;
			}
			if(i>=member){
				i=0;
			}
		}while(alive_member!=1);
		for(int j=0;j<member;j++){
			if(players[j].isAlive())
				System.out.println("玩家"+players[j].getName()+"获胜！");
		}
	}
	
	/*
	 * 初始化游戏
	 */
	public static void init(){
	
		System.out.print("请选择2-4位不重复玩家，"
				+ "输入编号即可（1.钱夫人；2.阿土伯；3.孙小美；4.金贝贝）不要空格如12:");	
		String m = input.nextLine();
		member = m.length();
		alive_member = member;		
		
		char[] ch = new char[member];
		for(int i=0; i<member; i++){
			try{
				ch[i] = m.charAt(i);
				int b = Integer.parseInt(m.charAt(i)+"");
				if(b>4||b<1){
					System.out.println("输入玩家编号不对，应为1~4");
					init();
					return;
				}
			}catch(Exception e){
				System.out.println("输入非法字符,请重新输入");
				init();
				return;
			}
		}
		if(member<2||member>4){
			System.out.println("输入人数不对或输入了空格,请重新输入");
			init();
			return;
		}
		//判断输入编号是否有重复
		for(int i=0;i<member;i++){
			for(int j=i+1;j<member;j++){
				if(ch[i]==ch[j]){
					System.out.println("输入的编号重复,请重新输入.");
					init();
					return;
				}
			}
		}
		players = new Player[member];	
		for(int i=0; i<member; i++){
			players[i] = new Player();
			int id = Integer.parseInt(m.charAt(i)+"");
			players[i].setName(names[id]);		
			players[i].setSymbol(symbols[id-1]);
		}
		init_money();
	}
	
	//初始化游戏资产
	public static void init_money(){
		System.out.print("设置玩家初始资金，范围1000~50000（默认10000）:");
		String money = input.nextLine();
		try{
			if(money.length()!=0){
				if(Integer.parseInt(money)>50000||Integer.parseInt(money)<1000){
					System.out.println("请设置在1000~50000范围内的数值。");
					init_money();
					return;
				}
			}
		}catch(Exception e){
			System.out.println("输入非法字符，请重新输入");
			init_money();
			return;
		}
		for(int i=0; i<member; i++){
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
			players[i].setStayBout(--stayBout);		
			System.out.println(players[i].getName()+"跳过本回合,还需跳过"+
					players[i].getStayBout()+"回合");
			return;
		}
		//指定玩家输入指令
		System.out.print(players[i].getName()+">:");
		//读取控制台一行
		String[] orders = input.nextLine().trim().split(" ");
		String order = orders[0];
		if(orders.length==2){
			try{
				step = Integer.parseInt(orders[1].trim());		
			}catch(Exception e){
				System.out.println("非法字符输入，请重新输入");
				ing(i);
				return;
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
			}else if("step".equalsIgnoreCase(order)){
				exist_step(i);
				int n = Control.step(step);
				do_roll(i,n);
			}else{
				error(i);
			}
		}else if(orders.length==1){
			if("robot".equalsIgnoreCase(order)){
				if(orders.length!=1){
					error(i);
				}
				Control.robot(players[i]);
				ing(i);
				return;
			}else if("query".equalsIgnoreCase(order)){
				if(orders.length!=1){
					error(i);
				}
				Control.query(players[i]);
				ing(i);
				return;
			}else if("help".equalsIgnoreCase(order)){
				if(orders.length!=1){
					error(i);
				}
				Control.help();
				ing(i);
				return;
			}else if("quit".equalsIgnoreCase(order)){
				if(orders.length!=1){
					error(i);
				}
				Control.quit();
			}else if("roll".equalsIgnoreCase(order)){
				if(orders.length!=1){
					error(i);
				}
				//调用Roll方法生成一个1~6的随机数
				int n = Control.roll();
				do_roll(i,n);
			}else{
				error(i);
			}
		}else{
			error(i);
		}
		for(int j=0;j<member;j++){
			int cur_position = players[j].getPosition();
			Ground cur_ground = Map.map.get(cur_position);
			cur_ground.setSymbol(players[j].getSymbol());
		}		
	}
	
	public static void do_roll(int i,int n){
		//显示骰子的数值
		System.out.println("骰子的点数是"+n);
		n = Operation.exist_block(n, players[i]);
		//获取当前玩家当前位置			
		int current = players[i].getPosition();
		//获取玩家目标位置的标识
		Ground aim_ground = Map.map.get(Operation.real_position(n+current));	
		//玩家走到目标位置
		players[i].setPosition(Operation.real_position(n+current));
		//判断目标位置的情况，及相应的处理方法
		if(aim_ground.isBomb()){
			exist_bomb(aim_ground,i);
		}else if(aim_ground.isJail()){
			System.out.println("目标位置是监狱");
			exist_jail(i);
		}else if(aim_ground.isGiftHouse()){
			System.out.println("目标位置是礼品屋");
			exist_giftHouse(i);
		}else if(aim_ground.isTool()){
			System.out.println("欢迎光临道具屋，请选择您所需要的道具.");		
			System.out.println("通过输入道具的编号选择道具，每位玩家最多可以拥有10个道具");
			System.out.println("1.路障 50；2.机器娃娃 50；3.炸弹 50");
			System.out.println("购买格式（编号 数量）：如:1 2");
			System.out.println("按F键退出道具屋。");
			exist_toolHouse(i);
		}else if(aim_ground.getMine()!=null){
			System.out.println("目标位置是矿地");
			exist_mine(aim_ground,i);
		}else if(aim_ground.getHouse()!=null){
			System.out.println("目标位置是一栋房子");
			exist_house(aim_ground,i);
		}else if(aim_ground.getPreSymbol()=='S'){
			System.out.println("回到起点");
		}else if(aim_ground.getPreSymbol()=='M'){
			System.out.println("到达魔法屋，魔法屋正在建设中");
		}else if(aim_ground.getPreSymbol()=='H'){
			System.out.println("到达医院");
		}else {
//			System.out.println("目标位置是空地");
			System.out.print("该位置是块空地，是否购买？Y/N：");
			exist_empty(aim_ground,i);
		}
		//修改玩家目的地的标识
//		aim_ground.setSymbol(players[i].getSymbol());
		//将玩家当前位置改回之前的状态
		Ground cur_ground = Map.map.get(current);
		cur_ground.setSymbol(cur_ground.getPreSymbol());
	}
	
	//判断目标位置是炸弹操作
	public static void exist_bomb(Ground aim_ground,int i){
		players[i].setStayBout(3);
		players[i].setPosition(14);
		aim_ground.setSymbol(aim_ground.getPreSymbol());
		aim_ground.setBomb(false);
		System.out.println("不幸踩到炸弹,进医院休息3天");
		return;
	}
	
	
	//目标位置是监狱的操作
	public static void exist_jail(int i){
		players[i].setStayBout(2);
		System.out.println("被抓入监狱2天");
		return;
	}
	
	//目标位置是礼品屋的操作
	public static void exist_giftHouse(int i){
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
	
	//目标位置是道具屋的操作
	public static void exist_toolHouse(int i){	
		if(players[i].getPoint()<50){
			System.out.println("您的点数不足，自动退出");
			return;
		}	
		int num = players[i].getBlockNum()+players[i].getBombNum()+players[i].getRobotNum();
		if(num<10){
			String in = input.nextLine();
			if("F".equalsIgnoreCase(in))
				return;
			String[] ins = in.split(" ");
			if(ins.length!=2){
				System.out.println("输入格式不正确，请重新输入：");
				exist_toolHouse(i);
				return;
			}
			int biao = Integer.parseInt(ins[0].trim());
			try{
				int n = Integer.parseInt(ins[1].trim());
				if(n<0){
					System.out.print("输入数量不对，请重新输入:");
					exist_toolHouse(i);
					return;
				}
				if(num+n>10){
					System.out.println("输入的数量超过最大允许携带限额，购买无效，请重新输入。");
					exist_toolHouse(i);
					return;
				}else{				
					int points = 50*n;
					if(points>players[i].getPoint()){
						System.out.println("点数不足以购买所要物品数量，请重新输入");
						exist_toolHouse(i);
						return;
					}else if(biao==1){
						players[i].setBlockNum(players[i].getBlockNum()+n);
						players[i].setPoint(players[i].getPoint()-points);
						System.out.println("已购买路障卡"+n+"个，共花费"+points+"点数");
						System.out.println("输入F退出，或继续购买:");
						exist_toolHouse(i);
						return;
					}else if(biao==2){
						players[i].setRobotNum(players[i].getRobotNum()+n);
						players[i].setPoint(players[i].getPoint()-points);
						System.out.println("已购买机器娃娃卡"+n+"个，共花费"+points+"点数");
						System.out.println("输入F退出，或继续购买:");
						exist_toolHouse(i);
						return;
					}else if(biao==3){
						players[i].setBombNum(players[i].getBombNum()+n);
						players[i].setPoint(players[i].getPoint()-points);
						System.out.println("已购买炸弹卡"+n+"个，共花费"+points+"点数");
						System.out.println("输入F退出，或继续购买:");
						exist_toolHouse(i);
						return;
					}else{
						System.out.println("输入物品标号错误，请重新输入.");
						exist_toolHouse(i);
						return;
					}
				}
			}catch(Exception e){
				System.out.println("非法字符输入，请重新输入：");
				exist_toolHouse(i);
				return;
			}
		}else{
			System.out.println("您的道具数量已满，自动退出");
			return;
		}			
	}
	
	//目标位置是矿地的操作
	public static void exist_mine(Ground aim_ground,int i){
		int points = aim_ground.getMine().getPoint();
		players[i].setPoint(players[i].getPoint()+points);
		System.out.println("路过矿地，点数增加"+points);
	} 
	
	//目标位置是房产的操作
	public static void exist_house(Ground aim_ground,int i){
		House house = aim_ground.getHouse();
		if(house.getName().equals(players[i].getName())){
			if(house.getGrade()==3)
				System.out.println("房子已到最高级，无法升级");
			else{
				System.out.println("当前房产等级为"+house.getGrade()+" 是否升级房产：（Y/N)");
				String in = input.nextLine();
				if("y".equalsIgnoreCase(in.trim())){
					int price = aim_ground.getPrice();
					if(price>players[i].getCash())
						System.out.println("现金不足无法升级!");
					else{
						players[i].setCash(players[i].getCash()-price);
						house.setGrade(house.getGrade()+1);
						house.setPrice(house.getPrice()+price);
						aim_ground.setPreSymbol((char)(house.getGrade()+48));
						aim_ground.setSymbol((char)(house.getGrade()+48));
						System.out.println("升级房产成功！当前等级为"+house.getGrade());
					}
				}
			}
		}else{
			int mammon = players[i].getMammon();
			if(mammon!=0){
				System.out.println("财神附身，可免过路费,剩余次数"+(--mammon));
				players[i].setMammon(--mammon);
			}else{
				int price = house.getPrice()/2;
				if(price>players[i].getCash()){
					System.out.println("抱歉，现金不足，您的游戏结束。");
					List<House> property = players[i].getProperty();
					for(House h :property){
						Ground ground =Map.map.get(h.getPosition());
						h.setPrice(0);
						h.setPosition(0);
						h.setGrade(0);
						ground.setPreSymbol('0');
						ground.setHouse(null);
					}
					players[i].setAlive(false);
					alive_member-=1;
				}else{
					players[i].setCash(players[i].getCash()-price);
					for(Player p : players){
						if(p.getName().equals(house.getName())){
							p.setCash(p.getCash()+price);
							break;
						}
					}
					System.out.println("路过他人房产,缴费"+price);
				}
			}
		}
	}
	
	//目标位置是空地的操作
	public static void exist_empty(Ground aim_ground,int i){
		String in = input.nextLine();
		if("y".equalsIgnoreCase(in.trim())){
			int price = aim_ground.getPrice();
			if(price>players[i].getCash()){
				System.out.println("现金不够，无法购买。");
				return;
			}else{
				players[i].setCash(players[i].getCash()-price);
				House h = new House();
				h.setPosition(aim_ground.getPosition());
				h.setPrice(price);
				h.setGrade(1);
				h.setName(players[i].getName());
				players[i].getProperty().add(h);
				aim_ground.setPreSymbol('1');
				aim_ground.setSymbol('1');
				aim_ground.setHouse(h);
				System.out.println("购买成功！");
			}
		}else if("n".equalsIgnoreCase(in.trim())){
			System.out.println("放弃购买资格.");
		}else{
			System.out.print("非法输入，请重新输入");
			exist_empty(aim_ground,i);
			return;
		}
	}
	//判断命令输入格式
	public static void exist_step(int i){
		if(step==0){
			System.out.println("命令格式有错或参数不能为负数或0,请输入Help命令查询或重新输入.");
			ing(i);
			return ;
		}
	}
	
	//提示命令格式错误
	public static void error(int i){
		System.out.println("命令格式有错误，请输入Help命令查询或重新输入.");
		ing(i);
		return;
	}
}