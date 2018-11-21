package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import oo13.Request;

public class RequestTest {
	public static Request request1=new Request(1,5,2,10,"[FR,5,DOWN,10]");
	public static Request request2=new Request(0,6,0,15,"[ER,6,15]");
	
	@Test
	public void testIssame1() {
		assertEquals(0,request1.issame(new Request(1,6,2,10,"[FR,6,DOWN,10]")));
	}
	@Test
	public void testIssame2() {
		assertEquals(0,request1.issame(new Request(1,5,1,10,"[FR,5,UP,10]")));
	}
	@Test
	public void testIssame3() {
		assertEquals(0,request1.issame(new Request(0,5,0,10,"[ER,5,10]")));
	}
	@Test
	public void testIssame4() {
		assertEquals(1,request1.issame(new Request(1,5,2,111,"[FR,5,DOWN,111]")));
	}
	@Test
	public void testIssame5() {
		assertEquals(1,request2.issame(new Request(0,6,0,111,"[ER,6,111]")));
	}
	@Test
	public void testrepOk() {
		assertEquals(false,new Request(3,6,0,111,"[ER,6,111]").repOk());
		assertEquals(false,new Request(1,0,0,111,"[ER,6,111]").repOk());
		assertEquals(false,new Request(1,11,0,111,"[ER,6,111]").repOk());
		assertEquals(false,new Request(0,6,1,111,"[ER,6,111]").repOk());
		assertEquals(false,new Request(1,6,0,111,"[ER,6,111]").repOk());
		assertEquals(false,new Request(1,1,2,111,"[ER,6,111]").repOk());
		assertEquals(false,new Request(1,10,1,111,"[ER,6,111]").repOk());
		assertEquals(false,new Request(0,6,0,-111,"[ER,6,111]").repOk());
		assertEquals(false,new Request(0,6,0,111,null).repOk());
		assertEquals(true,new Request(0,6,0,111,"[ER,6,111]").repOk());
		assertEquals(true,new Request(1,1,1,111,"[FR,1,UP,111]").repOk());
		assertEquals(true,new Request(1,10,2,111,"[FR,10,DOWN,111]").repOk());
	}
}
