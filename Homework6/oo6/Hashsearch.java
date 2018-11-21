package oo6;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Hashsearch {
	public static FileList diff(FileList old,FileList now) {
		FileList diflist=new FileList();
		Map<Filenode,Integer> map=new HashMap<Filenode,Integer>(old.len());
		for(int i=0;i<old.len();i++){
			Filenode file=old.search(i);
			map.put(file,1);
		}
		for(int i=0;i<now.len();i++){
			Filenode file=now.search(i);
			if(map.get(file)==null) {
				diflist.add(file);
				continue;
			}
		}		
		return diflist;
	}
	public static String formatdate(Long millSec) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(millSec);
		return sdf.format(date);
	}
}
