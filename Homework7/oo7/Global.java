package oo7;

import java.io.FileWriter;
import java.io.IOException;

public class Global {
	private int endflag;
	private String[] stringlist;
	private FileWriter out;
	private int orderflag;
	public Global() {
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
	synchronized public boolean startorder() {
		if(orderflag==0) {
			orderflag=1;
			return true;
		}
		return false;
	}
	synchronized public void endorder() {
		orderflag=0;
	}
	public void end() {
		endflag=1;
	}
	public int getend() {
		return endflag;
	}
	public void add(String s,int i) {
		stringlist[i]=stringlist[i]+s;
	}
	synchronized public void write(int i) {
		try {
			out.write(stringlist[i]);
			out.flush();
		}catch (IOException e) {
			System.out.println("The file is already closed!");
		}

		stringlist[i]="\r\n";
	}
	public void writend() {
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
