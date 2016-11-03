package common.logging.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Base Logger is the main and only class to be accessible by components loggers 
 * The base logger will make sure to initiate and return the same logger instance 
 * for the same logger file 
 * 
 * @author fares
 *
 */
public class BaseLogger implements Logger
{
    private LoggerHandler loggerHandler;
    private static Map<String, LoggerHandler> mp =  new HashMap();
    //
    public BaseLogger(String name)
    {
	if(mp.containsKey(name)) this.loggerHandler = mp.get(name);
	else this.loggerHandler = new LoggerHandler(name);
    }
    //
    //
    public void fatal(String message)
    {
	this.loggerHandler.fatal(message);
    }
    public void fatal(String message, Throwable throwable)
    {
	this.loggerHandler.fatal(message, throwable);
    }
    
    public void error(String message)
    {
	this.loggerHandler.error(message);
    }
    public void error(String message, Throwable throwable)
    {
	this.loggerHandler.error(message, throwable);
    }

    public void warn(String message)
    {
	this.loggerHandler.warn(message);
    }
    public void warn(String message, Throwable throwable)
    {
	this.loggerHandler.warn(message, throwable);
    }

    public void info(String message)
    {
	this.loggerHandler.info(message);
    }
    public void info(String message, Throwable throwable)
    {
	this.loggerHandler.info(message, throwable);
    }

    public void debug(String message)
    {
	this.loggerHandler.debug(message);
    }
    public void debug(String message, Throwable throwable)
    {
	this.loggerHandler.debug(message, throwable);
    }

    public void trace(String message)
    {
	this.loggerHandler.trace(message);
    }

    public void trace(String message, Throwable throwable)
    {
	this.loggerHandler.trace(message, throwable);
    }

}
