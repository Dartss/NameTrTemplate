package manager.impl.controller;

import common.json.JsonHandler;
import common.json.impl.JsonHandlerImpl;
import common.queuer.QueuerPoolHandler;
import common.queuer.QueuerPoolHandlerImpl;
import manager.Manager;
import common.template.manageradaptor.vo.JobVO;
import properties.template.NamesTrProperties;

import java.rmi.RemoteException;
import java.util.logging.Logger;

/**
 * Controller to handle the communication between manager and queue
 *
 * @author rud
 */
public class QueueControllerImpl implements Runnable
{

    private Manager manager;

    private QueuerPoolHandler queuerPoolHandler;
    private String queueHost;
    private int queuePort;
    private String inputQueueName;
    private JsonHandler jsonHandler;

    private static final long SLEEP_TIME_MILLIS = 3000;
    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public QueueControllerImpl(Manager manager)
    {
	loadProperties();
	this.manager = manager;
	this.queuerPoolHandler = new QueuerPoolHandlerImpl(queueHost, queuePort);
	this.jsonHandler = new JsonHandlerImpl();
    }

    private void loadProperties()
    {
	new NamesTrProperties();

	this.queueHost = NamesTrProperties.getRedisHost();
	this.queuePort = NamesTrProperties.getRedisPort();
	this.inputQueueName = NamesTrProperties.getInputQueueName();
    }

    @Override public void run()
    {
	while (true)
	{
	    // pull job
	    JobVO jobVO = pullJob(SLEEP_TIME_MILLIS);
	    //			logger.info("Controller sending job to adaptor " + jsonHandler.serialize(jobVO));
	    // send job to manager
	    boolean jobAccepted = false;
	    try
	    {
		jobAccepted = this.manager.executeJob(jobVO);
	    } catch (RemoteException e)
	    {
		// this exception can't be thrown
	    }

	    //			this.logger.info("Job accepted - " + jobAccepted);
	    if (!jobAccepted)
	    {
		requeueJob(jobVO);
		// set in wait mode
		synchronized (this)
		{
		    try
		    {
			this.wait();
		    } catch (InterruptedException e)
		    {
			this.logger.severe(e.getMessage());
		    }
		}
	    }
	}
    }

    public void requeueJob(JobVO jobVO)
    {
	this.queuerPoolHandler.lpush(this.inputQueueName, this.jsonHandler.serialize(jobVO));
    }

    /**
     * pulls item from redis queue
     *
     * @param sleepTimeMillis thread will sleep for this time if lpop return null
     * @return
     */
    private JobVO pullJob(long sleepTimeMillis)
    {
	String jobStr = null;

	while (jobStr == null)
	{
	    jobStr = this.queuerPoolHandler.lpop(this.inputQueueName);

	    // if we pulled null, then sleep for some time
	    if (jobStr == null)
	    {
		try
		{
		    Thread.sleep(sleepTimeMillis);
		} catch (InterruptedException e)
		{
		    this.logger.severe(e.getMessage());
		}
	    }
	}

	return (JobVO) jsonHandler.deserialize(jobStr, new JobVO());
    }

}