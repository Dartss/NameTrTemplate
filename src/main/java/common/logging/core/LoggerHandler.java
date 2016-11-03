package common.logging.core;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import jsmarty.core.common.logging.log4j.LoggerHelper;
import jsmarty.core.common.mail.MailConfig;
import jsmarty.core.common.mail.MailHandler;
import jsmarty.core.common.properties.LoggerProperties;

/**
 * This is the class handler that communicate with the logger helper
 * This class is the only class exposed to the BaseLogger Class
 * and the only class to deal with the Helper class
 * 
 * This Handler class initialize the Helper by passing the needed parameters to
 * control
 * the log file size, number or indexes of the same file, the physical log file
 * to be updated
 * As well the log level
 * 
 * This class as well send emails on a certain log level as defined in the
 * configuration
 * 
 * @author yev
 */
public class LoggerHandler
{
    private LoggerHelper loggerHelper;

    private String name;
    private String maxFileSize;
    private int maxBackupIndex;

    private List<String> emailMode = Arrays.asList("PRODUCTION");
    private List<String> mailsList;
    private List<String> levelsList;

    private static Map<String, Object> map = new java.util.HashMap<>();

    private static final String ALL = "ALL";
    private static final String FATAL = "FATAL";
    private static final String ERROR = "ERROR";
    private static final String TRACE = "TRACE";
    private static final String DEBUG = "DEBUG";
    private static final String WARN = "WARN";
    private static final String INFO = "INFO";

    /**
     * @param name
     *            - file name
     */
    public LoggerHandler(String name) {
	init(name, null, -1, null);
    }

    /**
     * @param name
     *            - file name (SomeClass.class.getSimpleName())
     * @param maxFileSize
     *            - maximum sizw for each file
     * @param maxBackupIndex
     *            - maximum number of files
     * @param sendEmailLevelsList
     *            - list of email addresses
     */
    public LoggerHandler(String name, String maxFileSize, int maxBackupIndex, List<String> sendEmailLevelsList) {
	new LoggerProperties();

	init(name, maxFileSize, maxBackupIndex, sendEmailLevelsList);
    }

    private void init(String name, String fileSize, int backupIndex, List<String> levelsList)
    {
	new LoggerProperties();
	//
	if (null == name)
	    name = LoggerProperties.getDEFAULT_FILE_NAME();
	if (maxBackupIndex < 0)
	    maxBackupIndex = LoggerProperties.getDEFAULT_MAX_BACKUP_INDEX();
	if (null == fileSize)
	    fileSize = LoggerProperties.getDEFAULT_MAX_FILE_SIZE();
	if (null == levelsList)
	    levelsList = LoggerProperties.getNOTIFICATION_LOG_LEVELS();
	if (backupIndex < 0)
	{
	    backupIndex = Short.MAX_VALUE;
	}
	//
	//
	if (!map.containsKey(name))
	{
	    loggerHelper = new LoggerHelper(LoggerProperties.getFILE_PATH(), name, fileSize, backupIndex, name, LoggerProperties.getDATE_FORMAT(),
		    LoggerProperties.getCONVERSION_PATTERN(), LoggerProperties.getROOT_LEVEL());
	    map.put(name, loggerHelper);
	} else
	{
	    loggerHelper = (LoggerHelper) map.get(name);
	}

	this.mailsList = LoggerProperties.getEMAIL_ADDRESSES();
	this.name = name;
	this.maxFileSize = fileSize;
	this.maxBackupIndex = backupIndex;
	this.levelsList = levelsList;

    }

    /**
     * FATAL method. Create fatal LOG.
     * 
     * @param message
     *            - description about log.
     * @throws IOException
     */
    public void fatal(String message)
    {
	this.fatal(message, null);
    }

    /**
     * 
     * @param message
     * @param throwable
     * @throws IOException
     */
    public void fatal(String message, Throwable throwable)
    {
	loggerHelper.getLogger().fatal(message, throwable);

	this.levelEmail(FATAL, message, throwable);
    }

    /**
     * ERROR method. Create error LOG.
     * 
     * @param message
     *            - description about log.
     * @throws IOException
     */
    public void error(String message)
    {
	this.error(message, null);
    }

    /**
     * 
     * @param message
     * @param throwable
     * @throws IOException
     */
    public void error(String message, Throwable throwable)
    {
	loggerHelper.getLogger().error(message, throwable);

	this.levelEmail(ERROR, message, throwable);
    }

    /**
     * WARN method. Create warn LOG.
     * 
     * @param message
     *            - description about log.
     * @throws IOException
     */
    public void warn(String message)
    {
	this.warn(message, null);
    }

    /**
     * 
     * @param message
     * @param throwable
     * @throws IOException
     */
    public void warn(String message, Throwable throwable)
    {
	loggerHelper.getLogger().warn(message, throwable);

	this.levelEmail(WARN, message, throwable);
    }

    /**
     * INFO method. Create info LOG.
     * 
     * @param message
     *            - description about log.
     * @throws IOException
     */
    public void info(String message)
    {
	this.info(message, null);
    }

    /**
     * 
     * @param message
     * @param throwable
     * @throws IOException
     */
    public void info(String message, Throwable throwable)
    {
	loggerHelper.getLogger().info(message, throwable);

	this.levelEmail(INFO, message, throwable);
    }

    /**
     * DEBUG method. Create debug LOG.
     * 
     * @param message
     *            - description about log.
     * @throws IOException
     */
    public void debug(String message)
    {
	this.debug(message, null);
    }

    /**
     * 
     * @param message
     * @param throwable
     * @throws IOException
     */
    public void debug(String message, Throwable throwable)
    {
	loggerHelper.getLogger().debug(message, throwable);

	this.levelEmail(DEBUG, message, throwable);
    }

    /**
     * TRACE method. Create trace LOG.
     * 
     * @param message
     *            - description about log.
     * @throws IOException
     */
    public void trace(String message)
    {
	this.trace(message, null);
    }

    /**
     * 
     * @param message
     * @param throwable
     * @throws IOException
     */
    public void trace(String message, Throwable throwable)
    {
	loggerHelper.getLogger().trace(message, throwable);

	this.levelEmail(TRACE, message, throwable);
    }

    /**
     * 
     * @param level
     */
    private void levelEmail(String level, String message, Throwable throwable)
    {
	if (null != this.mailsList && this.mailsList.size() > 0 && levelsList.contains(level) || levelsList.contains(ALL))
	{
	    if (emailMode.contains(LoggerProperties.getENVIRONMENT_MODE()))
	    {
		if (null != throwable)
		    message += "<br><br>" + throwable.getMessage();
		sendEmail(level, message);
	    }
	}
    }

    /**
     * Method will send email with some log.
     * Use MailSender.java
     * 
     * @param title
     * @param text
     * @param email
     */
    private void sendEmail(String title, String text)
    {
	MailConfig mailConfig = new MailConfig();
	MailHandler mailHandler = new MailHandler(mailConfig);
	mailHandler.send(LoggerProperties.getSEND_EMAIL_FROM(), this.mailsList, title, text);
    }

    //
    //
    //
    public String getName()
    {
	return name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    public String getMaxFileSize()
    {
	return maxFileSize;
    }

    public void setMaxFileSize(String maxFileSize)
    {
	this.maxFileSize = maxFileSize;
    }

    public int getMaxBackupIndex()
    {
	return maxBackupIndex;
    }

    public void setMaxBackupIndex(int maxBackupIndex)
    {
	this.maxBackupIndex = maxBackupIndex;
    }
}
