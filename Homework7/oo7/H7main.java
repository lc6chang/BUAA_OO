package oo7;

public class H7main {

	public static void main(String[] args) {
		////////////////初始化全局控制///////////////////////////
		Global global=new Global();
		////////////////初始化地图///////////////////////////////
		CarMap map=new CarMap();
		map.setmap("./map.txt");
		map.initmatrix();
		///////////////初始化最短路径////////////////////////////
		ShortestPath path=new ShortestPath();
		path.setpath(map);
		///////////////初始化请求容器////////////////////////////
		RequestList requestlist=new RequestList();
		///////////启动GUI///////////////////////////////////////
		TaxiGUI gui=new TaxiGUI();	
		gui.LoadMap(map.wholemap(),80);
		//////////////启动出租车/////////////////////////////////
		TaxiList taxilist=new TaxiList(global,map,path,gui);
		taxilist.start();
		///////////////启动监督线程/////////////////////////////
		Monitor monitor=new Monitor(global,path,taxilist,requestlist,gui);
		monitor.start();
		//////////////启动乘客请求线程////////////////////////////
		PassengerThread passenger=new PassengerThread(global,requestlist);
		passenger.start();
		//////////////要是想查询状态在这个后面直接调用Search类的方法或者新写一个线程都行~//////////////////////////
		
	}

}
