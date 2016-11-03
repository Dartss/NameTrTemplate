package common.logging.log4j;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

import jsmarty.core.common.utils.DateUtils;

/**
 * LoggerHelper is the main and only class to handle and manage the calls to the
 * Log4J library
 * And it's only accessible by the LoggerHandler class
 * 
 * The LoggerHelper class initialize and append tracing logs to physical log
 * files by calling the
 * Log4j library.
 * 
 * @author yev
 **/
public class LoggerHelper
{

    private boolean initializationFlag = false;
    private String maxFileSize;
    private int maxBackupIndex;
    private Logger log;
    private String fileName;
    private String folderPath;
    private String dateFormat;
    private String converisonPattern;
    private String rootLevel;

    public LoggerHelper(String folderPath, String fileName, String fileSize, int backupIndex, String className, String dateFormat, String converisonPattern, String rootLevel) {

	this.fileName = fileName;
	this.maxFileSize = fileSize;
	this.maxBackupIndex = backupIndex;
	this.folderPath = folderPath;
	this.dateFormat=dateFormat;
	this.converisonPattern=converisonPattern;
	this.rootLevel=rootLevel;

	log = Logger.getLogger(className);
    }

    /**
     * Initialize method. Set max file size, file name, file format.
     * RollingFileAppender it's template for file;
     * 
     * @param fileName
     *            - name of file;
     * @param log
     *            - Logger; created by each component separately;
     * @param fileSize
     *            - max file size for created files;
     * @param backupIndex
     *            - max number of created files for each component;
     * @throws IOException
     */
    private void initializeLogger()
    {
	try
	{
	    String currentDate = DateUtils.currentDate(this.dateFormat);

	    PatternLayout layout = new PatternLayout();
	    layout.setConversionPattern(this.converisonPattern);

	    RollingFileAppender rollingAppender = new RollingFileAppender(layout,
		    folderPath + File.separator + fileName + "_" + currentDate + ".log");

	    if (null != this.maxFileSize)
		rollingAppender.setMaxFileSize(this.maxFileSize);
	    if (this.maxBackupIndex >= 0)
		rollingAppender.setMaxBackupIndex(this.maxBackupIndex);
	    rollingAppender.activateOptions();
	    log.addAppender(rollingAppender);

	    //To enable console print out of logging
	    ConsoleAppender console = new ConsoleAppender(layout, "System.out");
	    log.addAppender(console);

	    log.setLevel(Level.toLevel(this.rootLevel));
	    //
	    System.out.println("LoggerHelper Initialized=[" + fileName + "]");
	} catch (IOException ex)
	{
	    ex.printStackTrace();
	}
    }

    /**
     * This method called from each component. And check if this component
     * already use some logger or not.
     * If no we will initialize new template for logger.
     * 
     * @param fileName
     * @param log
     * @return
     * @throws IOException
     */
    public Logger getLogger()
    {
	if (initializationFlag == false)
	{
	    System.out.println("LoggerHelper::Initializing logger");
	    //
	    initializeLogger();
	    initializationFlag = true;

	    return this.log;
	} else
	{
	    return this.log;
	}
    }
}
