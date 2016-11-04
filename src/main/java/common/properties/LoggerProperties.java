package common.properties;

import common.properties.core.DefaultProperties;

import java.util.List;

public class LoggerProperties extends DefaultProperties
{
    private final static String PROPERTIES_FILE = "logger.properties";
    //
    private static String ENV_MODE = "sm.logger.environment.mode";
    //
    private static String ROOT_LEVEL = "sm.logger.rootLevel";
    private static String DEFAULT_MAX_FILE_SIZE = "sm.logger.default.maxFileSize";
    private static String DEFAULT_MAX_BACKUP_INDEX = "sm.logger.default.maxBackupIndex";
    private static String DEFAULT_FILE_NAME = "sm.logger.default.fileName";

    private static String FILE_PATH = "sm.logger.dir.location";
    private static String CONVERSION_PATTERN = "sm.logger.conversion.pattern";
    private static String DATE_FORMAT = "sm.logger.fileName.dateFormat";

    private static String NOTIFICATION_LOG_LEVEL = "sm.logger.notification.level";
    private static String EMAIL_RECEIVERS = "sm.logger.notification.email.addresses";
    private static String EMAIL_FROM = "sm.logger.notification.email.from";
    private static String EMAIL_SUBJECT = "sm.logger.notification.email.subject";

    private static final LoggerProperties instance = new LoggerProperties();

    public LoggerProperties() {
	super(PROPERTIES_FILE);
    }

    public static LoggerProperties getInstance()
    {
	return instance;
    }

    public boolean loadProperties(boolean refresh)
    {
	return getInstance().loadProperties(PROPERTIES_FILE, refresh);
    }

    public static List<String> getNOTIFICATION_LOG_LEVELS()
    {
	return getInstance().getStringListDelim(PROPERTIES_FILE, NOTIFICATION_LOG_LEVEL);
    }

    public static String getDEFAULT_MAX_FILE_SIZE()
    {
	return getInstance().getString(PROPERTIES_FILE, DEFAULT_MAX_FILE_SIZE);
    }

    public static int getDEFAULT_MAX_BACKUP_INDEX()
    {
	return getInstance().getInt(PROPERTIES_FILE, DEFAULT_MAX_BACKUP_INDEX, 0);
    }

    public static String getDEFAULT_FILE_NAME()
    {
	return getInstance().getString(PROPERTIES_FILE, DEFAULT_FILE_NAME);
    }

    public static String getCONVERSION_PATTERN()
    {
	return getInstance().getString(PROPERTIES_FILE, CONVERSION_PATTERN);
    }

    public static String getFILE_PATH()
    {
	return getInstance().getString(PROPERTIES_FILE, FILE_PATH);
    }

    public static String getDATE_FORMAT()
    {
	return getInstance().getString(PROPERTIES_FILE, DATE_FORMAT);
    }

    public static List<String> getEMAIL_ADDRESSES()
    {
	return getInstance().getStringListDelim(PROPERTIES_FILE, EMAIL_RECEIVERS);
    }

    public static String getSEND_EMAIL_FROM()
    {
	return getInstance().getString(PROPERTIES_FILE, EMAIL_FROM);
    }

    public static String getEMAIL_SUBJECT()
    {
	return getInstance().getString(PROPERTIES_FILE, EMAIL_SUBJECT);
    }

    public static String getROOT_LEVEL()
    {
	return getInstance().getString(PROPERTIES_FILE, ROOT_LEVEL);
    }

    public static String getENVIRONMENT_MODE()
    {
	return getInstance().getString(PROPERTIES_FILE, ENV_MODE);
    }
}
