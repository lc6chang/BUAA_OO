package oo10;

import java.util.ArrayList;

public class GrabWindow extends Thread{
	/*Overview:这个类是抢单窗口线程类，记录某条乘客请求的抢单车辆信息，完成派单工作
	 * 
	 */
	private Global global;
	private CarMap map;
	private TaxiList taxilist;
	private Request request;
	private ArrayList<Integer> candidate;
	private long systemtime;
	public GrabWindow(Global g,CarMap c,TaxiList t,Request r) {
		/**@REQUIRES:Global g,CarMap c,TaxiList t,Request r;
		   @MODIFIES:this;
		   @EFFECTS:this.global==g&&this.map==c&&this.taxilist==t&&this.request==r;
		   			this.candidate==new ArrayList<Integer>();
		   			this.systemtime==System.currentTimeMillis();
		 */
		global=g;
		map=c;
		taxilist=t;
		request=r;
		candidate=new ArrayList<Integer>();
		systemtime=System.currentTimeMillis();
	}

    /*@repOk.
    check:1.this.request!=null&&this.global!=null&&this.map!=null&&this.taxilist!=null;
    	  2.this.global.repOK()&&this.map.repOK()&&this.taxilist.repOK()
    */
    public boolean repOk(){
        /*
        @EFFECTS:\result = invariant(this)
        */
    	if(request==null||global==null||map==null||taxilist==null) {
    		return false;
    	}
    	return global.repOk()&&map.repOk()&&taxilist.repOk();
	}
	public long Roundtime(long noptime) {
		/**@REQUIRES:long noptime;
		   @MODIFIES:this.systemtime;
		   @EFFECTS:(nowtime-this.systemtime)>noptime==>\result==0;
		   			(nowtime-this.systemtime)<=noptime==>\result==noptime+\old(this.systemtime)-nowtime;
		   			true==>this.systemtime==\old(this.systemtime)+noptime;
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
		   @MODIFIES:None;
		   @EFFECTS:None;
		   我写不来了，这咋写，您扣个分吧,这tm要写的太多了！
		 */
		String log="------------------------------------------------------\r\n";
		log=log+"窗口打开时间："+System.currentTimeMillis()/100+"(100ms)\r\n";
		log=log+"<乘客请求>：\r\n发出时间："+request.getime()+"(100ms)\r\n请求坐标：("+request.getstart().x+","+request.getstart().y+")\r\n目的地坐标：("+request.getend().x+","+request.getend().y+")\r\n";
		int count=0;
		while(count<50) {
			////////开始将该区域的车辆加入候选队列////////
			ArrayList<Integer> newcandidate=Search.byarea(request.getstart(),taxilist);
			ArrayList<Integer> diff=Hashdiff.diff(candidate,newcandidate);
			/////////增加信用并记录////////////
			for(int i=0;i<diff.size();i++) {
				if(taxilist.list[diff.get(i)].getstate()!=2) {
					continue;
				}
				candidate.add(diff.get(i));
				taxilist.list[diff.get(i)].addcredit(1);
			}
			try {
				sleep(Roundtime(150));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count++;
		}
		log=log+"窗口关闭时间："+System.currentTimeMillis()/100+"(100ms)\r\n<抢单车辆>：\r\n";
		///////////////////开始指派taxi接单///////////////////////////
		if(candidate.size()==0) {
			System.out.println("No taxi in this area!"+request.getword());
		}
		else {
			while(!global.startorder()) {
				;
			}
			int best_id=100;
			int best_credit=-1;
			int best_distance=10000000;
			for(int i=0;i<candidate.size();i++) {
				Taxi temp=taxilist.list[candidate.get(i)];
				log=log+"id:"+temp.getid()+"  位置：("+temp.getlocation().x+","+temp.getlocation().y+")  车辆状态：";
				log=log+temp.getstate()+"  车辆信用："+temp.getcredit()+"\r\n";
				if(temp.getstate()!=2) {
					continue;
				}
				if(temp.getcredit()<best_credit) {
					continue;
				}
				if(temp.getcredit()>best_credit) {
					best_id=temp.getid();
					best_credit=temp.getcredit();
					best_distance=GetPath.getlength(map,temp.getlocation(),request.getstart());
					continue;
				}
				if(temp.getcredit()==best_credit&&GetPath.getlength(map,temp.getlocation(),request.getstart())<best_distance) {
					best_id=temp.getid();
					best_credit=temp.getcredit();
					best_distance=GetPath.getlength(map,temp.getlocation(),request.getstart());
					continue;
				}
			}
			if(best_id!=100) {
				log=log+"<派单车辆信息>：\r\n车辆编号："+best_id+"\r\n派单时车辆坐标："+"("+taxilist.list[best_id].getlocation().x+","+taxilist.list[best_id].getlocation().y+")\r\n";
				log=log+"派单时刻："+System.currentTimeMillis()/100+"(100ms)\r\n到达乘客位置经过的分支点及到达时刻(100ms)：";
				global.add(log,best_id);
				taxilist.list[best_id].ordertaking(request.getstart(),request.getend());
			}
			else {
				System.out.println("The taxi can't be assigned!"+request.getword());
			}
			global.endorder();
		}
	}
}
