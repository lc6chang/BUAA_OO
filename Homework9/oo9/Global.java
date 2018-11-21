package oo9;

import java.io.FileWriter;
import java.io.IOException;

public class Global {
	private int endflag;
	private String[] stringlist;
	private FileWriter out;
	private int orderflag;
	private int volumeflag;
	public Global() {
		/**@REQUIRES:None;
		   @MODIFIES:this.endflag,this.stringlist,this.out,this.orderflag,this.volumeflag;
		   @EFFECTS:构造;
		 */
		endflag=0;
		stringlist=new String[100];
		for(int i=0;i<100;i++) {
			stringlist[i]="\r\n";
		}
		try {
			out = new FileWriter("./log.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		orderflag=0;
		volumeflag=0;
	}
	synchronized public boolean startmove() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:volumeflag==0==>\result=true;
		   			volumeflag!=0==>\result=false;
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:\locked();
		 */
		if(volumeflag==0) {
			return true;
		}
		return false;
	}
	synchronized public void endmove() {
		/**@REQUIRES:None;
		   @MODIFIES:this.volumeflag;
		   @EFFECTS:volumeflag++;
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:\locked();
		 */
		volumeflag++;
	}
	synchronized public void subvolumeflag() {
		/**@REQUIRES:None;
		   @MODIFIES:this.volumeflag;
		   @EFFECTS:volumeflag--;
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:\locked();
		 */
		volumeflag--;
	}
	synchronized public boolean startorder() {
		/**@REQUIRES:None;
		   @MODIFIES:this.orderflag;
		   @EFFECTS:orderflag==0==>\result=true&& orderflag=1;
		   			orderflag!=0==>\result=false;
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:\locked();
		 */
		if(orderflag==0) {
			orderflag=1;
			return true;
		}
		return false;
	}
	synchronized public void endorder() {
		/**@REQUIRES:None;
		   @MODIFIES:this.orderflag;
		   @EFFECTS:orderflag=0;
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:\locked();
		 */
		orderflag=0;
	}
	public void end() {
		/**@REQUIRES:None;
		   @MODIFIES:this.endflag;
		   @EFFECTS:endflag=1;
		   @THREAD_REQUIRES:\locked(this.endflag);
		   @THREAD_EFFECTS:None;
		 */
		endflag=1;
	}
	public int getend() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result=endflag;
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:None;
		 */
		return endflag;
	}
	public void add(String s,int i) {
		/**@REQUIRES:String s,int i;
		   @MODIFIES:this.stringlist[i];
		   @EFFECTS:stringlist[i]+=s;
		   @THREAD_REQUIRES:\locked(this.stringlist[i]);
		   @THREAD_EFFECTS:None;
		 */
		stringlist[i]=stringlist[i]+s;
	}
	synchronized public void write(int i) {
		/**@REQUIRES:None;
		   @MODIFIES:this.out,this.stringlist;
		   @EFFECTS:将stringlist[i]的内容写入this.out文件;
		   			stringlist[i]="\r\n";
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:\locked();
		 */
		try {
			out.write(stringlist[i]);
			out.flush();
		}catch (IOException e) {
			System.out.println("The file is already closed!");
		}

		stringlist[i]="\r\n";
	}
	public void writend() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:关闭out文件;
		   @THREAD_REQUIRES:\locked(this);
		   @THREAD_EFFECTS:None;
		 */
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
