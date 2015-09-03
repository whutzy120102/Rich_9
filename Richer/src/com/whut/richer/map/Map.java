package com.whut.richer.map;

import java.util.ArrayList;
import java.util.List;

import com.whut.richer.bean.Ground;
import com.whut.richer.bean.Mine;

/*
 * 描述地图
 * 初始化和打印地图
 */
public class Map {
	public static List<Ground> map = new ArrayList<Ground>();
	public Map(){
		init();
	}
	
	public void init(){
		/*
		 * 描述前64块空地  修改指定位置的空地属性
		 */
		for(int i=0; i<64; i++){
			Ground ground = new Ground();
			ground.setPosition(i);
			if(i==0){
				ground.setSymbol('S');
			}else if(i==14){
				ground.setSymbol('H');
				ground.setHospital(true);
			}else if(i==28){
				ground.setSymbol('T');
				ground.setTool(true);
			}else if(i==35){
				ground.setSymbol('G');
				ground.setGiftHouse(true);
			}else if(i==49){
				ground.setSymbol('P');
				ground.setJail(true);
			}else if(i==63){
				ground.setSymbol('M');
				ground.setMagicHouse(true);
			}
			map.add(ground);
		}
		
		/*
		 * 最后描述6块矿地
		 */
		Mine[] m = new Mine[6];
		for(int i=0;i<6;i++){
			m[i] = new Mine();
		}
		m[0].setPoint(20);
		m[1].setPoint(80);
		m[2].setPoint(100);
		m[3].setPoint(40);
		m[4].setPoint(80);
		m[5].setPoint(60);
		Ground[] ground = new Ground[6];
		for(int i=0;i<6;i++){
			ground[i] = new Ground();
			ground[i].setMine(m[i]);
			ground[i].setPosition(64+i);
			ground[i].setSymbol('$');
			map.add(ground[i]);
		}
	}
	
	/*
	 * 打印地图
	 */
	public static void printMap(List<Ground> map){
		for(int i=0;i<29;i++){
			System.out.print(map.get(i).getSymbol());
		}
		for(int i=0;i<6;i++){
			System.out.println();
			System.out.print(map.get(69-i).getSymbol()+
					"                           "
					+map.get(29+i).getSymbol());
		}
		System.out.println();
		for(int i=0; i<29; i++){
			System.out.print(map.get(63-i).getSymbol());
		}
		System.out.println();
	}
}
