package common.scheduling.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Custom interface to use it instead of Quartz class Job
 *
 * @author Dima
 */
public interface Task extends Job {
    void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException;
}
