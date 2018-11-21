package oo11;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PassengerThread extends Thread{
	/*Overview:这个类是乘客请求输入线程类，通过控制台读入请求，判断并加入请求队列；读取开关道路信息，判断并开关道路
	 * 
	 */
	private CarMap map;
	private Global global;
	private RequestList requestlist;
	private TaxiGUI gui;
	public PassengerThread(CarMap c,Global g,RequestList r,TaxiGUI gu) {
		/**@REQUIRES:CarMap c,Global g,RequestList r,TaxiGUI gu;
		   @MODIFIES:this;
		   @EFFECTS:this.map==c&&this.global==g&&this.requestlist==r&&this.gui==gu;
		 */
		map=c;
		global=g;
		requestlist=r;
		gui=gu;
	}
    /*@repOk.
    check:1.this.map!=null&&this.global!=null&&this.requestlist!=null;
    	  2.this.map.repOk()&&this.global.repOk()&&this.requestlist.repOk();
    */
    public boolean repOk(){
        /*
        @EFFECTS:\result = invariant(this)
        */
    	if(map==null||global==null||requestlist==null) {
    		return false;
    	}
    	return map.repOk()&&global.repOk()&&requestlist.repOk();
	}
	public void getRequest(String readin,long nowtime) {
		/**@REQUIRES:String readin满足正则表达式"\\[CR,\\([0-9]{1,2},[0-9]{1,2}\\),\\([0-9]{1,2},[0-9]{1,2}\\)\\]";
		   @MODIFIES:this.requestlist;
		   @EFFECTS:数字超范围，相同起始点==>System.out&&return;
		   			不属于上述情况==>this.requestlist.add(request);
		 */
		String judge_number="[0-9]+";
		Pattern pat_number=Pattern.compile(judge_number);
		Matcher mat_number=pat_number.matcher(readin);
		int[] number=new int[4];
		for(int i=0;i<4;i++) {
			mat_number.find();
			number[i]=Integer.parseInt(mat_number.group(0));
			if(number[i]>=80) {
				System.out.println("Error in type!");
				return;
			}
		}
		Point start=new Point(number[0],number[1]);
		Point end=new Point(number[2],number[3]);
		if(start.equals(end)) {
			System.out.println("Same start point and target point!");
			return;
		}
		Request request=new Request(start,end,nowtime/100,readin);
		requestlist.searchadd(request);
		return;
	}
	public void addpath(String readin) {
		/**@REQUIRES:String readin满足正则表达式"ADD\\([0-9]{1,2},[0-9]{1,2}\\),\\([0-9]{1,2},[0-9]{1,2}\\)";
		   @MODIFIES:this.map,this.gui;
		   @EFFECTS:数字超范围||相同起始点||之前没有先关闭==>System.out&&return;
		   			不属于上述情况==>this.map.openpath&&this.gui.SetRoadStatus;
		 */
		String judge_number="[0-9]+";
		Pattern pat_number=Pattern.compile(judge_number);
		Matcher mat_number=pat_number.matcher(readin);
		int[] number=new int[4];
		for(int i=0;i<4;i++) {
			mat_number.find();
			number[i]=Integer.parseInt(mat_number.group(0));
			if(number[i]>=80) {
				System.out.println("Error in type!");
				return;
			}
		}
		Point start=new Point(number[0],number[1]);
		Point end=new Point(number[2],number[3]);
		if(map.getmatrix(start.x*80+start.y,end.x*80+end.y)!=2) {
			System.out.println("Can't be opened!");
			return;
		}
		map.openpath(start,end);
		gui.SetRoadStatus(start,end,1);
		return;
	}
	public void removepath(String readin) {
		/**@REQUIRES:String readin满足正则表达式"REMOVE\\([0-9]{1,2},[0-9]{1,2}\\),\\([0-9]{1,2},[0-9]{1,2}\\)";
		   @MODIFIES:this.map,this.gui;
		   @EFFECTS:数字超范围||相同起始点||图上原本不连接==>System.out&&return;
		   			不属于上述情况==>this.map.closepath&&this.gui.SetRoadStatus;
		 */
		String judge_number="[0-9]+";
		Pattern pat_number=Pattern.compile(judge_number);
		Matcher mat_number=pat_number.matcher(readin);
		int[] number=new int[4];
		for(int i=0;i<4;i++) {
			mat_number.find();
			number[i]=Integer.parseInt(mat_number.group(0));
			if(number[i]>=80) {
				System.out.println("Error in type!");
				return;
			}
		}
		Point start=new Point(number[0],number[1]);
		Point end=new Point(number[2],number[3]);
		if(map.getmatrix(start.x*80+start.y,end.x*80+end.y)!=1) {
			System.out.println("Can't be closed!");
			return;
		}
		map.closepath(start,end);
		gui.SetRoadStatus(start,end,0);
		return;
	}
	public boolean getString(String readin,long nowtime) {
		/**@REQUIRES:String readin,long nowtime;
		   @MODIFIES:this;
		   @EFFECTS:readin.equals("STOP")==>\result==true;
		   			readin满足正则表达式"\\[CR,\\([0-9]{1,2},[0-9]{1,2}\\),\\([0-9]{1,2},[0-9]{1,2}\\)\\]"==>调用getRequest&&\result==false;
		   			readin满足正则表达式"ADD\\([0-9]{1,2},[0-9]{1,2}\\),\\([0-9]{1,2},[0-9]{1,2}\\)"==>调用addpath&&\result==false;
		   			readin满足正则表达式"REMOVE\\([0-9]{1,2},[0-9]{1,2}\\),\\([0-9]{1,2},[0-9]{1,2}\\)"==>调用removepath&&\result==false;
		   			不属于以上情况==>System.out&&\result==false;
		   			
		 */
		if(readin.equals("STOP")) {
			return true;
		}
		String judge_request="\\[CR,\\([0-9]{1,2},[0-9]{1,2}\\),\\([0-9]{1,2},[0-9]{1,2}\\)\\]";
		Pattern pat_request=Pattern.compile(judge_request);
		Matcher mat_request=pat_request.matcher(readin);
		boolean bool_request=mat_request.matches();
		if(bool_request) {
			getRequest(readin,nowtime);
			return false;
		}
		String judge_add="ADD\\([0-9]{1,2},[0-9]{1,2}\\),\\([0-9]{1,2},[0-9]{1,2}\\)";
		Pattern pat_add=Pattern.compile(judge_add);
		Matcher mat_add=pat_add.matcher(readin);
		boolean bool_add=mat_add.matches();
		if(bool_add) {
			addpath(readin);
			return false;
		}
		String judge_remove="REMOVE\\([0-9]{1,2},[0-9]{1,2}\\),\\([0-9]{1,2},[0-9]{1,2}\\)";
		Pattern pat_remove=Pattern.compile(judge_remove);
		Matcher mat_remove=pat_remove.matcher(readin);
		boolean bool_remove=mat_remove.matches();
		if(bool_remove) {
			removepath(readin);
			return false;
		}
		System.out.println("Error in type!");
		return false;
	}
	public void run() {
		/**@REQUIRES:None;
		   @MODIFIES:this;
		   @EFFECTS:从控制台读入，判断;
		 */
		System.out.println("Passenger thread is start!");
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String readin=null;
		while(true){
			try{
				readin=br.readLine();
			}catch(IOException e){
				System.out.println("IOerror");
			}
			if(getString(readin,System.currentTimeMillis())) {
				break;
			}
		}
		global.end();
		System.out.println("Passenger thread is end!");
	}
}
