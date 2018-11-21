package oo6;

import java.io.FileWriter;
import java.io.IOException;

public class All {
	private int[] sumtime;
	private String[] detailstring;
	private int endflag;
	private int busy;
	public All() {
		sumtime=new int[4];
		detailstring=new String[4];
		detailstring[0]="\r\n";
		detailstring[1]="\r\n";
		detailstring[2]="\r\n";
		detailstring[3]="\r\n";
		endflag=0;
		busy=0;
	}
	synchronized public boolean fileask() {
		if(busy==0) {
			busy++;
			return true;
		}
		return false;
	}
	synchronized public void filend() {
		busy--;
	}
	public void proend() {
		endflag=1;
	}
	public int outendflag() {
		return endflag;
	}
	synchronized public void recordsummary(int trigger) {
		sumtime[trigger-1]++;
	}
	synchronized public void recorddetail(int trigger,Filenode old,Filenode now) {
		String oldate=Hashsearch.formatdate(old.outmodified());
		String nowdate=Hashsearch.formatdate(now.outmodified());
		detailstring[trigger-1]=detailstring[trigger-1]+"Path: "+old.outpath()+"------->"+now.outpath()+"\r\n";
		detailstring[trigger-1]=detailstring[trigger-1]+"Filename: "+old.outname()+"-------->"+now.outname()+"\r\n";
		detailstring[trigger-1]=detailstring[trigger-1]+"Size: "+old.outsize()+"-------->"+now.outsize()+"\r\n";
		detailstring[trigger-1]=detailstring[trigger-1]+"Last Modified: "+oldate+"-------->"+nowdate+"\r\n\r\n";
	}
	public void writesummary() throws IOException {
		FileWriter summary=new FileWriter("./summary.txt");
		String a=null;
		a="^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\r\n";
		a=a+"renamed类触发器共触发"+sumtime[0]+"次\r\n";
		a=a+"Modified类触发器共触发"+sumtime[1]+"次\r\n";
		a=a+"path-changed类触发器共触发"+sumtime[2]+"次\r\n";
		a=a+"size-changed类触发器共触发"+sumtime[3]+"次\r\n";
		a=a+"^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\r\n";
		summary.write(a);
		summary.flush();
		summary.close();
	}
	
	public void writedetails() throws IOException {
		FileWriter details=new FileWriter("./details.txt");
		String a=null;
		a="^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\r\n";
		a=a+"renamed的文件有：\r\n";
		a=a+detailstring[0];
		a=a+"-------------------------------------------------------\r\n";
		a=a+"Modified的文件有：\r\n";
		a=a+detailstring[1];
		a=a+"-------------------------------------------------------\r\n";
		a=a+"path-changed的文件有：\r\n";
		a=a+detailstring[2];
		a=a+"-------------------------------------------------------\r\n";
		a=a+"size-changed的文件有：\r\n";
		a=a+detailstring[3];
		a=a+"^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\r\n";
		details.write(a);
		details.flush();
		details.close();
	}
}
