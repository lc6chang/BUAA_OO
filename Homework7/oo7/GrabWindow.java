package oo7;

import java.util.ArrayList;

public class GrabWindow extends Thread{
	private Global global;
	private ShortestPath path;
	private TaxiList taxilist;
	private Request request;
	private ArrayList<Integer> candidate;
	public GrabWindow(Global g,ShortestPath p,TaxiList t,Request r) {
		global=g;
		path=p;
		taxilist=t;
		request=r;
		candidate=new ArrayList<Integer>();
	}
	public void run() {
		String log="------------------------------------------------------\r\n";
		log=log+"抢票窗口启动时间："+System.currentTimeMillis()/100+"(100ms)\r\n";
		log=log+"<乘客请求>：\r\n发出时间："+request.getime()+"(100ms)\r\n请求坐标：("+request.getstart().x+","+request.getstart().y+")\r\n目的地坐标：("+request.getend().x+","+request.getend().y+")\r\n<抢单车辆>：\r\n";
		long oldtime=System.currentTimeMillis();
		while((System.currentTimeMillis()-oldtime)<3000) {
			////////开始将该区域的车辆加入候选队列////////
			ArrayList<Integer> newcandidate=Search.byarea(request.getstart(),taxilist);
			ArrayList<Integer> diff=Hashdiff.diff(candidate,newcandidate);
			/////////增加信用并记录////////////
			for(int i=0;i<diff.size();i++) {
				if(taxilist.list[diff.get(i)].getstate()!=1) {
					continue;
				}
				candidate.add(diff.get(i));
				taxilist.list[diff.get(i)].addcredit(1);
			}
		}
		try {
			sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
				if(temp.getstate()!=1) {
					continue;
				}
				if(temp.getcredit()<best_credit) {
					continue;
				}
				if(temp.getcredit()>best_credit) {
					best_id=temp.getid();
					best_credit=temp.getcredit();
					best_distance=path.get(temp.getlocation(),request.getstart()).size();
					continue;
				}
				if(temp.getcredit()==best_credit&&path.get(temp.getlocation(),request.getstart()).size()<best_distance) {
					best_id=temp.getid();
					best_credit=temp.getcredit();
					best_distance=path.get(temp.getlocation(),request.getstart()).size();
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
