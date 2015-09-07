package com.whut.richer.util;

import com.whut.richer.bean.Ground;
import com.whut.richer.bean.Player;
import com.whut.richer.map.Map;

/*
 * 该类用来表示玩家操作流程
 */
public class Operation {
	//重新定义玩家位置
	public static int real_position(int n){
		if(n>=0&&n<70)
			return n;
		else if(n>=70)
			return n - 70;
		else 
			return n+70;
	}
	//判断前方道路是否有路障，有返回距离，无为0
	public static int exist_block(int n,Player player){
		int distance = n;
		int position = player.getPosition();
		Ground ground;
		for(int i = 0; i<= n; i++ ){
			ground = Map.map.get(real_position(position+i));
			if(ground.isBlock()){
				distance = i;
				System.out.println("前方"+i+"处遇到路障，停在路障处");
				ground.setSymbol(ground.getPreSymbol());
				ground.setBlock(false);
				break;
			}
		}
		return distance;
	}
}
