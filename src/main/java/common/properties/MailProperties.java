package common.properties;

import java.util.logging.Logger;

import jsmarty.core.common.properties.core.DefaultProperties;

public class MailProperties extends DefaultProperties
{
    //
    private final static String PROPERTIES_FILE = "JSmarty.properties";
    private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static final MailProperties instance = new MailProperties();
    //
    private static final String HOST = "sm.mail.sender.host";
    private static final String PORT = "sm.mail.sender.port";
    private static final String SEND_FROM = "sm.mail.sender.from";
    private static final String USER = "sm.mail.sender.user";
    private static final String PASSWORD = "sm.mail.sender.password";
    private static final String USE_SSL = "sm.mail.sender.useSSL";

    public MailProperties() {
	super(PROPERTIES_FILE);
    }

    public static MailProperties getInstance()
    {
	return instance;
    }

    public static boolean loadProperties(boolean refresh)
    {
	return getInstance().loadProperties(PROPERTIES_FILE, refresh);
    }

    public static String getHOST()
    {
	return getInstance().getString(HOST);
    }

    public static int getPORT()
    {
	return getInstance().getInt(PORT, 110);
    }

    public static String getSEND_FROM()
    {
	return getInstance().getString(SEND_FROM);
    }

    public static String getUSER()
    {
	return getInstance().getString(USER);
    }

    public static String getPASSWORD()
    {
	return getInstance().getString(PASSWORD);
    }

    public static Boolean getUSE_SSL()
    {
	return getInstance().getBoolean(USE_SSL, false);
    }

}
