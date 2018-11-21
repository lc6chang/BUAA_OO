package oo10;

public class TaxiList {
	/*Overview:这个类是出租车列表类，没什么特殊的，存储了100个出租车
	 * 
	 */
	Taxi[] list;
    /*@repOk.
    check:\all int i;0<=i<100==>list[i].repOk();
    */
    public boolean repOk(){
        /*
        @EFFECTS:\result = invariant(this)
        */
    	
    	for(int i=0;i<100;i++) {
    		if(!list[i].repOk()) {
    			return false;
    		}
    	}
    	return true;
	}
	public TaxiList(Global global,CarMap map,TaxiGUI gui,Light light,LightThread lighthread) {
		/**@REQUIRES:Global global,CarMap map,TaxiGUI gui,Light light,LightThread lighthread;
		   @MODIFIES:this.list;
		   @EFFECTS:构造100个Taxi对象存至list;
		 */
		list=new Taxi[100];
		for(int i=0;i<100;i++) {
			list[i]=new Taxi(global,map,gui,light,lighthread,i);
		}
	}
	public void start(long time) {
		/**@REQUIRES:long time;
		   @MODIFIES:this.list;
		   @EFFECTS:\all int i;0<=i<100==>list[i].systemtime==time;
		   			\all int i;0<=i<100==>list[i].isAlive;
		   			true==>System.out;
		 */
		for(int i=0;i<100;i++) {
			list[i].setime(time);
			list[i].start();
		}
		System.out.println("Taxi is start!");
	}
}
