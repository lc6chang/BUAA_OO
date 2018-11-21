package oo7;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Vector;

public class ShortestPath {
	private int[][] path;
	public ShortestPath() {
		path=new int[6400][6400];
	}
	public void setpath(CarMap map) {
		int[] offset=new int[]{0,1,-1,80,-80};
		boolean[] view = new boolean[6405];
		for(int boot=0;boot<6400;boot++) {
			Vector<Integer> queue = new Vector<Integer>();
			for (int i=0;i<6400;i++)
				view[i]=false;
			queue.add(boot);
			while (queue.size() > 0) {
				int n = queue.get(0);
				view[n] = true;
				for (int i=1;i<=4;i++) {
					int next=n+offset[i];
					if (next >= 0 && next < 6400 && view[next] == false && map.getmatrix(n,next) == 1) {
						view[next] = true;
						queue.add(next);// 加入遍历队列
						path[boot][next]=n;
					}
				}
				queue.remove(0);// 退出队列
			}
		}
		System.out.println("Shortest path is already!");
	}
	public ArrayList<Point> get(Point start,Point fina){
		ArrayList<Point> list=new ArrayList<Point>();
		int startnode=start.x*80+start.y;
		int finalnode=fina.x*80+fina.y;
		int temp=finalnode;
		while(temp!=startnode) {
			Point p=new Point(temp/80,temp%80);
			list.add(p);
			temp=path[startnode][temp];			
		}
		return list;
	}
}
