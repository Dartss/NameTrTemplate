package common.scheduling.quartz;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Logger;

import org.quartz.Trigger;

/**
 * 
 * This represents the Value Object used in scheduling a task
 * 
 * 
 * Required : time params / cron expression
 * Optional : data map
 * 
 * @author Dima
 */
public class TaskVO implements Serializable{

	private Trigger trigger = null;
	private HashMap<String, Object> dataMap;

	private Logger log = Logger.getLogger(TaskVO.class.getName());

	/**
	 * Constructor to create a simple trigger
	 * Creates trigger to fire task single time
	 * @param startTime time for task to be started
	 */
	public TaskVO(Date startDate) {
		trigger = newTrigger()
				.startAt(startDate)
				.withSchedule(simpleSchedule())
				.build();
	}

	/**
	 * Constructor to create a cron trigger
	 * @param expression regular expression of Quartz API.
	 */
	public TaskVO(String cronExpression) {
		trigger = newTrigger()
				.withSchedule(cronSchedule(cronExpression))
				.build();
	}

	/**
	 * Constructor to create a cron trigger
	 * @param expression regular expression of Quartz API.
	 */
	public TaskVO(Date startingDate, String cronExpression) {
		trigger = newTrigger()
				.startAt(startingDate)
				.withSchedule(cronSchedule(cronExpression))
				.build();
	}

	/**
	 * @param dataMap map with values that should be delivered to the task class
	 */
	public void setDataMap(HashMap<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	/**
	 * @return values for task class map
	 */
	public HashMap<String, Object> getDataMap() {
		return this.dataMap;
	}

	/**
	 * @return time trigger that schedule task firing
	 */
	public Trigger getTrigger() {
		return this.trigger;
	}

}