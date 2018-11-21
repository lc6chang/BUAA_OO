package oo9;

import java.awt.Point;
import java.util.ArrayList;

public class Search {
	static ArrayList<Integer> byarea(Point p,TaxiList taxilist){
		/**@REQUIRES:Point p,TaxiList taxilist;
		 			 p在(0,0)-(79,79);
		   @MODIFIES:None;
		   @EFFECTS:\result=所有在以p为中心4*4范围&&状态为等待接客的出租车id的ArrayList;
		 */
		ArrayList<Integer> list=new ArrayList<Integer>();
		for(int i=0;i<100;i++) {
			Point temp=taxilist.list[i].getlocation();
			if(Math.abs(p.x-temp.x)<=2&&Math.abs(p.y-temp.y)<=2&&taxilist.list[i].getstate()==2) {
				list.add(i);
			}
		}
		return list;
	}
}
