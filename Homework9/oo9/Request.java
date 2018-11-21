package oo9;

import java.awt.Point;

public class Request {
	private Point start;
	private Point end;
	private long time;
	private String word;
	public Request(Point s,Point e,long t,String w) {
		/**@REQUIRES:Point s,Point e,long t,String w;
		   @MODIFIES:this;
		   @EFFECTS:构造;
		 */
		start=s;
		end=e;
		time=t;
		word=w;
	}
	public Point getstart() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result=start;
		 */
		return start;
	}
	public Point getend() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result=end;
		 */
		return end;
	}
	public long getime() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result=time;
		 */
		return time;
	}
	public String getword() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result=word;
		 */
		return word;
	}
}
