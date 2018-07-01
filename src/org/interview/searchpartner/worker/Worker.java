package org.interview.searchpartner.worker;

import java.util.concurrent.BlockingQueue;

import org.interview.searchpartner.Factory;
import org.interview.searchpartner.bean.Bolt;
import org.interview.searchpartner.bean.Machine;
import org.interview.searchpartner.bean.MachineParts;

public class Worker implements Runnable {

	private String workerName;
	private int noOfMachines = 0;
	private int noOfBolts = 0;
	private int timeInSecondsToCompleteTheJob = 0;
	private BlockingQueue<MachineParts> queue = null;

	public Worker(String s, int seconds, BlockingQueue<MachineParts> queue) {
		this.workerName = s;
		this.timeInSecondsToCompleteTheJob = seconds;
		this.queue = queue;
	}

	@Override
	public void run() {
		System.out.println(workerName+" has started.");
		processCommand();
		System.out.println(workerName+"  has ended.");
	}

	private void processCommand() {
		try {
			do {
				//System.out.println(queue.size());
				MachineParts mp = queue.peek();
				if ((noOfMachines != 1) && (mp instanceof Machine)) {
					queue.take();
					noOfMachines++;
					System.out.println(workerName+" has picked up the "+mp.getClass().getSimpleName());
				}
				if ((noOfBolts != 2) && (mp instanceof Bolt)) {
					queue.take();
					noOfBolts++;
					System.out.println(workerName+" has picked up the "+mp.getClass().getSimpleName());
				}
				if((noOfMachines == 1) && (noOfBolts == 2))
					break;
			} while (!(noOfMachines == 1 && noOfBolts == 2));
			if ((noOfMachines == 1) && (noOfBolts == 2)) {
				System.out.println(workerName+" is assembling the product");

				Thread.sleep(timeInSecondsToCompleteTheJob * 1000);
				System.out.println(workerName+" has assembled the product");
				Factory.getTotalProducts().incrementAndGet();
				
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return this.workerName;
	}
}
