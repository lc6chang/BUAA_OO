package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import oo13.Request;
import oo13.RequestList;

public class RequestListTest {
	@Test
	public void testlen() {
		RequestList requestlist=new RequestList();
		assertEquals(0,requestlist.len());
		requestlist.add(new Request(1,5,1,10,"[FR,5,UP,10]"));
		requestlist.add(new Request(1,6,2,15,"[FR,6,DOWN,15]"));
		requestlist.add(new Request(0,7,0,20,"[ER,7,20]"));
		assertEquals(3,requestlist.len());
	}

	@Test
	public void testSearch() {
		RequestList requestlist=new RequestList();
		requestlist.add(new Request(1,5,1,10,"[FR,5,UP,10]"));
		requestlist.add(new Request(1,6,2,15,"[FR,6,DOWN,15]"));
		requestlist.add(new Request(0,7,0,20,"[ER,7,20]"));
		assertEquals("[FR,5,UP,10]",requestlist.search(0).outword());
		assertEquals("[FR,6,DOWN,15]",requestlist.search(1).outword());
		assertEquals("[ER,7,20]",requestlist.search(2).outword());
	}

	@Test
	public void testLast() {
		RequestList requestlist=new RequestList();
		requestlist.add(new Request(1,5,1,10,"[FR,5,UP,10]"));
		requestlist.add(new Request(1,6,2,15,"[FR,6,DOWN,15]"));
		requestlist.add(new Request(0,7,0,20,"[ER,7,20]"));
		assertEquals("[ER,7,20]",requestlist.last().outword());
	}

	@Test
	public void testMoveto1() {
		RequestList requestlist=new RequestList();
		requestlist.add(new Request(1,5,1,10,"[FR,5,UP,10]"));
		requestlist.add(new Request(1,6,2,15,"[FR,6,DOWN,15]"));
		requestlist.add(new Request(0,7,0,20,"[ER,7,20]"));
		requestlist.moveto(2,0);
		assertEquals("[ER,7,20]",requestlist.search(0).outword());
		assertEquals("[FR,5,UP,10]",requestlist.search(1).outword());
		assertEquals("[FR,6,DOWN,15]",requestlist.search(2).outword());
		assertEquals("[FR,6,DOWN,15]",requestlist.last().outword());
	}
	
	@Test
	public void testMoveto2() {
		RequestList requestlist=new RequestList();
		requestlist.add(new Request(1,5,1,10,"[FR,5,UP,10]"));
		requestlist.add(new Request(1,6,2,15,"[FR,6,DOWN,15]"));
		requestlist.add(new Request(0,7,0,20,"[ER,7,20]"));
		requestlist.moveto(0,2);
		assertEquals("[FR,6,DOWN,15]",requestlist.search(0).outword());
		assertEquals("[ER,7,20]",requestlist.search(1).outword());
		assertEquals("[FR,5,UP,10]",requestlist.search(2).outword());
		assertEquals("[FR,5,UP,10]",requestlist.last().outword());
	}
	
	@Test
	public void testrepOk() {
		RequestList requestlist=new RequestList();
		assertEquals(true,requestlist.repOk());
	}
	
}
