package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import oo13.Elevator;
import oo13.Request;

public class ElevatorTest {

	@Test
	public void testMovetime1() {
		Elevator elevator=new Elevator();
		elevator.UpandDown(4,1,0,20);
		assertEquals(1.0,elevator.movetime(new Request(1,6,1,20,"[FR,6,UP,20]")),0.00001);
	}
	@Test
	public void testMovetime2() {
		Elevator elevator=new Elevator();
		elevator.UpandDown(4,1,0,20);
		assertEquals(1.5,elevator.movetime(new Request(0,1,1,20,"[ER,1,20]")),0.00001);
	}
	
	@Test
	public void testMoveway1() {
		Elevator elevator=new Elevator();
		elevator.UpandDown(4,1,0,20);
		assertEquals(1,elevator.moveway(new Request(0,10,0,20,"[ER,10,20]")));
	}
	@Test
	public void testMoveway2() {
		Elevator elevator=new Elevator();
		elevator.UpandDown(4,1,0,20);
		assertEquals(0,elevator.moveway(new Request(0,4,0,20,"[ER,4,20]")));
	}
	@Test
	public void testMoveway3() {
		Elevator elevator=new Elevator();
		elevator.UpandDown(4,1,0,20);
		assertEquals(2,elevator.moveway(new Request(1,1,1,20,"[FR,1,UP,20]")));
	}
		
	@Test
	public void testStartime1() {
		Elevator elevator=new Elevator();
		elevator.UpandDown(4,1,0,20);
		assertEquals(25.0,elevator.startime(new Request(1,1,1,25,"[FR,1,UP,25]")),0.00001);
	}
	@Test
	public void testStartime2() {
		Elevator elevator=new Elevator();
		elevator.UpandDown(4,1,0,20);
		assertEquals(20.0,elevator.startime(new Request(1,1,1,15,"[FR,1,UP,15]")),0.00001);
	}
	@Test
	public void testStartime3() {
		Elevator elevator=new Elevator();
		elevator.UpandDown(4,1,0,20);
		assertEquals(20.0,elevator.startime(new Request(1,1,1,20,"[FR,1,UP,20]")),0.00001);
	}

	@Test
	public void testToString1() {
		Elevator elevator=new Elevator();
		elevator.UpandDown(4,1,0,20);
		assertEquals("(4,UP,20.0)",elevator.toString());
	}
	@Test
	public void testToString2() {
		Elevator elevator=new Elevator();
		elevator.UpandDown(4,0,0,20.5);
		assertEquals("(4,STILL,21.5)",elevator.toString());
	}
	@Test
	public void testToString3() {
		Elevator elevator=new Elevator();
		elevator.UpandDown(5,2,0,20.5);
		assertEquals("(5,DOWN,20.5)",elevator.toString());
	}
	
	@Test
	public void testrepOk() {
		Elevator elevator=new Elevator();
		elevator.UpandDown(0,2,0,20.5);
		assertEquals(false,elevator.repOk());
		elevator.UpandDown(11,2,0,20.5);
		assertEquals(false,elevator.repOk());
		elevator.UpandDown(5,-1,0,20.5);
		assertEquals(false,elevator.repOk());
		elevator.UpandDown(5,3,0,20.5);
		assertEquals(false,elevator.repOk());
		elevator.UpandDown(5,2,0,-20.5);
		assertEquals(false,elevator.repOk());
		elevator.UpandDown(5,2,0,20.5);
		assertEquals(true,elevator.repOk());
	}
}
