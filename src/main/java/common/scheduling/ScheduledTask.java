package common.scheduling;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import jsmarty.core.common.logging.DefaultLogger;
import jsmarty.core.common.logging.core.Logger;

/**
 * Scheduled Task
 * 
 * @author patrick
 *
 */
public class ScheduledTask extends TimerTask
{

    private Timer timer;
    private Date date;
    private Object object;
    private String methodsStr;

    private final static Logger logger = DefaultLogger.getInstance();

    /**
     * Runs the specified methods from the specified object,
     * Starting from the specified date, and repeat when specified period is
     * reached
     * multiple methods can be called if separated with '|' character
     * 
     * @param obj
     * @param methodToRun
     * @param date
     * @param period
     */
    public ScheduledTask(Object obj, String methodToRun, Date date, long period) {
	this.object = obj;
	this.date = date;
	this.methodsStr = methodToRun;
	//
	logger.debug("SCHEDULED Initialization(" + obj.getClass().getName() + "." + methodToRun + "): for " + date + " with a period of " + period
		+ " ms");
	//
	timer = new Timer();
	timer.scheduleAtFixedRate(this, date, period);
	//
	logger.debug("SCHEDULED TASK (" + obj.getClass().getName() + "." + methodToRun + "): for " + date + " with a period of " + period + " ms");
    }

    @Override
    public void run()
    {
	String[] methodToRunArray = this.methodsStr.split("\\|");
	Method method = null;

	for (String substr : methodToRunArray)
	{
	    try
	    {
		method = object.getClass().getMethod(substr, null);
		//
		logger.debug("--SCHEDULED TASK INVOKING METHOD (" + object.getClass().getName() + "." + method.getName() + ")");
		//
		method.invoke(object, null);
		method.invoke(object, null);
		//
		logger.debug("--SCHEDULED TASK METHOD INVOKED (" + object.getClass().getName() + "." + method.getName() + ")");
		//
	    } catch (NoSuchMethodException e)
	    {
		logger.error("Error:NoSuchMethodException running scheduled task", e);
	    } catch (SecurityException e)
	    {
		logger.error("Error:SecurityException running scheduled task", e);
	    } catch (IllegalAccessException e)
	    {
		logger.error("Error:IllegalAccessException running scheduled task", e);
	    } catch (IllegalArgumentException e)
	    {
		logger.error("Error:IllegalArgumentException running scheduled task", e);
	    } catch (InvocationTargetException e)
	    {
		logger.error("Error:InvocationTargetException running scheduled task", e);
	    }
	}
    }

}