package oo11;

import java.awt.Point;
import java.util.Vector;

public class GetPath {
	/*Overview:这个类只有两个静态方法，提供了获取最短路径值以及最短路径的下一个坐标的方法
	 * 
	 */

    /*@repOk.
    check:None
    */
    public boolean repOk(){
        /*
        @EFFECTS:\result = invariant(this)
        */
    	return true;///////这个类啥也没有，只有方法，应该啥时候都正确吧
	}
	static int getlength(CarMap map,Point startpoint,Point targetpoint,int id) {
		/**@REQUIRES:CarMap map,Point startpoint,targetpoint,int id;
		 		     0<=startpoint.x,startpoint.y,targetpoint.x,targetpoint.y<=79&&0<=id<100;
		   @MODIFIES:None;
		   @EFFECTS:\result==startpoint到targetpoint的最短路径值(包含了可追踪出租车和普通出租车两种情况);
		 */
		int start=startpoint.x*80+startpoint.y;
		int target=targetpoint.x*80+targetpoint.y;
		boolean[] view=new boolean[6405];
		Vector<Integer> queue = new Vector<Integer>();
		int[] offset=new int[]{1,-1,80,-80};
		queue.add(start);
		int[] length=new int[6405];
		while (queue.size() > 0) {
			int n = queue.get(0);
			view[n] = true;
			if(n==target) {
				break;
			}
			for (int i=0;i<4;i++) {
				int next=n+offset[i];
				if (id>=30&&next >= 0 && next < 6400 &&view[next]==false&&map.getmatrix(n,next) == 1) {
					view[next] = true;
					queue.add(next);// 加入遍历队列
					length[next]=length[n]+1;
				}
				else if (id<30&&next >= 0 && next < 6400 &&view[next]==false&&(map.getmatrix(n,next) == 1||map.getmatrix(n,next) == 2)) {
					view[next] = true;
					queue.add(next);// 加入遍历队列
					length[next]=length[n]+1;
				}
			}
			queue.remove(0);
		}
		return length[target];
	}
	static Point getpoint(CarMap map,Point startpoint,Point targetpoint) {
		/**@REQUIRES:CarMap map,Point startpoint,targetpoint;
	     			 0<=startpoint.x,startpoint.y,targetpoint.x,targetpoint.y<=79;
		   @MODIFIES:None;
		   @EFFECTS:\result==startpoint到targetpoint的最短路径最小流量的下一个坐标(只针对普通出租车);
		*/
		int start=startpoint.x*80+startpoint.y;
		int target=targetpoint.x*80+targetpoint.y;
		int[] pathvalue=new int[6405];
		int[] volume=new int[6405];
		for(int i=0;i<6400;i++) {
			pathvalue[i]=1000000;
			volume[i]=1000000;
		}
		int[] pathpoint=new int[6405];
		Vector<Integer> queue = new Vector<Integer>();
		int[] offset=new int[]{1,-1,80,-80};
		queue.add(start);
		pathvalue[start]=0;
		volume[start]=0;
		while (queue.size() > 0) {
			int n = queue.get(0);
			if(n==target) {
				break;
			}
			for (int i=0;i<4;i++) {
				int next=n+offset[i];
				if (next >= 0 && next < 6400 &&(pathvalue[n]+1)<=pathvalue[next]&&map.getmatrix(n,next) == 1) {
					if((pathvalue[n]+1)<pathvalue[next]) {
						queue.add(next);
						pathvalue[next]=pathvalue[n]+1;
						volume[next]=volume[n]+map.getvolume(next, n);
						pathpoint[next]=n;
					}
					else if((pathvalue[n]+1)==pathvalue[next]) {
						if((volume[n]+map.getvolume(next, n))<volume[next]) {
							pathvalue[next]=pathvalue[n]+1;
							volume[next]=volume[n]+map.getvolume(next, n);
							pathpoint[next]=n;
						}
					}
				}
			}
			queue.remove(0);// 退出队列
		}
		int temp=target;
		while(true) {
			if(pathpoint[temp]==start) {
				break;
			}
			temp=pathpoint[temp];
		}
		return new Point(temp/80,temp%80);
	}
	static Point NBgetpoint(CarMap map,Point startpoint,Point targetpoint) {
		/**@REQUIRES:CarMap map,Point startpoint,targetpoint;
	     			 0<=startpoint.x,startpoint.y,targetpoint.x,targetpoint.y<=79;
		   @MODIFIES:None;
		   @EFFECTS:\result==startpoint到targetpoint的最短路径最小流量的下一个坐标(只针对可追踪出租车);
		*/
		int start=startpoint.x*80+startpoint.y;
		int target=targetpoint.x*80+targetpoint.y;
		int[] pathvalue=new int[6405];
		int[] volume=new int[6405];
		for(int i=0;i<6400;i++) {
			pathvalue[i]=1000000;
			volume[i]=1000000;
		}
		int[] pathpoint=new int[6405];
		Vector<Integer> queue = new Vector<Integer>();
		int[] offset=new int[]{1,-1,80,-80};
		queue.add(start);
		pathvalue[start]=0;
		volume[start]=0;
		while (queue.size() > 0) {
			int n = queue.get(0);
			if(n==target) {
				break;
			}
			for (int i=0;i<4;i++) {
				int next=n+offset[i];
				if (next >= 0 && next < 6400 &&(pathvalue[n]+1)<=pathvalue[next]&&(map.getmatrix(n,next) == 1||map.getmatrix(n,next) == 2)) {
					if((pathvalue[n]+1)<pathvalue[next]) {
						queue.add(next);
						pathvalue[next]=pathvalue[n]+1;
						volume[next]=volume[n]+map.getvolume(next, n);
						pathpoint[next]=n;
					}
					else if((pathvalue[n]+1)==pathvalue[next]) {
						if((volume[n]+map.getvolume(next, n))<volume[next]) {
							pathvalue[next]=pathvalue[n]+1;
							volume[next]=volume[n]+map.getvolume(next, n);
							pathpoint[next]=n;
						}
					}
				}
			}
			queue.remove(0);// 退出队列
		}
		int temp=target;
		while(true) {
			if(pathpoint[temp]==start) {
				break;
			}
			temp=pathpoint[temp];
		}
		return new Point(temp/80,temp%80);
	}
}
