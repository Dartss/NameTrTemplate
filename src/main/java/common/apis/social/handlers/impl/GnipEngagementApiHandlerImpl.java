package common.apis.social.handlers.impl;

import jsmarty.core.common.apis.social.handlers.StatsApiHandler;
import jsmarty.core.common.apis.social.helpers.gnip.engagement.GnipEngagementHelper;
import jsmarty.core.common.apis.social.helpers.gnip.engagement.models.response.GnipResponse;
import jsmarty.core.common.apis.social.helpers.twitter.TwitterApiKeyset;
import jsmarty.core.sdo.Stats;
import jsmarty.core.sdo.stats.ParseStatus;
import jsmarty.core.sdo.stats.StatsParserErrorCodes;
import jsmarty.core.sdo.stats.TweetStats;
import jsmarty.core.socialstats.model.StatsVO;

/**
 * 
 * 				TODO
 * Don't use it, it's not tested!
 * 
 * Handler for GNIP Engagement API
 * http://support.gnip.com/apis/engagement_api/api_reference.html
 * 
 * Used for fetching statistics for tweets.
 * 
 * @author vit
 *
 */

public class GnipEngagementApiHandlerImpl implements StatsApiHandler {

	private GnipEngagementHelper gnipEngagementApiHelper;

	public GnipEngagementApiHandlerImpl() {
		this.gnipEngagementApiHelper = new GnipEngagementHelper();
	}

	@Override
	public StatsVO getStats(StatsVO statsVO) {
		String url = statsVO.getJob().getOrigin_url();

		Long id = parseId(url);

		if (id == null) {
			ParseStatus parseStatus = new ParseStatus(statsVO.getApiKeyVO().getApiName());
			parseStatus.setSuccess(false);
			parseStatus.setError_code(StatsParserErrorCodes.INVALID_ID);
			parseStatus.setError_message("Invalid tweet id");
			statsVO.setParseStatus(parseStatus);
		} else {
			TwitterApiKeyset keyset = getTwitterApiKeyset(statsVO.getApiKeyVO().getApiKey());

			if (keyset == null) {
				ParseStatus parseStatus = new ParseStatus(statsVO.getApiKeyVO().getApiName());
				parseStatus.setSuccess(false);
				parseStatus.setError_code(StatsParserErrorCodes.INVALID_APP);
				parseStatus.setError_message("Invalid api key");
				statsVO.setParseStatus(parseStatus);
			} else {
				GnipResponse response = this.gnipEngagementApiHelper.getSocialStatistics(id, keyset.getAccessToken(),
						keyset.getAceessTokenSecret(), keyset.getConsumerKey(), keyset.getConsumerSecretKey());

				TweetStats tweetStats = new TweetStats();;
				tweetStats.setFavorites(response.getFavorites());
				tweetStats.setRetweets(response.getRetweets());
				tweetStats.setReplies(response.getReplies());

				tweetStats.setTimestamp(System.currentTimeMillis());

				Stats stats = new Stats();
				stats.setTweet(tweetStats);
				statsVO.setStats(stats);

				ParseStatus parseStatus = new ParseStatus(statsVO.getApiKeyVO().getApiName());
				parseStatus.setSuccess(true);
				statsVO.setParseStatus(parseStatus);
			}
		}
		return statsVO;
	}

	private Long parseId(String url) {
		String id = url.substring(url.lastIndexOf("/") + 1);
		if (id.matches("[0-9]+")) {
			return Long.parseLong(id);
		} else {
			return null;
		}
	}

	/**
	 * Create TwiiterApiKeyset from string
	 * 
	 * @param keys
	 *            string with keys separated by commas in such order
	 *            "consumerKey,consumerSecretKey,accessToken,accessTokenSecret"
	 * @return TwitterApiKey if @param keys is valid, null otherwise
	 */
	private TwitterApiKeyset getTwitterApiKeyset(String keys) {
		String[] keysArrays = keys.split(",");

		if (keysArrays.length == 4) {
			return new TwitterApiKeyset(keysArrays[0].trim(), keysArrays[1].trim(), keysArrays[2].trim(),
					keysArrays[3].trim());
		} else {
			return null;
		}
	}
}
