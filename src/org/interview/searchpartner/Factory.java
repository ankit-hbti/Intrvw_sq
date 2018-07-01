package org.interview.searchpartner;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.interview.searchpartner.bean.MachineParts;
import org.interview.searchpartner.producer.Producer;
import org.interview.searchpartner.worker.Worker;

public class Factory {

	private static AtomicInteger totalProducts = new AtomicInteger(0);

	public static void main(String[] args) {
		int noOfMachines = Integer.parseInt(args[0]);
		int noOfBolts = Integer.parseInt(args[1]);
		int timeInSecondsToCompleteTheJob = Integer.parseInt(args[2]);
		System.out.println("Number of Machines: "+noOfMachines);
		System.out.println("Number Of Bolts:  "+noOfBolts);
		System.out.println("Time taken to assemble a machine in seconds:  "+timeInSecondsToCompleteTheJob);
		BlockingQueue<MachineParts> queue = new ArrayBlockingQueue<>(noOfMachines + noOfBolts);
		Producer producer = new Producer("Producer", noOfMachines, noOfBolts, queue);
		new Thread(producer).start();
		double startTime=System.currentTimeMillis();
		System.out.println("Start Time: "+startTime);
		ExecutorService executor = Executors.newFixedThreadPool(3);
		for (int i = 1; i < 4; i++) {
			Runnable worker = new Worker("Worker" + i, timeInSecondsToCompleteTheJob, queue);
			executor.execute(worker);
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		double endTime=System.currentTimeMillis();
		System.out.println("End Time: "+endTime);
		System.out.println("Total Time: "+(endTime-startTime)/1000);
		System.out.println("Finished all threads");
		System.out.println("Total Products : "+getTotalProducts());
	}

	public static AtomicInteger getTotalProducts() {
		return totalProducts;
	}

	public static void setTotalProducts(AtomicInteger totalProducts) {
		Factory.totalProducts = totalProducts;
	}


}
