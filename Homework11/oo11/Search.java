package oo11;

import java.awt.Point;
import java.util.ArrayList;

public class Search {
	/*Overview:这个类也只有一个静态方法，用来获得某点4*4范围内的所有车辆编号
	 * 
	 */
    /*@repOk.
    check:这个也不用检查
    */
    public boolean repOk(){
        /*
        @EFFECTS:\result = invariant(this)
        */
    	return true;
	}
	static ArrayList<Integer> byarea(Point p,TaxiList taxilist){
		/**@REQUIRES:Point p,TaxiList taxilist;
		 			 p在(0,0)-(79,79)范围内;
		   @MODIFIES:None;
		   @EFFECTS:\result==所有在以p为中心4*4范围&&状态为等待接客的出租车id组成的ArrayList;
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
