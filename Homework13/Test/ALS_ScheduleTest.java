package Test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import oo13.ALS_Schedule;
import oo13.Elevator;
import oo13.Request;
import oo13.RequestList;

public class ALS_ScheduleTest {
	@Test
	//正常测试1
	public void testOverride1() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		
		RequestList requestlist=new RequestList();
		Elevator elevator=new Elevator();
		ALS_Schedule als=new ALS_Schedule();
		
		requestlist.add(new Request(1,1,1,0,"[FR,1,UP,0]"));
		
		als.override(requestlist, elevator);
		
		assertEquals("[FR,1,UP,0]/(1,STILL,1.0)"+System.lineSeparator(),output.toString());
		
		System.setOut(System.out);		
	}
	@Test
	//正常测试2
	public void testOverride2() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		
		RequestList requestlist=new RequestList();
		Elevator elevator=new Elevator();
		ALS_Schedule als=new ALS_Schedule();
		
		requestlist.add(new Request(1,1,1,0,"[FR,1,UP,0]"));
		requestlist.add(new Request(1,5,2,1,"[FR,5,DOWN,1]"));
		requestlist.add(new Request(1,10,1,1,"[FR,10,UP,1]"));
		
		als.override(requestlist, elevator);
		
		assertEquals("[FR,1,UP,0]/(1,STILL,1.0)"+System.lineSeparator()
					+"[FR,5,DOWN,1]/(5,UP,3.0)"+System.lineSeparator()
					+"[FR,10,UP,1]/(10,UP,6.5)"+System.lineSeparator(),output.toString());
		
		System.setOut(System.out);		
	}
	
	@Test
	//正常测试3
	public void testOverride3() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		
		RequestList requestlist=new RequestList();
		Elevator elevator=new Elevator();
		ALS_Schedule als=new ALS_Schedule();
		
		requestlist.add(new Request(1,1,1,0,"[FR,1,UP,0]"));
		requestlist.add(new Request(1,10,2,1,"[FR,10,DOWN,1]"));
		requestlist.add(new Request(1,5,2,1,"[FR,5,DOWN,1]"));
		
		als.override(requestlist, elevator);
		
		assertEquals("[FR,1,UP,0]/(1,STILL,1.0)"+System.lineSeparator()
					+"[FR,10,DOWN,1]/(10,UP,5.5)"+System.lineSeparator()
					+"[FR,5,DOWN,1]/(5,DOWN,9.0)"+System.lineSeparator(),output.toString());
		
		System.setOut(System.out);		
	}
	
	@Test
	//电梯休息一会儿再启动
	public void testOverride4() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		
		RequestList requestlist=new RequestList();
		Elevator elevator=new Elevator();
		ALS_Schedule als=new ALS_Schedule();
		
		requestlist.add(new Request(1,1,1,0,"[FR,1,UP,0]"));
		requestlist.add(new Request(1,10,2,10000,"[FR,10,DOWN,10000]"));
		requestlist.add(new Request(1,5,2,100000,"[FR,5,DOWN,100000]"));
		
		als.override(requestlist, elevator);
		
		assertEquals("[FR,1,UP,0]/(1,STILL,1.0)"+System.lineSeparator()
					+"[FR,10,DOWN,10000]/(10,UP,10004.5)"+System.lineSeparator()
					+"[FR,5,DOWN,100000]/(5,DOWN,100002.5)"+System.lineSeparator(),output.toString());
		
		System.setOut(System.out);		
	}
	
	@Test
	//向上捎带
	public void testOverride5() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		
		RequestList requestlist=new RequestList();
		Elevator elevator=new Elevator();
		ALS_Schedule als=new ALS_Schedule();
		
		requestlist.add(new Request(1,1,1,0,"[FR,1,UP,0]"));
		requestlist.add(new Request(1,4,2,5,"[FR,4,DOWN,5]"));
		requestlist.add(new Request(1,3,1,5,"[FR,3,UP,5]"));
		
		als.override(requestlist, elevator);
		
		assertEquals("[FR,1,UP,0]/(1,STILL,1.0)"+System.lineSeparator()
					+"[FR,3,UP,5]/(3,UP,6.0)"+System.lineSeparator()
					+"[FR,4,DOWN,5]/(4,UP,7.5)"+System.lineSeparator(),output.toString());
		
		System.setOut(System.out);		
	}
	
	@Test
	//向下捎带
	public void testOverride6() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		
		RequestList requestlist=new RequestList();
		Elevator elevator=new Elevator();
		ALS_Schedule als=new ALS_Schedule();
		
		requestlist.add(new Request(1,1,1,0,"[FR,1,UP,0]"));
		requestlist.add(new Request(1,10,2,0,"[FR,10,DOWN,0]"));
		requestlist.add(new Request(1,2,2,10,"[FR,2,DOWN,10]"));
		requestlist.add(new Request(1,3,2,10,"[FR,3,DOWN,10]"));
		
		als.override(requestlist, elevator);
		
		assertEquals("[FR,1,UP,0]/(1,STILL,1.0)"+System.lineSeparator()
					+"[FR,10,DOWN,0]/(10,UP,5.5)"+System.lineSeparator()
					+"[FR,3,DOWN,10]/(3,DOWN,13.5)"+System.lineSeparator()
					+"[FR,2,DOWN,10]/(2,DOWN,15.0)"+System.lineSeparator(),output.toString());
		
		System.setOut(System.out);		
	}
	
	@Test
	//向上捎带同层多条
	public void testOverride7() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		
		RequestList requestlist=new RequestList();
		Elevator elevator=new Elevator();
		ALS_Schedule als=new ALS_Schedule();
		
		requestlist.add(new Request(1,1,1,0,"[FR,1,UP,0]"));
		requestlist.add(new Request(1,5,2,2,"[FR,5,DOWN,2]"));
		requestlist.add(new Request(1,5,1,2,"[FR,5,UP,2]"));
		requestlist.add(new Request(0,5,0,2,"[ER,5,2]"));
		
		als.override(requestlist, elevator);
		
		assertEquals("[FR,1,UP,0]/(1,STILL,1.0)"+System.lineSeparator()
					+"[FR,5,DOWN,2]/(5,UP,4.0)"+System.lineSeparator()
					+"[FR,5,UP,2]/(5,UP,4.0)"+System.lineSeparator()
					+"[ER,5,2]/(5,UP,4.0)"+System.lineSeparator(),output.toString());
		
		System.setOut(System.out);		
	}
	@Test
	//向下捎带同层多条
	public void testOverride8() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		
		RequestList requestlist=new RequestList();
		Elevator elevator=new Elevator();
		ALS_Schedule als=new ALS_Schedule();
		
		requestlist.add(new Request(1,1,1,0,"[FR,1,UP,0]"));
		requestlist.add(new Request(1,10,2,0,"[FR,10,DOWN,0]"));
		requestlist.add(new Request(1,4,1,10,"[FR,4,UP,10]"));
		requestlist.add(new Request(1,4,2,10,"[FR,4,DOWN,10]"));
		requestlist.add(new Request(0,4,0,10,"[ER,4,10]"));
		
		als.override(requestlist, elevator);
		
		assertEquals("[FR,1,UP,0]/(1,STILL,1.0)"+System.lineSeparator()
					+"[FR,10,DOWN,0]/(10,UP,5.5)"+System.lineSeparator()
					+"[FR,4,UP,10]/(4,DOWN,13.0)"+System.lineSeparator()
					+"[FR,4,DOWN,10]/(4,DOWN,13.0)"+System.lineSeparator()
					+"[ER,4,10]/(4,DOWN,13.0)"+System.lineSeparator(),output.toString());
		
		System.setOut(System.out);		
	}
	@Test
	//向上同质
	public void testOverride9() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		
		RequestList requestlist=new RequestList();
		Elevator elevator=new Elevator();
		ALS_Schedule als=new ALS_Schedule();
		
		requestlist.add(new Request(1,1,1,0,"[FR,1,UP,0]"));
		requestlist.add(new Request(1,1,1,0,"[FR,1,UP,0]"));
		requestlist.add(new Request(1,5,2,2,"[FR,5,DOWN,2]"));
		requestlist.add(new Request(1,5,2,2,"[FR,5,DOWN,2]"));
		requestlist.add(new Request(1,5,1,2,"[FR,5,UP,2]"));
		requestlist.add(new Request(1,5,1,2,"[FR,5,UP,2]"));
		requestlist.add(new Request(0,5,0,2,"[ER,5,2]"));
		requestlist.add(new Request(0,5,0,3,"[ER,5,3]"));
		
		als.override(requestlist, elevator);
		
		assertEquals("[FR,1,UP,0]/(1,STILL,1.0)"+System.lineSeparator()
					+"#SAME[FR,1,UP,0]"+System.lineSeparator()
					+"[FR,5,DOWN,2]/(5,UP,4.0)"+System.lineSeparator()
					+"[FR,5,UP,2]/(5,UP,4.0)"+System.lineSeparator()
					+"[ER,5,2]/(5,UP,4.0)"+System.lineSeparator()
					+"#SAME[FR,5,DOWN,2]"+System.lineSeparator()
					+"#SAME[FR,5,UP,2]"+System.lineSeparator()
					+"#SAME[ER,5,3]"+System.lineSeparator(),output.toString());
		
		System.setOut(System.out);		
	}
	
	@Test
	//向下同质
	public void testOverride10() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		
		RequestList requestlist=new RequestList();
		Elevator elevator=new Elevator();
		ALS_Schedule als=new ALS_Schedule();
		
		requestlist.add(new Request(1,1,1,0,"[FR,1,UP,0]"));
		requestlist.add(new Request(1,1,1,0,"[FR,1,UP,0]"));
		requestlist.add(new Request(1,10,2,0,"[FR,10,DOWN,0]"));
		requestlist.add(new Request(1,4,1,10,"[FR,4,UP,10]"));
		requestlist.add(new Request(1,4,1,10,"[FR,4,UP,10]"));
		requestlist.add(new Request(1,4,2,10,"[FR,4,DOWN,10]"));
		requestlist.add(new Request(1,4,2,10,"[FR,4,DOWN,10]"));
		requestlist.add(new Request(0,4,0,10,"[ER,4,10]"));
		requestlist.add(new Request(0,4,0,11,"[ER,4,11]"));
		
		als.override(requestlist, elevator);
		
		assertEquals("[FR,1,UP,0]/(1,STILL,1.0)"+System.lineSeparator()
					+"#SAME[FR,1,UP,0]"+System.lineSeparator()
					+"[FR,10,DOWN,0]/(10,UP,5.5)"+System.lineSeparator()
					+"[FR,4,UP,10]/(4,DOWN,13.0)"+System.lineSeparator()
					+"[FR,4,DOWN,10]/(4,DOWN,13.0)"+System.lineSeparator()
					+"[ER,4,10]/(4,DOWN,13.0)"+System.lineSeparator()
					+"#SAME[FR,4,UP,10]"+System.lineSeparator()
					+"#SAME[FR,4,DOWN,10]"+System.lineSeparator()
					+"#SAME[ER,4,11]"+System.lineSeparator(),output.toString());
		
		System.setOut(System.out);		
	}
	
	@Test
	//向上未捎带提前
	public void testOverride11() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		
		RequestList requestlist=new RequestList();
		Elevator elevator=new Elevator();
		ALS_Schedule als=new ALS_Schedule();
		
		requestlist.add(new Request(1,1,1,0,"[FR,1,UP,0]"));
		requestlist.add(new Request(1,4,1,0,"[FR,4,UP,0]"));
		requestlist.add(new Request(1,2,2,0,"[FR,2,DOWN,0]"));
		requestlist.add(new Request(0,8,0,0,"[ER,8,0]"));
		requestlist.add(new Request(1,6,1,2,"[FR,6,UP,2]"));
		
		als.override(requestlist, elevator);
		
		assertEquals("[FR,1,UP,0]/(1,STILL,1.0)"+System.lineSeparator()
					+"[FR,4,UP,0]/(4,UP,2.5)"+System.lineSeparator()
					+"[FR,6,UP,2]/(6,UP,4.5)"+System.lineSeparator()
					+"[ER,8,0]/(8,UP,6.5)"+System.lineSeparator()
					+"[FR,2,DOWN,0]/(2,DOWN,10.5)"+System.lineSeparator(),output.toString());
		
		System.setOut(System.out);		
	}
	
	@Test
	//向下未捎带提前
	public void testOverride12() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		
		RequestList requestlist=new RequestList();
		Elevator elevator=new Elevator();
		ALS_Schedule als=new ALS_Schedule();
		
		requestlist.add(new Request(1,1,1,0,"[FR,1,UP,0]"));
		requestlist.add(new Request(1,10,2,0,"[FR,10,DOWN,0]"));
		requestlist.add(new Request(1,4,2,10,"[FR,4,DOWN,10]"));
		requestlist.add(new Request(1,5,1,10,"[FR,5,UP,10]"));
		requestlist.add(new Request(0,2,0,10,"[ER,2,10]"));
		requestlist.add(new Request(1,3,2,12,"[FR,3,DOWN,12]"));
		
		als.override(requestlist, elevator);
		
		assertEquals("[FR,1,UP,0]/(1,STILL,1.0)"+System.lineSeparator()
					+"[FR,10,DOWN,0]/(10,UP,5.5)"+System.lineSeparator()
					+"[FR,4,DOWN,10]/(4,DOWN,13.0)"+System.lineSeparator()
					+"[FR,3,DOWN,12]/(3,DOWN,14.5)"+System.lineSeparator()
					+"[ER,2,10]/(2,DOWN,16.0)"+System.lineSeparator()
					+"[FR,5,UP,10]/(5,UP,18.5)"+System.lineSeparator(),output.toString());
		
		System.setOut(System.out);		
	}
	
	@Test
	//向下停一会儿再运行
	public void testOverride13() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		
		RequestList requestlist=new RequestList();
		Elevator elevator=new Elevator();
		ALS_Schedule als=new ALS_Schedule();
		
		requestlist.add(new Request(1,1,1,0,"[FR,1,UP,0]"));
		requestlist.add(new Request(1,10,2,0,"[FR,10,DOWN,0]"));
		requestlist.add(new Request(1,4,2,10,"[FR,4,DOWN,10]"));
		requestlist.add(new Request(0,2,0,100,"[ER,2,100]"));
		
		als.override(requestlist, elevator);
		
		assertEquals("[FR,1,UP,0]/(1,STILL,1.0)"+System.lineSeparator()
					+"[FR,10,DOWN,0]/(10,UP,5.5)"+System.lineSeparator()
					+"[FR,4,DOWN,10]/(4,DOWN,13.0)"+System.lineSeparator()
					+"[ER,2,100]/(2,DOWN,101.0)"+System.lineSeparator(),output.toString());
		
		System.setOut(System.out);		
	}
	
	@Test
	//增加分支覆盖率1
	public void testOverride14() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		
		RequestList requestlist=new RequestList();
		Elevator elevator=new Elevator();
		ALS_Schedule als=new ALS_Schedule();
		
		requestlist.add(new Request(1,1,1,0,"[FR,1,UP,0]"));
		requestlist.add(new Request(1,7,2,0,"[FR,7,DOWN,0]"));
		requestlist.add(new Request(0,2,0,3,"[ER,2,3]"));
		
		als.override(requestlist, elevator);
		
		assertEquals("[FR,1,UP,0]/(1,STILL,1.0)"+System.lineSeparator()
					+"[FR,7,DOWN,0]/(7,UP,4.0)"+System.lineSeparator()
					+"[ER,2,3]/(2,DOWN,7.5)"+System.lineSeparator(),output.toString());
		
		System.setOut(System.out);		
	}
	
	@Test
	//增加分支覆盖率2
	public void testOverride15() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		
		RequestList requestlist=new RequestList();
		Elevator elevator=new Elevator();
		ALS_Schedule als=new ALS_Schedule();
		
		requestlist.add(new Request(1,1,1,0,"[FR,1,UP,0]"));
		requestlist.add(new Request(1,10,2,0,"[FR,10,DOWN,0]"));
		requestlist.add(new Request(1,2,2,10,"[FR,2,DOWN,10]"));
		requestlist.add(new Request(0,6,0,13,"[ER,6,13]"));
		
		als.override(requestlist, elevator);
		
		assertEquals("[FR,1,UP,0]/(1,STILL,1.0)"+System.lineSeparator()
					+"[FR,10,DOWN,0]/(10,UP,5.5)"+System.lineSeparator()
					+"[FR,2,DOWN,10]/(2,DOWN,14.0)"+System.lineSeparator()
					+"[ER,6,13]/(6,UP,17.0)"+System.lineSeparator(),output.toString());
		
		System.setOut(System.out);		
	}
	
	@Test
	//这是个bug
	public void testOverride16() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		
		RequestList requestlist=new RequestList();
		Elevator elevator=new Elevator();
		ALS_Schedule als=new ALS_Schedule();
		
		requestlist.add(new Request(1,1,1,0,"[FR,1,UP,0]"));
		requestlist.add(new Request(1,5,1,0,"[FR,5,UP,0]"));
		requestlist.add(new Request(1,7,1,0,"[FR,7,UP,0]"));
		requestlist.add(new Request(0,7,0,0,"[ER,7,0]"));
		
		als.override(requestlist, elevator);
		
		assertEquals("[FR,1,UP,0]/(1,STILL,1.0)"+System.lineSeparator()
					+"[FR,5,UP,0]/(5,UP,3.0)"+System.lineSeparator()
					+"[FR,7,UP,0]/(7,UP,5.0)"+System.lineSeparator()
					+"[ER,7,0]/(7,UP,5.0)"+System.lineSeparator(),output.toString());
		
		System.setOut(System.out);		
	}
}
