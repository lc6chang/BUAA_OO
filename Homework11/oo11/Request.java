package oo11;

import java.awt.Point;

public class Request {
	/*Overview:这个类是请求类，实际上就是构造了一个请求，没什么特别的
	 * 
	 */
	private Point start;
	private Point end;
	private long time;
	private String word;
	public Request(Point s,Point e,long t,String w) {
		/**@REQUIRES:Point s,Point e,long t,String w;
		   @MODIFIES:this;
		   @EFFECTS:this.start==s&&this.end==e&&this.time==t&&this.word==w;
		 */
		start=s;
		end=e;
		time=t;
		word=w;
	}
    /*@repOk.
    check:我觉得一个请求好像没什么判断的吧.....它就是一个单纯的数据结构而已
    */
    public boolean repOk(){
        /*
        @EFFECTS:\result = invariant(this)
        */
    	return true;
	}
	public Point getstart() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result==this.start;
		 */
		return start;
	}
	public Point getend() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result==this.end;
		 */
		return end;
	}
	public long getime() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result==this.time;
		 */
		return time;
	}
	public String getword() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result==this.word;
		 */
		return word;
	}
}
