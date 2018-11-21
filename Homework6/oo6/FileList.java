package oo6;
import java.util.ArrayList;

public class FileList {
	private ArrayList<Filenode> filelist;
	
	public FileList() {
		filelist=new ArrayList<Filenode>();
	}
	public int len() {
		return filelist.size();
	}
	public void add(Filenode a) {
		filelist.add(a);
	}
	public Filenode search(int i) {
		return filelist.get(i);
	}
	public void remove(int i) {
		filelist.remove(i);
	}
	public void replace(int i,Filenode a) {
		filelist.remove(i);
		filelist.add(i, a);
	}
	public ArrayList<Filenode> outlist(){
		return filelist;
	}
	public void addlist(FileList a) {
		filelist.addAll(a.outlist());
	}
}

