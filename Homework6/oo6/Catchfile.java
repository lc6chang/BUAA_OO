package oo6;
import java.io.File;

public class Catchfile {
	private FileList filelist;
	public Catchfile() {
		filelist=new FileList();
	}
	public FileList outfilelist() {
		return filelist;
	}
	public void snapshot(String path) {
		File file=new File(path);
		File[] files=file.listFiles();
		if(files==null) {
			return;
		}
		for(File f:files) {
			if(f.isFile()) {
				filelist.add(Changtofile.change(f));
			}
			else {
				snapshot(f.getAbsolutePath());
			}
		}
	}
}
