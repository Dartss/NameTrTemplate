package common.apis.social.handlers.impl;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import jsmarty.core.common.apis.social.handlers.StatsApiHandler;
import jsmarty.core.common.apis.social.helpers.reddit.RedditHelper;
import jsmarty.core.sdo.Stats;
import jsmarty.core.sdo.stats.RedditStats;
import jsmarty.core.socialstats.model.StatsVO;
import net.dean.jraw.http.NetworkException;
import net.dean.jraw.http.oauth.OAuthException;

/**
 * Handler to fetch stats for url link from Reddit social network
 *
 * This handler should work in 2 ways, either by calling reddit with auth/credentials, either without.
 * 
 * Credentials/configuration should be passed in constructor or can be passed any time later via "authenticate" method (in case the empty constructor were used to create instance),
 * 
 * @author Dima
 */

public class RedditUrlHandlerImpl implements Serializable, StatsApiHandler {

	private RedditHelper redditHelper;
	private Map<String, String > properties;

	/**
	 * Creates Handler that use Reddit API wrapper, and needs authentication data
	 * 
	 * @param username reddit account username
	 * @param password reddit account pass
	 * @param secret reddit applications secret (example: "Ze-rgCc21aLIxyUfRVe5qIY9BZQ")
	 * @param clientId reddit applications id (example:"NAxNQ_g8eHcbdg")
	 * @param appId reddit applications groupId (example:"com.smarty.research")
	 * @param appVersion reddit applications version (example: "0.1")
	 * @param appPlatform reddit applications platform(example: "desktop")
	 * @throws OAuthException
	 */
	public RedditUrlHandlerImpl(String username, String password, String secret, String clientId, String appId, String appVersion, String appPlatform) throws OAuthException {
		fillProperties(username, password, secret, clientId, appId, appVersion, appPlatform);
		this.redditHelper = new RedditHelper(properties);
	}

	private void fillProperties(String username, String password, String secret, String clientId, String appId, String appVersion, String appPlatform) {
		properties = new HashMap<>();
		properties.put("REDDIT_API_USERNAME", username);
		properties.put("REDDIT_API_PASSWORD", password);
		properties.put("REDDIT_API_CLIENT_SECRET", secret);
		properties.put("REDDIT_API_CLIENT_ID", clientId);
		properties.put("REDDIT_API_APP_ID", appId);
		properties.put("REDDIT_API_APP_VERSION", appVersion);
		properties.put("REDDIT_API_APP_PLATFORM", appPlatform);		
	}

	/**
	 * Creates Handler that use direct requests to Reddit API and doesn't need authentication
	 */
	public RedditUrlHandlerImpl() {
		this.redditHelper = new RedditHelper();
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @param secret
	 * @param clientId
	 * @param appId
	 * @param appVersion
	 * @param appPlatform
	 * @throws NetworkException
	 * @throws OAuthException
	 */
	public void authenticate(String username, String password, String secret,String clientId, String appId, String appVersion,String appPlatform) throws NetworkException, OAuthException{
		if (!this.redditHelper.isAuthenticated()){
			fillProperties(username, password, secret, clientId, appId, appVersion, appPlatform);
			this.redditHelper.authenticate(properties);
		}
	}

	/**
	 *
	 *	fetch data of a url in case it exists on Reddit
	 *
	 * @param url to search for on Reddit
	 * @return
	 */
	private RedditStats fetchDataByUrl(String url) {
		return this.redditHelper.fetchDataByUrl(url);
	}

	@Override
	public StatsVO getStats(StatsVO statsVO) {
		/*
		 * check if statsVO contains key/credentials of reddit, if yes, extract them and authenticate;
		 */
		if(!statsVO.getApiKeyVO().getApiKey().isEmpty()){
			String[] apiCredentials = statsVO.getApiKeyVO().getApiKey().split(",");
			fillProperties(apiCredentials[0], apiCredentials[1], apiCredentials[2], apiCredentials[3], apiCredentials[4], apiCredentials[5], apiCredentials[6]);
		}

		RedditStats redditStats = fetchDataByUrl(statsVO.getJob().getOrigin_url());
		redditStats.setTimestamp(System.currentTimeMillis());
		Stats stats = new Stats();
		stats.setUrl_reddit(redditStats);
		statsVO.setStats(stats);
		statsVO.setParseStatus(redditStats.getParse_status());
		return statsVO;
	}

}