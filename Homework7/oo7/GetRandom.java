package oo7;

import java.awt.Point;
import java.util.Random;

public class GetRandom {
	static Point randomlocation() {
		Random ran=new Random();
		int x=ran.nextInt(80);
		int y=ran.nextInt(80);
		Point p=new Point(x,y);
		return p;
	}
	static Point randomove(CarMap map,Point point) {
		int boot=point.x*80+point.y;
		int[] offset=new int[]{1,-1,80,-80};
		int[] candidate=new int[4];
		int len=0;
		for(int i=0;i<4;i++) {
			int newboot=boot+offset[i];
			if(newboot>=0&&newboot<6400&&map.getmatrix(boot,newboot)==1) {
				candidate[len++]=boot+offset[i];
			}
		}
		Random ran=new Random();
		int x=ran.nextInt(len);
		int y=candidate[x];
		Point p=new Point(y/80,y%80);
		return p;
	}
}
