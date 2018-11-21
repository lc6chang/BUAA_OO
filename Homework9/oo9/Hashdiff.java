package oo9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Hashdiff {
	public static ArrayList<Integer> diff(ArrayList<Integer> old,ArrayList<Integer> now) {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result=相对于old数组，new数组中新增的元素;
		 */
		ArrayList<Integer> diflist=new ArrayList<Integer>();
		Map<Integer,Integer> map=new HashMap<Integer,Integer>(old.size());
		for(int i=0;i<old.size();i++){
			int n=old.get(i);
			map.put(n,1);
		}
		for(int i=0;i<now.size();i++){
			int n=now.get(i);
			if(map.get(n)==null) {
				diflist.add(n);
				continue;
			}
		}		
		return diflist;
	}
}
