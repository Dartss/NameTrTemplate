package common.http;

import common.properties.HttpClientProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class HttpConfig {
	
	private int maximumConnectionsCount;
	private int maximumPerRoute;
	private String contentType;
	private String encoding;
	private String contentEncoding;
	
	public static Map<Integer, String> errors;
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	static{
		errors = new HashMap<>();
		errors.put(404, "ERROR 404. Page not Found.");
	}
	
	public HttpConfig(){
		LOGGER.info("Loading http configuration from properties file");
		new HttpClientProperties();
		this.maximumConnectionsCount = HttpClientProperties.getMAXIMUM_CONNECTIONS_COUNT();
		this.maximumPerRoute = HttpClientProperties.getMAXIMUM_PER_ROUTE();
		this.contentType = HttpClientProperties.getCONTENT_TYPE();
		this.encoding = HttpClientProperties.getENCODING();
		this.contentEncoding = HttpClientProperties.getCONTENT_ENCODING();
	}
	
	public HttpConfig(int maxConnections, int maxPerRoute) {
		LOGGER.info("Getting http configuration by constructor parameters");
		this.maximumConnectionsCount = maxConnections;
		this.maximumPerRoute = maxPerRoute;
	}

	public int getMaximumConnectionsCount() {
		return maximumConnectionsCount;
	}

	public void setMaximumConnectionsCount(int maximumConnectionsCount) {
		this.maximumConnectionsCount = maximumConnectionsCount;
	}

	public int getMaximumPerRoute() {
		return maximumPerRoute;
	}

	public void setMaximumPerRoute(int maximumPerRoute) {
		this.maximumPerRoute = maximumPerRoute;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getContentEncoding() {
		return contentEncoding;
	}

	public void setContentEncoding(String contentEncoding) {
		this.contentEncoding = contentEncoding;
	}
	
}