package oo6;

public class Filenode {
	private String name;
	private String path;
	private String fatherpath;
	private long size;
	private long modified;
	
	public Filenode(String n,String p,String f,long s,long m) {
		name=n;
		path=p;
		fatherpath=f;
		size=s;
		modified=m;
	}
	public String outname() {
		return name;
	}
	public String outpath() {
		return path;
	}
	public String outfatherpath() {
		return fatherpath;
	}
	public long outsize() {
		return size;
	}
	public long outmodified() {
		return modified;
	}
	/////////////重写hashCode和equals/////////////////
	public int hashCode() {
		return path.hashCode();
	}
	public boolean equals(Object o) {
		if(this==o) {
			return true;
		}
		if(!(o instanceof Filenode)) {
			return false;
		}
		Filenode f=(Filenode)o;
		return f.outname().equals(name)&&f.outpath().equals(path)&&f.outfatherpath().equals(fatherpath);
	}
	public Filenode exists(FileList list) {
		for(int i=0;i<list.len();i++) {
			if(this.equals(list.search(i))) {
				return list.search(i);
			}
		}
		return null;
	}
}
