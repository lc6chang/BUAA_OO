package oo10;

public class H10main {
	/*Overview:这是主类......我觉得什么都不用写吧......
	 * 
	 */
	public static void main(String[] args) {
		////////////////初始化全局控制///////////////////////////
		Global global=new Global();
		////////////////初始化地图///////////////////////////////
		CarMap map=new CarMap();
		map.setmap("./map.txt");
		map.initmatrix();
		///////////////初始化请求容器////////////////////////////
		RequestList requestlist=new RequestList();
		///////////初始化GUI///////////////////////////////////////
		TaxiGUI gui=new TaxiGUI();
		gui.LoadMap(map.wholemap(),80);
		///////////初始化Light/////////////////////////////////////
		Light light=new Light(gui);
		light.initlight("./light.txt");
		///////////初始化LightThread//////////////////////////////
		LightThread lighthread=new LightThread(light,global);
		//////////////初始化出租车/////////////////////////////////
		TaxiList taxilist=new TaxiList(global,map,gui,light,lighthread);
		//////////////初始化监督线程//////////////////////////////
		Monitor monitor=new Monitor(global,map,taxilist,requestlist,gui);
		//////////////初始化乘客请求线程/////////////////////////////
		PassengerThread passenger=new PassengerThread(map,global,requestlist,gui);
		//////////////创建测试接口//////////////////////////////////
		LoadFile loadfile=new LoadFile(map,requestlist,taxilist);
		loadfile.test("./testfile.txt");
		/////////////启动出租车线程////////////////////////////////
		long time=System.currentTimeMillis();////将初始时间对齐，以便统一//////////////////////
		taxilist.start(time);
		//////////////启动红绿灯线程//////////////////////////////
		lighthread.setsystemtime(time);
		lighthread.start();
		///////////////启动监督线程/////////////////////////////		
		monitor.start();
		//////////////启动乘客请求线程////////////////////////////		
		passenger.start();
	}

}
