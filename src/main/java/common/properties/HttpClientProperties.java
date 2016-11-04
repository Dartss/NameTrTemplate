package common.properties;


import common.properties.core.DefaultProperties;

import java.util.logging.Logger;

public class HttpClientProperties extends DefaultProperties
{

	private final static String PROPERTIES_FILE = "JSmarty.properties";
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static final HttpClientProperties instance = new HttpClientProperties();

	private static String MAXIMUM_CONNECTIONS_COUNT = "http.client.max.total.connections";
	private static String MAXIMUM_PER_ROUTE = "http.client.max.per.route";
	private static String CONTENT_TYPE = "http.client.content.type";
	private static String ENCODING = "http.client.encoding";
	private static String CONTENT_ENCODING = "http.client.content.encoding";

	public HttpClientProperties() {
		super(PROPERTIES_FILE);
	}

	public static HttpClientProperties getInstance() {
		return instance;
	}

	public static boolean loadProperties(boolean refresh) {
		return getInstance().loadProperties(PROPERTIES_FILE, refresh);
	}

	public static int getMAXIMUM_CONNECTIONS_COUNT() {
		return getInstance().getInt(MAXIMUM_CONNECTIONS_COUNT);
	}

	public static int getMAXIMUM_PER_ROUTE() {
		return getInstance().getInt(MAXIMUM_PER_ROUTE);
	}

	public static String getCONTENT_TYPE() {
		return getInstance().getString(CONTENT_TYPE);
	}

	public static String getENCODING() {
		return getInstance().getString(ENCODING);
	}

	public static String getCONTENT_ENCODING() {
		return getInstance().getString(CONTENT_ENCODING);
	}

}