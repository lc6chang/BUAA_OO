package oo11;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ListIterator;

public class TaxilogThread extends Thread{
	/*Overview:这个类是可追踪出租车的日志写入线程类，测试者通过在run方法中写入指令，这个线程就可以控制相应的出租车将日志利用迭代器写入文件*/
	private TaxiList taxilist;
	public TaxilogThread(TaxiList t) {
		/**@REQUIRES:TaxiList t;
		   @MODIFIES:this.taxilist;
		   @EFFECTS:this.taxilist==t;
		 */
		taxilist=t;
	}
    /*@repOk.
    check:this.taxilist!=null&&this.taxilist.repOk();
    */
    public boolean repOk(){
        /*
        @EFFECTS:\result = invariant(this)
        */
    	if(taxilist==null) {
    		return false;
    	}
    	return taxilist.repOk();
	}
	public void writelog(int id,int type) {
		/**@REQUIRES:int id,type;
		   @MODIFIES:None;
		   @EFFECTS:id>=30||id<0==>System.out.println&&return;
		   			type!=0&&type!=1==>System.out.println&&return;
		   			Others==>将对应id的出租车的日志按照type的方式输出到相应文件(type==0是向后迭代，type==1是向前迭代);
		 */
		if(id>=30||id<0) {
			System.out.println("Wrong id!");
			return;
		}
		if(type!=0&&type!=1) {
			System.out.println("Wrong type");
			return;
		}
		ListIterator<Log> logiterator=taxilist.list[id].listiterator();
		FileWriter file=null;
		if(type==0) {
			try {
				file = new FileWriter("./taxi "+id+ "'s next log.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
			while(logiterator.hasNext()) {
				Log l=logiterator.next();
				try {
					file.write(l.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		else {			
			try {
				file = new FileWriter("./taxi "+id+ "'s previous log.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
			while(logiterator.hasNext()) {
				logiterator.next();
			}
			while(logiterator.hasPrevious()) {
				Log l=logiterator.previous();
				try {
					file.write(l.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	public void run() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:就是测试线程，测试者写入指令可以将追踪的出租车日志写入文件;
		 */
		System.out.println("id:0-29 Taxi log is writing!");
		//////////////////////////在这里加入要追踪的出租车就可以了//////////////////////////
		writelog(0,0);
		writelog(0,1);
		
		
		
		
		///////////////////////////////////////////////////////////////////////////////////
		System.out.println("id:0-29 Taxi log is over writing!");
	}
}
