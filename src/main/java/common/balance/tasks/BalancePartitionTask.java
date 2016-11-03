package common.balance.tasks;

import jsmarty.core.common.queuer.QueuerPoolHandler;
import jsmarty.core.common.queuer.QueuerPoolHandlerImpl;

public class BalancePartitionTask implements Runnable{
	
	private long actualLifetimeInSeconds;
	private long maxLifetimeInSeconds;
	private long timeRangeInSeconds;
	private int partitionCount;
	
	private String queueName;
	private String queueHost;
	private int queuePort;
	private QueuerPoolHandler queueHandler;
	
	public BalancePartitionTask(long timeRangeInSeconds, long maxLifetimeInSeconds, int partitionCount, String queueName, String queueHost, int queuePort) {
		super();
		this.timeRangeInSeconds = timeRangeInSeconds*1000;
		this.maxLifetimeInSeconds = maxLifetimeInSeconds*1000;
		this.partitionCount = partitionCount;
		this.queueName = queueName;
		this.queueHost = queueHost;
		this.queuePort = queuePort;
		this.queueHandler = new QueuerPoolHandlerImpl(queueHost, queuePort);
	}

	@Override
	public void run() {
		while(actualLifetimeInSeconds<maxLifetimeInSeconds){
			System.out.println(actualLifetimeInSeconds);
			this.queueHandler.set(queueName, partitionCount+"");
			try {
				Thread.sleep(timeRangeInSeconds);
				actualLifetimeInSeconds+=timeRangeInSeconds;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}