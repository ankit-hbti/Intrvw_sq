package org.interview.searchpartner.worker;

import static org.junit.Assert.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.interview.searchpartner.Factory;
import org.interview.searchpartner.bean.MachineParts;
import org.interview.searchpartner.producer.Producer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class WorkerTest {
	
	private static int noOfMachines = 1;
	private static int noOfBolts = 2;
	private int timeInSecondsToCompleteTheJob = 5;
	private static BlockingQueue<MachineParts> queue = new ArrayBlockingQueue<MachineParts>(noOfMachines + noOfBolts);
	private Worker worker;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		Producer producer = new Producer("Producer", noOfMachines, noOfBolts, queue);
		new Thread(producer).start();
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		worker = new Worker("TestWorker", timeInSecondsToCompleteTheJob, queue);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore
	@Test
	public void testWorker() {
		fail("Not yet implemented");
	}

	@Test
	public void testRun() {
		//new Thread(worker).start();
		worker.run();
		assertEquals(1,Factory.getTotalProducts().get());
	}

	@Test
	public void testToString() {
		assertEquals("TestWorker",worker.toString());
	}

}
