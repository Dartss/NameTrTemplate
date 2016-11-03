package common.apis.social.handlers.impl;

import jsmarty.core.common.apis.social.handlers.StatsApiHandler;
import jsmarty.core.common.apis.social.helpers.facebook.url.GraphUrlHelper;
import jsmarty.core.common.apis.social.helpers.facebook.url.GraphUrlResponse;
import jsmarty.core.sdo.Stats;
import jsmarty.core.sdo.stats.ParseStatus;
import jsmarty.core.sdo.stats.StatsParserErrorCodes;
import jsmarty.core.sdo.stats.UrlFacebookStats;
import jsmarty.core.socialstats.model.StatsVO;

/**
 * Helper for fetching facebook social stats (only comments and shares count) for a URL
 * @author vit
 *
 */

public class GraphUrlApiHandlerImpl implements StatsApiHandler {

	private GraphUrlHelper graphUrlHelper;
	private static final String API_NAME = "graphUrlAPI"; 

	public GraphUrlApiHandlerImpl() {
		this.graphUrlHelper = new GraphUrlHelper();
	}

	@Override
	public StatsVO getStats(StatsVO statsVO) {

		String url = statsVO.getJob().getOrigin_url();

		GraphUrlResponse graphUrlResponse = this.graphUrlHelper.getStats(url);

		if(graphUrlResponse != null && graphUrlResponse.getShare() != null) {
			ParseStatus parseStatus = new ParseStatus();
			parseStatus.setSuccess(true);
			parseStatus.setApi_name(API_NAME);

			UrlFacebookStats urlStats = new UrlFacebookStats();
			urlStats.setComments(graphUrlResponse.getShare().getComment_count());
			urlStats.setShares(graphUrlResponse.getShare().getShare_count());
			urlStats.setTimestamp(System.currentTimeMillis());

			Stats stats = new Stats();
			stats.setUrl_facebook(urlStats);

			statsVO.setStats(stats);
			statsVO.setParseStatus(parseStatus);	
		} else {
			/**
			 * HANDLE MORE SPECIFIC ERRORS
			 */
			ParseStatus parseStatus = new ParseStatus();
			parseStatus.setSuccess(false);
			parseStatus.setError_code(StatsParserErrorCodes.UNKNOWN_ERROR);
			parseStatus.setError_message("returned response was null.");
			parseStatus.setApi_name(API_NAME);
			statsVO.setParseStatus(parseStatus);	
		}

		return statsVO;
	}

}