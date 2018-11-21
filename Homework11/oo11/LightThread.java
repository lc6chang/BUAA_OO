package oo11;

public class LightThread extends Thread{
	/*Overview:这是红绿灯控制线程类，控制红绿灯每隔随机时间进行改变
	 * 
	 */
	private Global global;
	private Light light;
	private long systemtime;
	private long changetime;
	public LightThread(Light _light,Global _global) {
		/**@REQUIRES:Light _light,Global _global;
		   @MODIFIES:this;
		   @EFFECTS:this.global==_global&&this.light==_light;
		   			this.changetime==0-500的随机数+500;
		 */
		global=_global;
		light=_light;
		changetime=GetRandom.randomtime()+500;
		System.out.println("The change time is "+changetime+"ms.");
	}
    /*@repOk.
    check:1.this.global!=null&&this.light!=null;
    	  2.this.global.repOk()&&this.light.repOk();
    	  3.500<=this.changetime<=1000;
    */
    public boolean repOk(){
        /*
        @EFFECTS:\result = invariant(this)
        */
    	if(global==null||light==null) {
    		return false;
    	}
    	if(changetime<500||changetime>1000) {
    		return false;
    	}
    	return global.repOk()&&light.repOk();
	}
	public void setsystemtime(long time) {
		/**@REQUIRES:long time;
		   @MODIFIES:this.systemtime;
		   @EFFECTS:this.systemtime==time;
		 */
		systemtime=time;
	}
	public long getsystemtime() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result==this.systemtime;
		 */
		return systemtime;
	}
	public long Roundtime(long noptime) {
		/**@REQUIRES:long noptime;
		   @MODIFIES:this.systemtime;
		   @EFFECTS:(nowtime-this.systemtime)>noptime==>\result==0;
		   			(nowtime-this.systemtime)<=noptime==>\result==noptime+this.systemtime-nowtime;
		   			true==>this.systemtime==\old(this.systemtime)+noptime;
		   @THREAD_REQUIRES:\locked(this.systemtime);
		   @THREAD_EFFECTS:None;
		 */
		long sleeptime;
		long nowtime=System.currentTimeMillis();
		if((nowtime-systemtime)>noptime) {
			sleeptime=0;
		}
		else {
			sleeptime=noptime+systemtime-nowtime;
		}
		systemtime+=noptime;
		return sleeptime;
	}
	public void run() {
		/**@REQUIRES:None;
		   @MODIFIES:this.light;
		   @EFFECTS:每隔changetime的时间，红绿灯改变一次;
		 */
		System.out.println("The light thread is start!");
		while(global.getend()==0) {
			try {
				sleep(Roundtime(changetime));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			light.changelight();
		}
		System.out.println("The light thread is end!");
	}
}
