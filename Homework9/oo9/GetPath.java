package oo9;

import java.awt.Point;
import java.util.Vector;

public class GetPath {
	static int getlength(CarMap map,Point startpoint,Point targetpoint) {
		/**@REQUIRES:CarMap map,Point startpoint,targetpoint;
		 		     0<=startpoint.x,startpoint.y,targetpoint.x,targetpoint.y<=79;
		   @MODIFIES:None;
		   @EFFECTS:\result=startpoint到targetpoint的最短路径值;
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
				if (next >= 0 && next < 6400 &&view[next]==false&&map.getmatrix(n,next) == 1) {
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
		   @EFFECTS:\result=startpoint到targetpoint的最短路径最小流量的下一个坐标;
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
}
