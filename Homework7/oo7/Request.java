package oo7;

import java.awt.Point;

public class Request {
	private Point start;
	private Point end;
	private long time;
	private String word;
	public Request(Point s,Point e,long t,String w) {
		start=s;
		end=e;
		time=t;
		word=w;
	}
	public Point getstart() {
		return start;
	}
	public Point getend() {
		return end;
	}
	public long getime() {
		return time;
	}
	public String getword() {
		return word;
	}
}
