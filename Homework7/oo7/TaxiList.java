package oo7;

public class TaxiList {
	Taxi[] list;
	public TaxiList(Global global,CarMap map,ShortestPath path,TaxiGUI gui) {
		list=new Taxi[100];
		for(int i=0;i<100;i++) {
			list[i]=new Taxi(global,map,path,gui,i);
		}
	}
	public void start() {
		for(int i=0;i<100;i++) {
			list[i].start();
		}
		System.out.println("Taxi is start!");
	}
}
