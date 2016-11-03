package common.scheduling.quartz;

import static org.quartz.JobBuilder.newJob;

import java.util.Map;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Helper for working with Quartz scheduling API
 * @author Dima
 */
public class QuartzHelper {

	private Scheduler scheduler;

	public QuartzHelper() throws SchedulerException {
		SchedulerFactory factory = new StdSchedulerFactory();
		this.scheduler = factory.getScheduler();
	}

	/**
	 * @param task class to schedule, must implement quartz job interface
	 * @param taskVo Scheduling parameters
	 * @param identity string for identification of the job
	 * @throws SchedulerException scheduler error
	 */
	public void scheduleTask(Task task, TaskVO taskVo, String identity) throws SchedulerException {
		
		JobDetail jobDetail = newJob(task.getClass())
				.withIdentity(identity)
				.build();
	
		Map<String, Object> dataMap = taskVo.getDataMap();

		if (dataMap != null) {
			jobDetail.getJobDataMap().putAll(dataMap);
		}

		scheduler.scheduleJob(jobDetail, taskVo.getTrigger());
		scheduler.start();
	}

	/**
	 * Will shutdown current schedule
	 * @throws SchedulerException scheduler error
	 */
	public void shutDownScheduler() throws SchedulerException {
		scheduler.shutdown();
	}

	/**
	 * Removes task from schedule
	 * @param identity unique identificator of the task
	 * @throws SchedulerException exception of scheduler
	 */
	public void killScheduledTask(String identity) throws SchedulerException {
		scheduler.deleteJob(JobKey.jobKey(identity));
	}

}