package common.properties.core;

public class DefaultProperties extends BaseProperties
{
    private final static String PROPERTIES_FILE = "JSmarty.properties";
    private static final DefaultProperties instance = new DefaultProperties();

    /* DEFAULT GRAPH DB */
    private static String DEFAULT_GRAPH_HOST = "sm.default.db.graph.host";
    private static String DEFAULT_GRAPH_PORT = "sm.default.db.graph.port";
    private static String DEFAULT_GRAPH_USERNAME = "sm.default.db.graph.username";
    private static String DEFAULT_GRAPH_PASSWORD = "sm.default.db.graph.password";

    /* DEFAULT DB */
    private static String DEFAULT_DB_DRIVER = "sm.default.db.jdbc.driver";
    private static String DEFAULT_DB_URL = "sm.default.db.jdbc.url";
    private static String DEFAULT_DB_USERNAME = "sm.default.db.jdbc.username";
    private static String DEFAULT_DB_PASSWORD = "sm.default.db.jdbc.password";
    private static String DEFAULT_DB_TRXTYPE = "sm.default.db.jdbc.trxType";
    private static String DEFAULT_DB_SHOWSQL = "sm.default.db.showSql";

    /* SOCKETS */
    private static String DEFAULT_SOCKET_TIMEOUT = "sockets.timeout.time";

    public DefaultProperties() {
	super(PROPERTIES_FILE);
    }

    public DefaultProperties(String propertyFileName) {
	super(propertyFileName);
	super.loadProperties(PROPERTIES_FILE);
    }

    public static DefaultProperties getInstance()
    {
	return instance;
    }

    /* DEFAULT GRAPH DB */
    public static String getDEFAULT_GRAPH_HOST()
    {
	return getInstance().getString(DEFAULT_GRAPH_HOST);
    }

    public static int getDEFAULT_GRAPH_PORT()
    {
	return getInstance().getInt(DEFAULT_GRAPH_PORT, 0);
    }

    public static String getDEFAULT_GRAPH_USERNAME()
    {
	return getInstance().getString(DEFAULT_GRAPH_USERNAME);
    }

    public static String getDEFAULT_GRAPH_PASSWORD()
    {
	return getInstance().getString(DEFAULT_GRAPH_PASSWORD);
    }

    /* SOCKETS */
    public static int getDEFAULT_SOCKET_TIMEOUT()
    {
	return getInstance().getInt(PROPERTIES_FILE, DEFAULT_SOCKET_TIMEOUT, 0);
    }

    /* DEFAULT DB */
    public static String getDEFAULT_DB_DRIVER()
    {
	return getInstance().getString(PROPERTIES_FILE, DEFAULT_DB_DRIVER, null);
    }

    public static String getDEFAULT_DB_URL()
    {
	return getInstance().getString(PROPERTIES_FILE, DEFAULT_DB_URL, null);
    }

    public static String getDEFAULT_DB_USERNAME()
    {
	return getInstance().getString(PROPERTIES_FILE, DEFAULT_DB_USERNAME, null);
    }

    public static String getDEFAULT_DB_PASSWORD()
    {
	return getInstance().getString(PROPERTIES_FILE, DEFAULT_DB_PASSWORD, null);
    }

    public static String getDEFAULT_DB_TRXTYPE()
    {
	return getInstance().getString(PROPERTIES_FILE, DEFAULT_DB_TRXTYPE, null);
    }

    public static boolean getDEFAULT_DB_SHOWSQL()
    {
	return getInstance().getBoolean(PROPERTIES_FILE, DEFAULT_DB_SHOWSQL, false);
    }

}