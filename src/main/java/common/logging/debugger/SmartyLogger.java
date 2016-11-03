package common.logging.debugger;

import java.sql.Timestamp;
import java.util.Date;

import jsmarty.core.common.logging.debugger.model.SmartyLog;
import jsmarty.core.common.queuer.QueuerPoolHandler;
import jsmarty.core.common.queuer.QueuerPoolHandlerImpl;

/**
 * Unified Logger for all JSmarty
 * Check README.md for more info
 * 
 * @author rud
 *
 */
public class SmartyLogger {

	private Date date;
	private SmartyLog smartyLog;

	private QueuerPoolHandler queuerPoolHandler;
	private String logsQueueHost=SmartyLoggerProperties.getLOGS_QUEUE_SERVER_HOST();
	private int logsQueuePort=SmartyLoggerProperties.getLOGS_QUEUE_SERVER_PORT();
	private String logsQueueName=SmartyLoggerProperties.getLOGS_QUEUE_NAME();
	private int printFrequencyTime=SmartyLoggerProperties.getPRINT_FREQUENCY_TIME();

	public SmartyLogger() {
		super();
		queuerPoolHandler = new QueuerPoolHandlerImpl(logsQueueHost, logsQueuePort);
	}

	private SmartyLog log(int level, String component, String message){
		date=new Date();
		smartyLog=new SmartyLog(logsQueueName, queuerPoolHandler, new Timestamp(date.getTime()), component, message, level, printFrequencyTime);
		return smartyLog;
	}
	public SmartyLog FATAL(String component, String message){
		return log(Level.FATAL, component, message);
	}
	public SmartyLog ERROR(String component, String message){
		return log(Level.ERROR, component, message);
	}
	public SmartyLog WARN(String component, String message){
		return log(Level.WARN, component, message);
	}
	public SmartyLog INFO(String component, String message){
		return log(Level.INFO, component, message);
	}
	public SmartyLog DEBUG(String component, String message){
		return log(Level.DEBUG, component, message);
	}
	public SmartyLog TRACE(String component, String message){
		return log(Level.TRACE, component, message);
	}

	private void pushLog(String log){
		queuerPoolHandler.lpush(logsQueueName, log);
	}

}