package org.interview.searchpartner.worker;

import java.util.concurrent.BlockingQueue;

import org.interview.searchpartner.Factory;
import org.interview.searchpartner.bean.Bolt;
import org.interview.searchpartner.bean.CollectedParts;
import org.interview.searchpartner.bean.Machine;
import org.interview.searchpartner.bean.MachineParts;

public class Worker implements Runnable {

	private String workerName;
	private ThreadLocal<CollectedParts> collectedParts = new ThreadLocal<CollectedParts>() {
		@Override
		protected CollectedParts initialValue() {
			return new CollectedParts(0, 0);
		}
	};
	private int timeInSecondsToCompleteTheJob = 0;
	private BlockingQueue<MachineParts> queue = null;

	public Worker(String s, int seconds, BlockingQueue<MachineParts> queue) {
		this.workerName = s;
		this.timeInSecondsToCompleteTheJob = seconds;
		this.queue = queue;
	}

	@Override
	public void run() {
		System.out.println(workerName + " has started.");

		processCommand();

		System.out.println(workerName + "  has ended.");
	}

	private void processCommand() {
		try {
			outer: do {

				if (!queue.isEmpty()) {
					do {

						synchronized (queue) {

							if ((collectedParts.get().getNoOfBolts() < 2) && (!queue.contains(new Bolt()))) {
								if (collectedParts.get().getNoOfBolts() == 1) {
									queue.put(new Bolt());
									collectedParts.get().setNoOfBolts(collectedParts.get().getNoOfBolts() - 1);

								}
								break outer;

							}
							if ((collectedParts.get().getNoOfMachines() < 1) && (!queue.contains(new Machine()))) {
								break outer;

							}

							MachineParts mp = queue.poll();
							boolean consumeFlag = false;
							if (null != mp) {

								System.out.println(
										workerName + " has " + mp.getClass().getSimpleName() + " for him to pick");

								if ((collectedParts.get().getNoOfMachines() < 1) && (mp instanceof Machine)
										&& (collectedParts.get().getNoOfBolts() <= 2)) {
									collectedParts.get().setNoOfMachines(collectedParts.get().getNoOfMachines() + 1);
									System.out.println(
											workerName + " has picked up the " + mp.getClass().getSimpleName());
									consumeFlag = true;
								}
								if ((collectedParts.get().getNoOfBolts() < 2) && (mp instanceof Bolt)
										&& (collectedParts.get().getNoOfMachines() <= 1)) {
									collectedParts.get().setNoOfBolts(collectedParts.get().getNoOfBolts() + 1);
									System.out.println(
											workerName + " has picked up the " + mp.getClass().getSimpleName());
									consumeFlag = true;
								}

							}

							else {
								System.out.println(workerName + " has nothing  for him to pick");
								break outer;
							}
							if (!consumeFlag) {

								System.out.println(workerName + " didn't pick up  " + mp.getClass().getSimpleName());

								queue.put(mp);

							}
						}

						System.out.println(workerName + " has " + collectedParts.get().getNoOfMachines()
								+ " machine and " + collectedParts.get().getNoOfBolts() + " bolts");

						if ((collectedParts.get().getNoOfMachines() >= 1) && (collectedParts.get().getNoOfBolts() >= 2))
							break;
					} while (!(collectedParts.get().getNoOfMachines() >= 1
							&& collectedParts.get().getNoOfBolts() >= 2));
					if ((collectedParts.get().getNoOfMachines() >= 1) && (collectedParts.get().getNoOfBolts() >= 2)) {
						System.out.println(workerName + " is assembling the product");
						collectedParts.get().setNoOfMachines(collectedParts.get().getNoOfMachines() - 1);
						collectedParts.get().setNoOfBolts(collectedParts.get().getNoOfBolts() - 2);
						Thread.sleep(timeInSecondsToCompleteTheJob * 1000);
						System.out.println(workerName + " has assembled the product");
						Factory.getTotalProducts().incrementAndGet();
					}

				}
			} while (!queue.isEmpty());

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return this.workerName;
	}
}
