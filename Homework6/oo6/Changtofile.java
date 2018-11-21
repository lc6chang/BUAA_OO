package oo6;

import java.io.File;

public class Changtofile {
	public static Filenode change(File f) {
		String name=f.getName();
		String path=f.getAbsolutePath();
		String fatherpath=f.getParent();
		long size=f.length();
		long modified=f.lastModified();
		Filenode file=new Filenode(name,path,fatherpath,size,modified);
		return file;
	}
}
