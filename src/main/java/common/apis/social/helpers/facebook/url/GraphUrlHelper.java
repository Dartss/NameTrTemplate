package common.apis.social.helpers.facebook.url;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import jsmarty.core.common.http.HttpRequestHandler;
import jsmarty.core.common.http.HttpResponse;
import jsmarty.core.common.json.JsonHandler;
import jsmarty.core.common.json.impl.JsonHandlerImpl;
import jsmarty.core.common.properties.StatsParserProperties;

public class GraphUrlHelper {


	private HttpRequestHandler httpRequestHandler;
	private JsonHandler jsonHandler;

	private StatsParserProperties statsProperties = new StatsParserProperties();
	private String URL = StatsParserProperties.getAPI_GRAPHURL_URL();
	private String PARAM_ID_NAME = StatsParserProperties.getAPI_GRAPHURL_PARAM_ID_NAME();

	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public GraphUrlHelper(){
		this.httpRequestHandler = new HttpRequestHandler();
		this.jsonHandler = new JsonHandlerImpl();
	}

	public GraphUrlResponse getStats(String url){
		GraphUrlResponse graphUrlResponse = null;

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(PARAM_ID_NAME, url);

		try {
			HttpResponse httpResponse = this.httpRequestHandler.executeGet(URL, null, parameters, null);
			graphUrlResponse = (GraphUrlResponse) jsonHandler.deserialize(httpResponse.getBody(), new GraphUrlResponse());
		} catch (Exception ex) {
			LOGGER.info("Error calling Graph API - fetching url: "+URL);
			ex.printStackTrace();
		}		

		return graphUrlResponse;
	}

}