package oo6;

public class Request {
	private String path;
	private int trigger;
	private int job;
	public Request(String s,int t,int j) {
		path=s;
		trigger=t;
		job=j;
	}
	public String outpath() {
		return path;
	}
	public int outrigger() {
		return trigger;
	}
	public int outjob() {
		return job;
	}
}
