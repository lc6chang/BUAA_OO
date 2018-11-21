package oo10;

import java.io.FileWriter;
import java.io.IOException;

public class Global {
	/*Overview:这是Global类，提供了一些全局的控制信号例如结束控制信号等，同时提供了控制日志输出到文件的一系列方法
	 * 
	 */
	private int endflag;
	private String[] stringlist;
	private FileWriter out;
	private int orderflag;
	public Global() {
		/**@REQUIRES:None;
		   @MODIFIES:this.endflag,this.stringlist,this.out,this.orderflag,this.volumeflag;
		   @EFFECTS:this.endflag==0;
		   			\all int i;0<=i<100==>this.stringlist[i]=="\r\n";
		   			this.out==FileWriter("./log.txt");
		   			this.orderflag==0;
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
	}
    /*@repOk.
    check:1.this.endflag==1||this.endflag==0;
    	  2.\all int i;0<=i<100==>this.stringlist[i]!=null;
    	  3.this.orderflag==1||this.orderflag==0;
    */
    public boolean repOk(){
        /*
        @EFFECTS:\result = invariant(this)
        */
    	if(endflag!=1&&endflag!=0) {
    		return false;
    	}
    	for(int i=0;i<100;i++) {
    		if(stringlist[i]==null) {
    			return false;
    		}
    	}
    	if(orderflag!=1&&orderflag!=0) {
    		return false;
    	}
    	return true;
	}
	synchronized public boolean startorder() {
		/**@REQUIRES:None;
		   @MODIFIES:this.orderflag;
		   @EFFECTS:this.orderflag==0==>\result==true&&this.orderflag==1;
		   			this.orderflag!=0==>\result==false;
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
		   @EFFECTS:this.orderflag==0;
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:\locked();
		 */
		orderflag=0;
	}
	public void end() {
		/**@REQUIRES:None;
		   @MODIFIES:this.endflag;
		   @EFFECTS:this.endflag==1;
		   @THREAD_REQUIRES:\locked(this.endflag);
		   @THREAD_EFFECTS:None;
		 */
		endflag=1;
	}
	public int getend() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result==this.endflag;
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:None;
		 */
		return endflag;
	}
	public void add(String s,int i) {
		/**@REQUIRES:String s,int i;
		   @MODIFIES:this.stringlist[i];
		   @EFFECTS:this.stringlist[i]==\old(this.stringlist[i])+s;
		   @THREAD_REQUIRES:\locked(this.stringlist[i]);
		   @THREAD_EFFECTS:None;
		 */
		stringlist[i]=stringlist[i]+s;
	}
	synchronized public void write(int i) {
		/**@REQUIRES:None;
		   @MODIFIES:this.out,this.stringlist;
		   @EFFECTS:将this.stringlist[i]的内容写入this.out文件;
		   			this.stringlist[i]=="\r\n";
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
