package oo10;

import java.awt.Point;
import java.util.Random;

public class GetRandom {
	/*Overview:这个类同样只提供静态方法，它提供了随机位置，随机移动，红绿灯的随机初始值，以及红绿灯随机时间的方法
	 * 
	 */

    /*@repOk.
    check:None
    */
    public boolean repOk(){
        /*
        @EFFECTS:\result = invariant(this)
        */
    	return true;//////////////这个类也是啥也没有，应该都是true
	}
	static Point randomlocation() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result==一个(0,0)-(79,79)的随机坐标;
		 */
		Random ran=new Random();
		int x=ran.nextInt(80);
		int y=ran.nextInt(80);
		Point p=new Point(x,y);
		return p;
	}
	static Point randomove(CarMap map,Point point) {
		/**@REQUIRES:CarMap map,Point point;
		 			 map已经加载完毕正确的地图;
		 			 point点不超出(0,0)-(79,79)范围且不能是孤立的;
		   @MODIFIES:None;
		   @EFFECTS:\result==处在point点的出租车按最小流量原则随机移动的下一个坐标;
		 */
		int boot=point.x*80+point.y;
		int[] offset=new int[]{1,-1,80,-80};
		int[] candidate=new int[4];
		int[] volume=new int[4];
		int len=0;
		int minvolume=10000000;
		for(int i=0;i<4;i++) {
			int newboot=boot+offset[i];
			if(newboot>=0&&newboot<6400&&map.getmatrix(boot,newboot)==1) {
				volume[i]=map.getvolume(boot,newboot);
				if(volume[i]<minvolume) {
					minvolume=volume[i];
				}
			}
		}
		for(int i=0;i<4;i++) {
			int newboot=boot+offset[i];
			if(newboot>=0&&newboot<6400&&map.getmatrix(boot,newboot)==1) {
				if(volume[i]==minvolume) {
					candidate[len++]=newboot;
				}
			}
		}
		Random ran=new Random();
		int x=ran.nextInt(len);
		int y=candidate[x];
		Point p=new Point(y/80,y%80);
		return p;
	}
	public static int randomlight() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result==1或2的随机数
		 */
		Random ran=new Random();
		int x=ran.nextInt(2);
		return x+1;
	}
	public static long randomtime() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result==一个0-500的随机数
		 */
		Random ran=new Random();
		long x=ran.nextInt(501);
		return x;
	}
}
