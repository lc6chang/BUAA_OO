package oo5;

import java.io.FileWriter;
import java.io.IOException;

public class ALL {
	private double basetime;
	private int endflag;
	private int schflag;
	private FileWriter out; 
	public ALL() {
		basetime=0;
		endflag=0;
		schflag=0;
		try {
			out = new FileWriter("./result.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void end() {
		endflag=1;
	}
	public int outendflag() {
		return endflag;
	}
	public void shc_end() {
		schflag=1;
	}
	public int outshcflag() {
		return schflag;
	}
	public void addstring(String a) {
		try {
			out.write(a);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setime(double t) {
		basetime=t;
	}
	public double outbasetime() {
		return basetime;
	}
	public void writein() throws IOException {
		out.close();
	}
}
