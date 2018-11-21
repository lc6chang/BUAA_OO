package oo10;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoadFile {
	/*Overview:这个是LoadFile类，提供测试文件的读入以及流量、出租车、请求初始设置的方法
	 * 
	 */
	private CarMap map;
	private RequestList requestlist;
	private TaxiList taxilist;
	private int state;
	public LoadFile(CarMap m,RequestList r,TaxiList l) {
		/**@REQUIRES:CarMap m,RequestList r,TaxiList l;
		   @MODIFIES:this;
		   @EFFECTS:this.map==m&&this.requestlist==r&&this.taxilist==l&&this.state==0;
		 */
		map=m;
		requestlist=r;
		taxilist=l;
		state=0;
	}
    /*@repOk.
    check:1.this.map!=null&&this.requestlist!=null&&this.taxilist!=null;
    	  2.this.map.repOk()&&this.requestlist.repOk()&&this.taxilist.repOk();
    	  3.state==0||state==1||state==2||state==3;
    */
    public boolean repOk(){
        /*
        @EFFECTS:\result = invariant(this)
        */
    	if(map==null||requestlist==null||taxilist==null) {
    		return false;
    	}
    	if(state<0||state>3) {
    		return false;
    	}
    	return map.repOk()&&requestlist.repOk()&&taxilist.repOk();
	}
	public void setflow(Point a,Point b,int m) {
		/**@REQUIRES:Point a,b, int m;
		   @MODIFIES:this.map;
		   @EFFECTS:m<0==>System.out&&return;
		   			a,b不属于(0,0)-(79,79)==>System.out&&return;
		   			this.map.matrix邻接矩阵不连通==>System.out&&return;
		   			不属于以上三种情况==>this.map.setvolume(a,b,m);
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
		   @MODIFIES:this.taxilist;
		   @EFFECTS:id不是0-99,sta不是0或1或2或3,剩下三个Point超范围==>System.out&&return;
		   			不属于前面的情况==>this.taxilist.list[id].setaxi(sta, cre, loc, startp, targetp);
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
		   			不属于上述情况==>this.requestlist.add(new request);
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
	public void judge_flow(String s) {
		/**@REQUIRES:String s&&s符合"(x1,y1)(x2,y2) value"这种格式;
		   @MODIFIES:this.map;
		   @EFFECTS:提取数字并且调用setflow设置流量基础值;
		 */
		String judge_number="[0-9]+";
		Pattern pat_number=Pattern.compile(judge_number);
		Matcher mat_number=pat_number.matcher(s);
		int[] number=new int[5];
		for(int i=0;i<5;i++) {
			mat_number.find();
			number[i]=Integer.parseInt(mat_number.group(0));
		}
		setflow(new Point(number[0],number[1]),new Point(number[2],number[3]),number[4]);
	}
	public void judge_taxi(String s) {
		/**@REQUIRES:String s&&s符合"NO.id state credit (x,y)"这种格式;
		   @MODIFIES:this.taxilist;
		   @EFFECTS:提取数字并且调用setaxi设置出租车状态;
		 */
		String judge_number="[0-9]+";
		Pattern pat_number=Pattern.compile(judge_number);
		Matcher mat_number=pat_number.matcher(s);
		int[] number=new int[5];
		for(int i=0;i<5;i++) {
			mat_number.find();
			number[i]=Integer.parseInt(mat_number.group(0));
		}
		setaxi(number[0],number[1],number[2],new Point(number[3],number[4]),new Point (40,40),new Point(35,35));
	}
	public void judge_request(String s) {
		/**@REQUIRES:String s&&s符合"[CR,(x1,y1),(x2,y2)]"这种格式;
		   @MODIFIES:this.requestlist;
		   @EFFECTS:调用setrequest添加请求;
		 */
		setrequest(s);
	}
	public void test(String path) {
		/**@REQUIRES:String path;
		   @MODIFIES:this;
		   @EFFECTS:太复杂了，您扣分吧！
		 */
		File file = new File(path);
		if(!file.exists()) {
			System.out.println("The test file is not exist!");
			System.exit(0);
		}
		BufferedReader reader = null;  
		try {    
			reader = new BufferedReader(new FileReader(file));  
			String tempString = null;  
			while ((tempString = reader.readLine()) != null) {
				if(state==0) {
					if(tempString.equals("#flow")) {
						state=1;
						continue;
					}
					if(tempString.equals("#taxi")) {
						state=2;
						continue;
					}
					if(tempString.equals("#request")) {
						state=3;
						continue;
					}
				}
				if(state==1) {
					if(tempString.equals("#end_flow")) {
						state=0;
						continue;
					}
					judge_flow(tempString);
					continue;
				}
				if(state==2) {
					if(tempString.equals("#end_taxi")) {
						state=0;
						continue;
					}
					judge_taxi(tempString);
					continue;
				}
				if(state==3) {
					if(tempString.equals("#end_request")) {
						break;
					}
					judge_request(tempString);
					continue;
				}
			}
			reader.close();
			System.out.println("The test file is already!");
		} catch (IOException e) {  
			e.printStackTrace();  
		} 
	}
}
