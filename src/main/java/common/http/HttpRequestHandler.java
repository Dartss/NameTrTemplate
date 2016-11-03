package common.http;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Http client handler
 * based on Apache Http client 
 * 
 * @author yev
 *
 */
public class HttpRequestHandler{

	private static HttpRequestHelper httpRequestHelper;
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public HttpRequestHandler() {
		initHttpHelper();
	}

	private static synchronized void initHttpHelper(){
		if(httpRequestHelper == null){
			HttpConfig httpConfig = new HttpConfig();
			httpRequestHelper = new HttpRequestHelper(httpConfig);
		} 	
		attachShutdownHook();  
	}

	private static synchronized void attachShutdownHook(){
		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				try {
					LOGGER.info("We entered the shutdownhook inside the HTTPRequestHandler");
					shutdown();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public HttpResponse executeGet(String url, Map<String, String> headerParameters, Map<String, String> urlParameters, Map<String, String> authParameters) throws Exception {
		return httpRequestHelper.get(url, headerParameters, urlParameters, authParameters);
	}

	public HttpResponse executeGet(String url) throws Exception {
		return httpRequestHelper.get(url, null, null, null);
	}

	public HttpResponse executePost(String url, Map<String, String> headerParameters, Map<String, String> urlParameters, Map<String, String> authParameters) throws Exception {
		return httpRequestHelper.post(url, headerParameters, urlParameters, authParameters);
	}

	public HttpResponse executePost(String url) throws Exception{
		return httpRequestHelper.post(url, null, null, null);
	}

	public HttpResponse getInputStream(String url, Map<String, String> headerParameters, Map<String, String> urlParameters, Map<String, String> authParameters) throws Exception {
		return httpRequestHelper.getInputStream(url, headerParameters, urlParameters, authParameters);
	}

	public HttpResponse getInputStream(String url) throws Exception {
		return httpRequestHelper.getInputStream(url, null, null, null);
	}

	public static synchronized void shutdown() throws IOException {
		httpRequestHelper.shutdown();
	}

}