package oo11;

import java.awt.Point;
import java.util.ArrayList;

public class Log {
	/*Overview:这是日志类，其实就是一个日志的数据结构，保存了请求，位置，坐标等信息*/
	private Request request;
	private Point location;
	private ArrayList<Point> pickup;
	private ArrayList<Point> moveto;
	
	public Log() {
		/**@REQUIRES:None;
		   @MODIFIES:this;
		   @EFFECTS:this.request==new Request(new Point(0,0),new Point (0,0),0,"None");
		   			this.location==new Point(0,0);
		   			this.pickup==new ArrayList<Point>();
		   			this.moveto==new ArrayList<Point>();
		 */
		request=new Request(new Point(0,0),new Point (0,0),0,"None");
		location=new Point(0,0);
		pickup=new ArrayList<Point>();
		moveto=new ArrayList<Point>();
	}
    /*@repOk.
    check:1.this.request!=null&&this.location!=null;
    	  2.0<=this.location.x,this.location.y<80;
    	  3.this.request.repOK();
    */
    public boolean repOk(){
        /*
        @EFFECTS:\result = invariant(this)
        */
    	if(request==null||location==null) {
    		return false;
    	}
    	if(location.x<0||location.y<0||location.x>79||location.y>79) {
    		return false;
    	}
    	return request.repOk();
	}
	public void addpickup(Point a) {
		/**@REQUIRES:Point a;0<=a.x,a.y<80;
		   @MODIFIES:this.pickup;
		   @EFFECTS:this.pickup.add(a);
		 */
		pickup.add(a);
	}
	public void addmoveto(Point a) {
		/**@REQUIRES:Point a;0<=a.x,a.y<80;
		   @MODIFIES:this.moveto;
		   @EFFECTS:this.moveto.add(a);
		 */
		moveto.add(a);
	}
	public void setRequest(Request r) {
		/**@REQUIRES:Request r;
		   @MODIFIES:this.request;
		   @EFFECTS:this.request==r;
		 */
		request=r;
	}
	public void setLocation(Point a) {
		/**@REQUIRES:Point a;0<=a.x,a.y<80;
		   @MODIFIES:this.location;
		   @EFFECTS:this.location==a;
		 */
		location=a;
	}
	public String toString() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result==唉就是把这个日志信息按下面的格式写成字符串;
		 */
		String s="\r\n";
		s+="_____________________________________________________\r\n";
		s+="请求内容："+request.getword()+"\r\n";
		s+="请求时间："+request.getime()+"(100ms)\r\n";
		s+="乘客位置："+"("+request.getstart().x+","+request.getstart().y+")\r\n";
		s+="目的地："+"("+request.getend().x+","+request.getend().y+")\r\n";
		s+="抢到单（派单时）车辆位置："+"("+location.x+","+location.y+")\r\n";
		s+="前往接客经过的坐标：\r\n";
		for(int i=0;i<pickup.size();i++) {
			s+="("+pickup.get(i).x+","+pickup.get(i).y+")\r\n";
		}
		s+="前往目的地经过的坐标：\r\n";
		for(int i=0;i<moveto.size();i++) {
			s+="("+moveto.get(i).x+","+moveto.get(i).y+")\r\n";
		}
		s+="_____________________________________________________\r\n";
		return s;
	}
}
