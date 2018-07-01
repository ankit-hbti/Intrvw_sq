package org.interview.searchpartner.producer;

import java.util.concurrent.BlockingQueue;

import org.interview.searchpartner.bean.Bolt;
import org.interview.searchpartner.bean.Machine;
import org.interview.searchpartner.bean.MachineParts;

public class Producer implements Runnable {

	private String command;
	private int noOfMachines = 0;
	private int noOfBolts = 0;
	private BlockingQueue<MachineParts> queue = null;

	public Producer(String s, int noOfMachines, int noOfBolts, BlockingQueue<MachineParts> queue) {
		this.command = s;
		this.noOfMachines = noOfMachines;
		this.noOfBolts = noOfBolts;
		this.queue = queue;
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " Start. Command = " + command);
		processCommand();
		System.out.println(Thread.currentThread().getName() + " End.");
	}

	private void processCommand() {
		try {
			do {
				if (noOfMachines > 0) {
					queue.put(new Machine());
					noOfMachines--;
				}
				if (noOfBolts > 0) {
					queue.put(new Bolt());
					noOfBolts--;
				}
			} while (noOfMachines > 0 || noOfBolts > 0);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return this.command;
	}
}
