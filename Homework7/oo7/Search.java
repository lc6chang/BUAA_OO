package oo7;

import java.awt.Point;
import java.util.ArrayList;

public class Search {
	static ArrayList<Integer> byarea(Point p,TaxiList taxilist){
		ArrayList<Integer> list=new ArrayList<Integer>();
		for(int i=0;i<100;i++) {
			Point temp=taxilist.list[i].getlocation();
			if(Math.abs(p.x-temp.x)<=2&&Math.abs(p.y-temp.y)<=2&&taxilist.list[i].getstate()==1) {
				list.add(i);
			}
		}
		return list;
	}
	static void bystate(int state,TaxiList taxilist){
		System.out.print("查询的状态"+state+"的汽车id有:");
		for(int i=0;i<100;i++) {
			if(taxilist.list[i].getstate()==state) {
				System.out.print(i+" ");
			}
		}
		System.out.print("\n");
	}
	static void byid(int id,TaxiList taxilist) {
		System.out.println("查询时间："+System.currentTimeMillis());
		System.out.println("查询id："+taxilist.list[id].getid());
		System.out.println("出租车位置："+taxilist.list[id].getlocation());
		System.out.println("出租车状态："+taxilist.list[id].getstate());
	}
}
