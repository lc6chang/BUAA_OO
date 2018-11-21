package oo9;

import java.awt.Point;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoadFile {
	private CarMap map;
	private RequestList requestlist;
	private TaxiList taxilist;
	public LoadFile(CarMap m,RequestList r,TaxiList l) {
		/**@REQUIRES:CarMap m,RequestList r,TaxiList l;
		   @MODIFIES:this;
		   @EFFECTS:构造;
		 */
		map=m;
		requestlist=r;
		taxilist=l;
	}
	public void setflow(Point a,Point b,int m) {
		/**@REQUIRES:Point a,b, int m;
		   @MODIFIES:map;
		   @EFFECTS:m<0==>System.out&&turn;
		   			a,b不属于(0,0)-(79,79)==>System.out&&return;
		   			map.matrix邻接矩阵不连通==>System.out&&return;
		   			不属于以上三种情况==>map.setvolume(a,b,m);
		 */
		if(m<0) {
			System.out.println("Flow must be positive!");
			return;
		}
		int aa=a.x*80+a.y;
		int bb=b.x*80+b.y;
		if(aa<0||aa>6399||bb<0||bb>6399) {
			System.out.println("Out of border!");
			return;
		}
		if(map.getmatrix(aa,bb)!=1) {
			System.out.println("Not exists!");
			return;
		}
		map.setvolume(a,b,m);
	}
	public void setaxi(int id,int sta,int cre,Point loc,Point startp,Point targetp) {
		/**@REQUIRES:int id,int sta,int cre,Point loc,Point startp,Point targetp;
		   @MODIFIES:taxilist;
		   @EFFECTS:id不是0-99,sta不是0或1或2或3,剩下三个Point超范围==>System.out&&return;
		   			不属于前面的情况==>taxilist.list[id].setaxi(sta, cre, loc, startp, targetp);
		 */
		if(id<0||id>99) {
			System.out.println("Wrong ID!");
			return;
		}
		if(sta!=0&&sta!=1&&sta!=2&&sta!=3) {
			System.out.println("Wrong State!");
			return;
		}
		int ll=loc.x*80+loc.y;
		int ss=startp.x*80+startp.y;
		int tt=targetp.x*80+targetp.y;
		if(ll<0||ll>6399||ss<0||ss>6399||tt<0||tt>6399) {
			System.out.println("Out of border!");
			return;
		}
		taxilist.list[id].setaxi(sta, cre, loc, startp, targetp);
	}
	public void setrequest(String readin) {
		/**@REQUIRES:String readin满足正则表达式"\\[CR,\\([0-9]{1,2},[0-9]{1,2}\\),\\([0-9]{1,2},[0-9]{1,2}\\)\\]";
		   @MODIFIES:this.requestlist;
		   @EFFECTS:数字超范围，相同起始点==>System.out&&return;
		   			不属于上述情况==>requestlist.add(new request);
		 */
		long nowtime=System.currentTimeMillis();
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
	public void test() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:None;
		 */
		///////////////////setflow示例////////////////////
		//////////////////setaxi示例/////////////////////
		/////////////////setrequest示例//////////////////
	}
}
