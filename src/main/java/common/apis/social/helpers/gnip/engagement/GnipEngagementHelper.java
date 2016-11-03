package common.apis.social.helpers.gnip.engagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import jsmarty.core.common.apis.social.helpers.gnip.engagement.models.request.GnipEngagementRequest;
import jsmarty.core.common.apis.social.helpers.gnip.engagement.models.request.Group;
import jsmarty.core.common.apis.social.helpers.gnip.engagement.models.request.Groupings;
import jsmarty.core.common.apis.social.helpers.gnip.engagement.models.response.GnipResponse;
import jsmarty.core.common.http.HttpConstants;
import jsmarty.core.common.http.HttpRequestHandler;
import jsmarty.core.common.http.HttpResponse;
import jsmarty.core.common.json.JsonHandler;
import jsmarty.core.common.json.impl.JsonHandlerImpl;
import net.arnx.jsonic.JSONException;

/**
 * 
 * Don't use it 
 * TODO: this helper not tested because we have not working credentials for GNIP Engagement API
 * For more details: http://support.gnip.com/apis/engagement_api/api_reference.html
 * @author vit
 *
 */

public class GnipEngagementHelper {

	private HttpRequestHandler httpRequestHandler;
	private JsonHandler jsonHandler;

	/*
	 *  TODO: this is response for test. Remove it after we have working gnip app
	 *  
	 */
	private static final String RESPONSE_TWEET_ID = "123456789";
	private static final String RESPONSE_GROUP_NAME = "group1";
	private static final String GNIP_RESPONSE_SAMPLE = "{" +
			"\"group1\": {" +
			"\"123456789\": {" +
			"\"favorites\": \"109\"," +
			"\"replies\": \"41\"," +
			"\"retweets\": \"88\"" +
			"}," +
			"\"223456789\": {" +
			"\"favorites\": \"0\"," +
			"\"replies\": \"2\"," +
			"\"retweets\": \"0\"" +
			"}" +
			"}" +
			"}";

	public GnipEngagementHelper(){
		this.httpRequestHandler = new HttpRequestHandler();
		this.jsonHandler = new JsonHandlerImpl();
	}

	public GnipResponse getSocialStatistics(Long tweetId, String accessToken, String accessTokenSecret, String consumerKey, String consumerKeySecret){
		GnipResponse gnipResponse = null;

		GnipEngagementRequest request = prepareRequest(Arrays.asList(tweetId));

		Map<String, String> authorization = new HashMap<String, String>();
		/*
		 * 
		 *	!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		 *	WHY IN CLIENT PROPERTIES 
		 * 
		 * 
		 */
		authorization.put(HttpConstants.OAUTH_ACCESS_TOKEN, accessToken);
		authorization.put(HttpConstants.OAUTH_TOKEN_SECRET, accessTokenSecret);
		authorization.put(HttpConstants.OAUTH_CONSUMER_KEY, consumerKey);
		authorization.put(HttpConstants.OAUTH_CONSUMER_KEY, consumerKeySecret);

		HttpResponse httpResponse = null;
		try {
			/**
			 * TODO
			 * ENGAGEMENT API ACCEPTS STRICTLY JSON AS REQUEST @VITALII
			 * SO THIS SHOULD BE HANDLED IN ELASTICHANDLER WHEN WE PUT THIS ENAGAGEMENT HANDLER TO WORK !!
			httpResponse = HttpRequestHandler.executePost(GnipEnagementConstants.URL_ENGAGEMENT_API_TOTALS, authorization,
					jsonHandler.serialize(request));
			 */
		} catch (Exception e) {
			// no operation
		}

		if(httpResponse != null){
			String response = httpResponse.getBody();
			// TODO: remove this
			response = GNIP_RESPONSE_SAMPLE;
			// TODO: change params
			gnipResponse = parseJsonResponse(response, RESPONSE_GROUP_NAME, RESPONSE_TWEET_ID);
		}

		return gnipResponse;
	}

	/**
	 * TODO
	 * ENGAGEMENT API ACCEPTS STRICTLY JSON AS REQUEST @VITALII
	 * SO THIS SHOULD BE HANDLED IN ELASTICHANDLER WHEN WE PUT THIS ENAGAGEMENT HANDLER TO WORK !!
	 */
	private GnipEngagementRequest prepareRequest(final List<Long> tweetIds){
		GnipEngagementRequest gnipEngagementRequest = new GnipEngagementRequest();
		gnipEngagementRequest.setEngagement_types(Arrays.asList(GnipEnagementConstants.ENGAGEMENT_TYPE_FAVORITES, GnipEnagementConstants.ENGAGEMENT_TYPE_REPLIES, GnipEnagementConstants.ENGAGEMENT_TYPE_RETWEETS));

		List<String> tweetIdsString = new ArrayList<>(tweetIds.size());
		for(Long tweetId : tweetIds){
			tweetIdsString.add(String.valueOf(tweetId));
		}

		gnipEngagementRequest.setTweet_ids(tweetIdsString);

		Groupings groupings = new Groupings();
		Group group = new Group();
		group.setGroup_by(Arrays.asList(GnipEnagementConstants.GROUP_BY_TWEET_ID, GnipEnagementConstants.GROUP_BY_ENGAGEMENT_TYPE));
		groupings.setGroup(group);

		gnipEngagementRequest.setGroupings(groupings);

		return gnipEngagementRequest;
	}

	private GnipResponse parseJsonResponse(String jsonResponse, String groupName, String tweetId){
		GnipResponse response = null;
		try{
			JSONObject object = new JSONObject(jsonResponse);
			JSONObject group = object.getJSONObject(groupName);
			JSONObject tweet = group.getJSONObject(tweetId);

			response = new GnipResponse();
			response.setFavorites(parseLong(tweet.getString(GnipEnagementConstants.ENGAGEMENT_TYPE_FAVORITES)));
			response.setReplies(parseLong(tweet.getString(GnipEnagementConstants.ENGAGEMENT_TYPE_REPLIES)));
			response.setRetweets(parseLong(tweet.getString(GnipEnagementConstants.ENGAGEMENT_TYPE_RETWEETS)));

		} catch(JSONException e){
			// no operation
		}

		return response;
	}

	private Long parseLong(String value){
		Long result = null;
		if(value != null && !value.isEmpty()){
			result = Long.parseLong(value);
		}
		return result;
	}

}