package common.apis.social.helpers.reddit;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import jsmarty.core.common.http.HttpRequestHandler;
import jsmarty.core.common.http.HttpResponse;
import jsmarty.core.sdo.stats.ParseStatus;
import jsmarty.core.sdo.stats.RedditStats;
import net.dean.jraw.RedditClient;
import net.dean.jraw.http.HttpRequest;
import net.dean.jraw.http.NetworkException;
import net.dean.jraw.http.RestResponse;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.http.oauth.Credentials;
import net.dean.jraw.http.oauth.OAuthData;
import net.dean.jraw.http.oauth.OAuthException;

public class RedditHelper {

	private RedditClient redditClient;
	private final String REQUEST_URL_BASE = "https://www.reddit.com/api/info.json?url=";
	private final String NOAUTH_REQUEST_URL_BASE = "https://www.reddit.com/api/info.json";

	private HttpRequestHandler httpHandler;

	private Logger logger = Logger.getLogger(this.getClass().getName());

	/*
	 * Constructor called when attempting to fetch stats without client..
	 */
	public RedditHelper(){
		this.httpHandler = new HttpRequestHandler();
	}

	/*
	 * Constructor called when attempting to fetch stats with client..
	 */
	public RedditHelper(Map<String, String> properties) throws OAuthException {
		this.redditClient = createClient(properties);
		authenticate(properties);
	}

	private RedditClient createClient(Map<String, String> properties) {
		UserAgent userAgent = UserAgent.of(properties.get("REDDIT_API_APP_PLATFORM"),
				properties.get("REDDIT_API_APP_ID"),
				properties.get("REDDIT_API_APP_VERSION"),
				properties.get("REDDIT_API_USERNAME"));

		return new RedditClient(userAgent);
	}

	public void authenticate(Map<String, String> properties) throws NetworkException, OAuthException{
		if(this.redditClient == null) {
			this.redditClient = createClient(properties);
		}
		if (!this.redditClient.isAuthenticated()) {
			Credentials credentials = Credentials.script(properties.get("REDDIT_API_USERNAME"),
					properties.get("REDDIT_API_PASSWORD"),
					properties.get("REDDIT_API_CLIENT_ID"),
					properties.get("REDDIT_API_CLIENT_SECRET"));

			OAuthData authData = this.redditClient.getOAuthHelper().easyAuth(credentials);
			this.redditClient.authenticate(authData);
		}
	}

	public boolean isAuthenticated(){
		if(this.redditClient != null){
			if(this.redditClient.isAuthenticated()){
				return true;	
			}
		}
		return false;
	}

	/*
	 * calls reddit api with/without client - depending on how the helper was created, if it was created using authenticate, then the test of "isAuthenticated" below will succeed
	 * 
	 */
	public RedditStats fetchDataByUrl(String url) {
		RedditStats redditStats = new RedditStats();
		if(isAuthenticated()){
			URL requestUrl = null;
			try {
				requestUrl = new URL(REQUEST_URL_BASE + url);
			} catch (MalformedURLException e) {
				logger.info("URL exception");
			}
			HttpRequest httpRequest = HttpRequest.from("GET", requestUrl);

			//executing request
			RestResponse response;
			response = this.redditClient.execute(httpRequest);
			if (null == response.getJson() | null == response.getJson().get("data") | null == response.getJson().get("data").get("children")) {
				logger.info("Json in response from Reddit is some how NULL");
				ParseStatus status = new ParseStatus();
				status.setApi_name("RedditStats");
				status.setSuccess(false);
				status.setError_message("Got null response from Reddit API.");
				redditStats.setParse_status(status);
			}else{
				redditStats = extractStats(response.getRaw());
			}

		}else{
			/*
			 * getting stats without auth client
			 */
			Map<String, String> urlParams = new HashMap<>();
			urlParams.put("url", url);
			Map<String, String> headerParams = new HashMap<>();
			headerParams.put("User-Agent", "Java");

			HttpResponse response;
			try {
				response = this.httpHandler.executeGet(NOAUTH_REQUEST_URL_BASE, headerParams, urlParams,null);
				redditStats = extractStats(response.getBody());
			} catch (Exception e1) {
				e1.printStackTrace();
				ParseStatus status = new ParseStatus();
				status.setApi_name("RedditStats");
				status.setSuccess(false);
				status.setError_message("Error while calling reddit.");
				redditStats.setParse_status(status);
			}

		}
		return redditStats;
	}

	private RedditStats extractStats(String response) {
		RedditStats redditStats = new RedditStats();

		JSONObject jsonResponse = new JSONObject(response);
		JSONObject data = (JSONObject) jsonResponse.get("data");
		JSONArray childrenArray = data.getJSONArray("children");
		JSONObject childrenEntry;

		for (int i=0; i<childrenArray.length(); i++){
			childrenEntry = (JSONObject) childrenArray.getJSONObject(i).getJSONObject("data");

			//					Integer likes = childrenEntry.getInt("likes");
			Integer comments = childrenEntry.getInt("num_comments");
			Integer score = childrenEntry.getInt("score");
			Integer ups = childrenEntry.getInt("ups");
			Integer downs = childrenEntry.getInt("downs");

			if(comments != null){
				redditStats.setCommentsCount(redditStats.getCommentsCount()+comments);
			}
			if(score != null){
				redditStats.setScore(redditStats.getScore()+score);
			}
			if(ups != null){
				redditStats.setUpsCount(redditStats.getUpsCount()+ups);
			}
			if(downs != null){
				redditStats.setDownsCount(redditStats.getDownsCount()+downs);
			}
		}
		return redditStats;

	}

}