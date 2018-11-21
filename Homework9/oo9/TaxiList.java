package oo9;

public class TaxiList {
	Taxi[] list;
	public TaxiList(Global global,CarMap map,TaxiGUI gui) {
		/**@REQUIRES:Global global,CarMap map,TaxiGUI gui;
		   @MODIFIES:this.list;
		   @EFFECTS:构造100个Taxi对象存至list;
		 */
		list=new Taxi[100];
		for(int i=0;i<100;i++) {
			list[i]=new Taxi(global,map,gui,i);
		}
	}
	public void start() {
		/**@REQUIRES:None;
		   @MODIFIES:this.list;
		   @EFFECTS:为每个出租车setime&&启动出租车线程&&System.out;
		 */
		long time=System.currentTimeMillis();
		for(int i=0;i<100;i++) {
			list[i].setime(time);
			list[i].start();
		}
		System.out.println("Taxi is start!");
	}
}
