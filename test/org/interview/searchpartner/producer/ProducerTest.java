package org.interview.searchpartner.producer;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.interview.searchpartner.bean.MachineParts;
import org.junit.Before;
import org.junit.Test;

public class ProducerTest {
	
	private int noOfMachines = 1;
	private int noOfBolts = 2;;
	private BlockingQueue<MachineParts> queue=null;
	private Producer producer;



	@Before
	public void setUp() throws Exception {
		queue = new ArrayBlockingQueue<MachineParts>(noOfMachines + noOfBolts);
		producer = new Producer("TestProducer", noOfMachines, noOfBolts, queue);
	}



	@Test
	public void testRun() {
		producer.run();
		assertEquals(false, queue.isEmpty());
		assertEquals(3, queue.size());
	}

	@Test
	public void testToString() {
		assertEquals("TestProducer",producer.toString());
	}

}
