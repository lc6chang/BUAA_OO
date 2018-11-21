package oo13;

public class ALS_Schedule{
	/*Overview:这是调度类，只有一个方法，输入请求队列和电梯，就会按照指导书和Readme中相关规则调度电梯*/
	
	
	public void override(RequestList list,Elevator ele) {
		/**@REQUIRES:RequestList list; list!=null;
		 * 			 \all int i;0<=i<list.size() ==>list[i].type==0||list[i].type==1 && 1<=list[i].floor<=10 
		 * 											&& (list[i].floor==1==>list[i].move!=2) && (list[i].floor==10==>list[i].move!=1)
		 * 											&& (list[i].type==0==>list[i].move==0) && (list[i].type==1==>(list[i].move==1||list[i].move==2))
		 * 			 								&& list[i].Time>=0 && list[i].word!=null
		 * 											&& list[i].word的格式为"[FR,floor,UP/DOWN,time]"或"[ER,floor,time]",且内容必须要与前面输入的参数相符;
		 * 			 								(list[i].word的内容会作为输出中的指令部分直接输出，请严格按照Readme中的规定输入，不然输出就会出错啦！使用自然语言描述请见谅....)
		 * 			 \all int i,j;0<=i,j<list.size() && i<j ==> list[i].Time<=list[j].Time;
		 * 			 Elevator ele; ele!=null;
		 * 			 ele.floor==1 && ele.move==0 && ele.time==0;
		   @MODIFIES:list, ele;
		   @EFFECTS:......这个看起来有点复杂......概括起来就是这个调度方法会根据指导书以及Readme里面的原则，根据list中的request来进行电梯调度，并输出相应的信息......
		 */
		for(int i=0;i<list.len();i++) {
			if(list.search(i).outflag()!=1) {
				System.out.println("#SAME"+list.search(i).outword());
				continue;
			}
			double start_time=ele.startime(list.search(i));
			int move_drc=ele.moveway(list.search(i));
			int floor_drc=list.search(i).outfloor();
			int offset=0;
			int stopnum=0;
			int root_move=list.search(i).outmove();
			
			
/////////////////////////////////////方向向上/////////////////////////////////////////////////////		
			if(move_drc==1) {
				for(int f=ele.outfloor()+1;f<=floor_drc;f++) {
					double end_time=start_time+(f-ele.outfloor())*0.5+stopnum;
					int flag=0;
					
					for(int j=i+offset;j<list.len();j++) {
						if(list.search(j).outTime()>=end_time) {
							break;
						}
						if(list.search(j).outflag()!=1) {
							continue;
						}
						if((list.search(j).outfloor()==f&&(list.search(j).outmove()==0||list.search(j).outmove()==move_drc))||(f==floor_drc&&list.search(j).outfloor()==f&&list.search(j).outmove()==root_move)){
							if(flag==0) {
								flag=1;
								stopnum=stopnum+1;
							}else {
								list.search(j).flag2();
							}
							//除去同质//
							for(int k=j+1;k<list.len();k++) {
								if(list.search(k).outTime()>end_time+1) {
									break;
								}
								else {
									int p=list.search(j).issame(list.search(k));
									if(p==1) {
										list.search(k).unflag();
									}
								}
							}
							list.moveto(j, i+offset);
							offset=offset+1;
						}
					}
				}
				///无论如何自己主请求都算了一遍，所以要把offset再减一//////////
				offset=offset-1;
				//开始输出//
				double fromtime=start_time;
				for(int l=0;l<=offset;l++) {
					if(list.search(i+l).outflag()==2) {
						System.out.print(list.search(i+l).outword()+"/");
						System.out.println("("+ele.outfloor()+",UP,"+String.format("%.1f", ele.outtime()-1)+")");
					}
					else {
						int tofloor=list.search(i+l).outfloor();
						int tomove=ele.moveway(list.search(i+l));
						double tomovetime=ele.movetime(list.search(i+l));
						ele.UpandDown(tofloor, tomove, fromtime, tomovetime);
						System.out.print(list.search(i+l).outword()+"/");
						System.out.println(ele.toString());
						ele.stop();
						fromtime=ele.outtime();
					}
				}
				//将未完成的捎带提前//
				int flag2=0;
				double dead_time=ele.outtime()-1;
				int y=0;
				for(y=i+offset+1;y<list.len();y++) {
					if(list.search(y).outTime()>=dead_time) {
						break;
					}
					if(list.search(y).outflag()!=1) {
						continue;
					}
					if(list.search(y).outtype()==0&&list.search(y).outfloor()>ele.outfloor()) {
						flag2=1;
						break;
					}
				}
				if(flag2==1) {
					list.moveto(y,i+offset+1);
				}
				i=i+offset;				
			}
			
////////////////////////////////////方向向下//////////////////////////////////////////////////////////////////////////////
			else if(move_drc==2) {
				for(int f=ele.outfloor()-1;f>=floor_drc;f--) {
					double end_time=start_time+(ele.outfloor()-f)*0.5+stopnum;
					int flag=0;
					
					for(int j=i+offset;j<list.len();j++) {
						if(list.search(j).outTime()>=end_time) {
							break;
						}
						if(list.search(j).outflag()!=1) {
							continue;
						}
						if((list.search(j).outfloor()==f&&(list.search(j).outmove()==0||list.search(j).outmove()==move_drc))||(f==floor_drc&&list.search(j).outfloor()==f&&list.search(j).outmove()==root_move)) {
							if(flag==0) {
								flag=1;
								stopnum=stopnum+1;
							}else {
								list.search(j).flag2();
							}
							//除去同质//
							for(int k=j+1;k<list.len();k++) {
								if(list.search(k).outTime()>end_time+1) {
									break;
								}
								else {
									int p=list.search(j).issame(list.search(k));
									if(p==1) {
										list.search(k).unflag();
									}
								}
							}
							list.moveto(j, i+offset);
							offset=offset+1;
						}
					}
				}
				offset=offset-1;
				//开始输出//
				double fromtime=start_time;
				for(int l=0;l<=offset;l++) {
					if(list.search(i+l).outflag()==2) {
						System.out.print(list.search(i+l).outword()+"/");
						System.out.println("("+ele.outfloor()+",DOWN,"+String.format("%.1f", ele.outtime()-1)+")");
					}
					else {
						int tofloor=list.search(i+l).outfloor();
						int tomove=ele.moveway(list.search(i+l));
						double tomovetime=ele.movetime(list.search(i+l));
						ele.UpandDown(tofloor, tomove, fromtime, tomovetime);
						System.out.print(list.search(i+l).outword()+"/");
						System.out.println(ele.toString());
						ele.stop();
						fromtime=ele.outtime();
					}
				}
				//将未完成的捎带提前//
				int flag2=0;
				double dead_time=ele.outtime()-1;
				int y=0;
				for(y=i+offset+1;y<list.len();y++) {
					if(list.search(y).outTime()>=dead_time) {
						break;
					}
					if(list.search(y).outflag()!=1) {
						continue;
					}
					if(list.search(y).outtype()==0&&list.search(y).outfloor()<ele.outfloor()) {
						flag2=1;
						break;
					}
				}
				if(flag2==1) {
					list.moveto(y,i+offset+1);
				}
				i=i+offset;				
			}
			
////////////////////////停留/////////////////////////////////////////////////
			else {
/////////////////////////////去除同质//////////////
				double timend=ele.startime(list.search(i))+1;
				for(int j=i+1;j<list.len();j++){
					if(list.search(j).outTime()>timend) {
						break;
					}
					else {
						int p=list.search(i).issame(list.search(j));
						if(p==1) {
							list.search(j).unflag();
						}
					}
				}
				int tofloor=list.search(i).outfloor();
				int tomove=ele.moveway(list.search(i));
				ele.UpandDown(tofloor, tomove, timend-1, 0);
				System.out.print(list.search(i).outword()+"/");
				System.out.println(ele.toString());
				ele.stop();
				
			}			
		}
	}
}
