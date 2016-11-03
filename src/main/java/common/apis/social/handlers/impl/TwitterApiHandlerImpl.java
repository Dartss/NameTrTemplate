package common.apis.social.handlers.impl;

import java.util.logging.Logger;

import jsmarty.core.common.apis.social.handlers.StatsApiHandler;
import jsmarty.core.common.apis.social.helpers.twitter.TwitterApiHelper;
import jsmarty.core.common.apis.social.helpers.twitter.TwitterApiKeyset;
import jsmarty.core.sdo.Stats;
import jsmarty.core.sdo.stats.ParseStatus;
import jsmarty.core.sdo.stats.StatsParserErrorCodes;
import jsmarty.core.sdo.stats.TweetStats;
import jsmarty.core.socialstats.model.StatsVO;
import twitter4j.Status;
import twitter4j.TwitterException;

/**
 * 
 * @author vit
 *
 */
public class TwitterApiHandlerImpl implements StatsApiHandler{

	public TwitterApiHelper twitterApiHelper;
	private transient static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public TwitterApiHandlerImpl() {
		this.twitterApiHelper = new TwitterApiHelper();
	}

	@Override
	public StatsVO getStats(StatsVO statsVO) {
		String url = statsVO.getJob().getOrigin_url();

		Long id = parseId(url);

		if(id == null){
			ParseStatus parseStatus = new ParseStatus(statsVO.getApiKeyVO().getApiName());
			parseStatus.setSuccess(false);
			parseStatus.setError_code(StatsParserErrorCodes.INVALID_ID);
			parseStatus.setError_message("Invalid tweet id");
			statsVO.setParseStatus(parseStatus);
		} else {
			LOGGER.info("?????????????? twitter keyset : " + statsVO.getApiKeyVO().getApiKey());
			TwitterApiKeyset keyset = getTwitterApiKeyset(statsVO.getApiKeyVO().getApiKey());

			if(keyset == null){
				ParseStatus parseStatus = new ParseStatus(statsVO.getApiKeyVO().getApiName());
				parseStatus.setSuccess(false);
				parseStatus.setError_code(StatsParserErrorCodes.INVALID_APP);
				parseStatus.setError_message("Invalid api key");
				statsVO.setParseStatus(parseStatus);
			} else{
				twitterApiHelper.setApiKeys(keyset);
				Status status = null;
				try {
					status = twitterApiHelper.getFavorites(id);
				} catch (TwitterException tex) {
					ParseStatus parseStatus = handleErrors(tex);
					parseStatus.setApi_name(statsVO.getApiKeyVO().getApiName());
					statsVO.setParseStatus(parseStatus);
				}

				if(status != null){
					TweetStats tweetStats = new TweetStats();;
					tweetStats.setFavorites(Long.valueOf(status.getFavoriteCount()));
					tweetStats.setRetweets(Long.valueOf(status.getRetweetCount()));
					// TODO: get the count of replies  - current API doesn't allow it
					tweetStats.setTimestamp(System.currentTimeMillis());

					Stats stats = new Stats();
					stats.setTweet(tweetStats);
					statsVO.setStats(stats);

					/*
					 * ??!
					 * currently all the balance are handled from manager side
					 * keeping it commented out, because we might reconsider its use 
					statsVO.getApiKeyVO().setCurrentBalance(status.getRateLimitStatus().getRemaining());
					 */

					ParseStatus parseStatus = new ParseStatus(statsVO.getApiKeyVO().getApiName());
					parseStatus.setSuccess(true);
					statsVO.setParseStatus(parseStatus);
				} 
			}
		}

		return statsVO;
	}

	private Long parseId(String url){
		String id = url.substring(url.lastIndexOf("/") + 1);		
		if(id.matches("[0-9]+")){
			return Long.parseLong(id);
		} else {
			return null;
		}
	}

	private ParseStatus handleErrors(TwitterException e){
		ParseStatus parseStatus = new ParseStatus();
		parseStatus.setSuccess(false);
		parseStatus.setError_message(e.getErrorMessage());

		if(e.getStatusCode() == 401){
			parseStatus.setError_code(StatsParserErrorCodes.INVALID_APP);
		} else if(e.getStatusCode() == 403) {
			parseStatus.setError_code(StatsParserErrorCodes.PERMISSION_ERROR);
		} else if(e.getStatusCode() == 404) {
			parseStatus.setError_code(StatsParserErrorCodes.NOT_EXISTS);
		} else if(e.getStatusCode() == 420 || e.getStatusCode() == 429) {
			parseStatus.setError_code(StatsParserErrorCodes.API_ERROR);

			if(e.getErrorCode() == 88){
				parseStatus.setError_code(StatsParserErrorCodes.APP_REACHED_LIMIT);
			}
		} else {
			parseStatus.setError_code(StatsParserErrorCodes.UNKNOWN_ERROR);
		}

		return parseStatus;
	}

	/**
	 * Create TwiiterApiKeyset from string
	 * @param keys string with keys separated by commas in such order
	 * "consumerKey,consumerSecretKey,accessToken,accessTokenSecret"
	 * @return TwitterApiKey if @param keys is valid, null otherwise
	 */
	private TwitterApiKeyset getTwitterApiKeyset(String keys){
		String[] keysArrays = keys.split(",");

		if(keysArrays.length == 4){
			return new TwitterApiKeyset(keysArrays[0].trim(), keysArrays[1].trim(), keysArrays[2].trim(), keysArrays[3].trim());
		} else {
			return null;
		}
	}
	
}