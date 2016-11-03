package common.scheduling.quartz;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import jsmarty.core.common.utils.NumberUtils;

/**
 * Handler for scheduling tasks
 * build on top of Quartz Java Library
 *
 * @author Dima
 */
public class TaskScheduleHandler {

	private QuartzHelper quartzHelper;
	private ScheduledExecutorService scheduledExecutorService;
	private Logger log = Logger.getLogger(this.getClass().getName());

	public TaskScheduleHandler() {
	}

	public QuartzHelper getQuartzHelper() {
		if(this.quartzHelper == null){
			try {
				this.quartzHelper = new QuartzHelper();
			} catch (Exception e) {
				log.log(Level.INFO, "Failed to create quartzHelper: ", e);
			}
		}
		return quartzHelper;
	}

	public ScheduledExecutorService getScheduledExecutorService() {
		if(this.scheduledExecutorService == null){
			this.scheduledExecutorService = Executors.newScheduledThreadPool(5);
		}
		return scheduledExecutorService;
	}

	/**
	 * Schedules the task with given parameters
	 * @param task task to schedule
	 * @param parameters time condition and additional values for task
	 * @param identity string to identify the task
	 */
	public void scheduleTask(Task task, TaskVO parameters, String identity) {
		try {
			if(identity == null){
				identity = NumberUtils.getRandomInt()+""+System.currentTimeMillis();
			}
			getQuartzHelper().scheduleTask(task, parameters, identity);
		} catch (Exception e) {
			log.log(Level.INFO, "Failed to schedule task: ", e);
		}
	}

	/**
	 * Schedule task to be fired every day at specified time
	 *
	 * @param task task to schedule
	 * @param data map with values that task will operate with
	 * @param startDate If not null - defines date for task to be started
	 *                     and time to be repeated every day after.
	 *                     If null - will starts right now and will be
	 *                     repeated at the same time every day
	 * @param identity String to identify this task from others
	 *                 (should be unique)
	 */
	public void scheduleDailyTask(Task task, HashMap<String, Object> data,
			Date startDate, String identity) {

		if(startDate == null) {
			startDate = new Date(); //Starting date by default is now;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		int second = cal.get(Calendar.SECOND);
		int minute = cal.get(Calendar.MINUTE);
		int hour = cal.get(Calendar.HOUR_OF_DAY);

		String cronExpression = second + " " + minute + " " + hour + " * * ?";
		System.out.println("Expression: " + cronExpression);
		System.out.println("Starting date: " + startDate);
		TaskVO taskVo = new TaskVO(cronExpression);
		taskVo.setDataMap(data);
		scheduleTask(task, taskVo, identity);
	}

	/**
	 * Schedule task to be fired every month at specified day and time
	 *
	 * @param task task to schedule
	 * @param data map with values that task will operate with
	 * @param startDate If not null - defines date for task to be started
	 *                     and time to be repeated every day after.
	 *                     If null - will starts right now and will be
	 *                     repeated at the same time and day every month
	 * @param identity String to identify this task from others
	 *                 (should be unique)
	 */
	public void scheduleMonthlyTask(Task task, HashMap<String, Object> data,
			Date startDate, String identity) {

		if(startDate == null){
			startDate = new Date();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		int second = cal.get(Calendar.SECOND);
		int minute = cal.get(Calendar.MINUTE);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

		String cronExpression = second + " " + minute + " " + hour + " " + dayOfMonth + " * ?";
		System.out.println("Expression: " + cronExpression);
		System.out.println("Starting date: " + startDate);
		TaskVO taskVo = new TaskVO(cronExpression);
		taskVo.setDataMap(data);
		scheduleTask(task, taskVo, identity);
	}

	/**
	 * Schedule task to be fired after N days from specified date
	 * or from now.
	 *
	 * @param task task to schedule
	 * @param data map with values that task will operate with
	 * @param startDate If not null - defines date and time for
	 *                        task to be started after delay.
	 *                        If null - task will be started after delay
	 *                        counted from now.
	 * @param daysNumber Days of delay before firing task.
	 * @param identity String to identify this task from others
	 *                 (should be unique)
	 */
	public void scheduleDaysDelayedTask(Task task, HashMap<String, Object> data,
			Date startDate, int daysNumber,
			String identity) {
		if(startDate == null) {
			startDate = new Date();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.add(Calendar.DATE, daysNumber);

		Date startingDate = cal.getTime();

		TaskVO taskVo = new TaskVO(startingDate);
		taskVo.setDataMap(data);
		scheduleTask(task, taskVo, identity);
	}

	/**
	 * Schedule task to be fired after N hours from specified date
	 * or from now.
	 * @param job task to schedule
	 * @param data map with values that task will operate with
	 * @param startDate If not null - defines date and time for
	 *                        task to be started after delay.
	 *                        If null - task will be started after delay
	 *                        counted from now.
	 * @param hoursNumber Hours count of delay before firing task.
	 * @param identity String to identify this task from others
	 *                 (should be unique)
	 */
	public void scheduleHoursDelayedTask(Task job, HashMap<String, Object> data,
			Date startDate, int hoursNumber,
			String identity) {

		if(startDate == null) {
			startDate = new Date();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.add(Calendar.HOUR, hoursNumber);

		Date startingDate = cal.getTime();

		TaskVO taskVo = new TaskVO(startingDate);
		taskVo.setDataMap(data);
		scheduleTask(job, taskVo, identity);
	}

	/**
	 * Schedule task to be fired according to cron expression
	 * @param job task to schedule
	 * @param data map with values that task will operate with
	 * @param identity String to identify this task from others
	 *                 (should be unique)
	 * @param cronExpression String expression that defines when task
	 *                       will be fired. For detailed information see:
	 *                   http://www.quartz-scheduler.org/documentation/quartz-2.2.x/tutorials/tutorial-lesson-06.html
	 *
	 *                   String should be in format like this:
	 *                   "seconds minutes hours day-of-month month day-of-week"
	 *
	 *                   All of the fields have a set of valid values that can be specified.
	 *                   These values should be fairly obvious - such as the numbers 0 to 59
	 *                   for seconds and minutes, and the values 0 to 23 for hours.
	 *                   Day-of-Month can be any value 1-31, but you need to be careful about
	 *                   how many days are in a given month! Months can be specified as values
	 *                   between 0 and 11.
	 *
	 *                   "0 0 12 ? * WED" - means every Wednesday at 12:00:00 pm;
	 *
	 *                   The ‘?’ character is allowed for the day-of-month and day-of-week fields.
	 *                   It is used to specify “no specific value”.
	 *
	 *                   A ‘*’ in the Day-Of-Week field would therefore obviously mean
	 *                   “every day of the week” - same to other fields.
	 *
	 *                   The ‘L’ character is allowed for the day-of-month fields.
	 *                   This character is short-hand for “last”.. For example, the value “L” in the
	 *                   day-of-month field means “the last day of the month” - day 31 for January,
	 *                   day 28 for February on non-leap years.
	 *
	 *                   More examples:
	 *
	 *                   CronTrigger Example 1 - an expression to create a trigger that simply fires
	 *                   every 5 minutes:
	 *                   “0 0/5 * * * ?”
	 *
	 *                   CronTrigger Example 3 - an expression to create a trigger that fires at 10:30,
	 *                   11:30, 12:30, and 13:30, on every Wednesday and Friday:
	 *                   “0 30 10-13 ? * WED,FRI”
	 *
	 *                   CronTrigger Example 2 - an expression to create a trigger that fires every 5 minutes,
	 *                   at 10 seconds after the minute (i.e. 10:00:10 am, 10:05:10 am, etc.):
	 *                   “10 0/5 * * * ?”
	 */
	public void scheduleCronTask(Task job, HashMap<String, Object> data,
			String cronExpression,
			String identity) {

		TaskVO taskVo = new TaskVO(cronExpression);
		taskVo.setDataMap(data);
		scheduleTask(job, taskVo, identity);
	}

	/**
	 * Schedule task to be fired according to cron expression
	 * @param job task to schedule
	 * @param data map with values that task will operate with
	 * @param startingDate If not null - defines date for task to be started.
	 *                     If null - will starts right now.
	 * @param identity String to identify this task from others
	 *                 (should be unique)
	 * @param cronExpression String expression that defines when task
	 *                       will be fired. For detailed information see:
	 *
	 */
	public void scheduleCronTaskWithStartingDate(Task job, HashMap<String, Object> data,
			Date startingDate, String cronExpression,
			String identity) {
		if(startingDate == null) {
			startingDate = new Date(); //Starting date by default is now;
		}

		TaskVO taskVo = new TaskVO(startingDate, cronExpression);
		taskVo.setDataMap(data);
		scheduleTask(job, taskVo, identity);
	}

	public void scheduleThreadAtFixedRate(Thread thread, long initialDelay, long period, TimeUnit unit){
		getScheduledExecutorService().scheduleAtFixedRate(thread, initialDelay, period, unit);
	}

	public void scheduleThreadWithFixedDelay(Thread thread, long initialDelay, long delay, TimeUnit timeUnit){
		getScheduledExecutorService().scheduleWithFixedDelay(thread, initialDelay, delay, timeUnit);
	}

	/**
	 * Shutdown schedule
	 */
	public void shutDownScheduler() {
		try {
			if(this.quartzHelper != null){
				this.quartzHelper.shutDownScheduler();
			}
			if(this.scheduledExecutorService != null){
				this.scheduledExecutorService.shutdown();
			}
		} catch (Exception e) {
			log.log(Level.INFO, "Failed to shutdown quartzHelper: ", e);
		}
	}

	/**
	 * Removes task from schedule
	 * @param identity unique identificator of the task
	 */
	public void killScheduledTask(String identity) {
		try {
			if(this.quartzHelper != null){
				this.quartzHelper.killScheduledTask(identity);
			}
		} catch (Exception e) {
			log.log(Level.INFO, "Removing task failed: ", e);
		}
	}

}