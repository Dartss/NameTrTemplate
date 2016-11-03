package common.apis.social.helpers.gnip.engagement;

public final class  GnipEnagementConstants {
	
	public static final String URL_ENGAGEMENT_API = "https://data-api.twitter.com";
	
	public static final String METHOD_TOTALS = "/insights/engagement/totals";
	public static final String METHOD_28HR = "/insights/engagement/28hr";
	public static final String METHOD_HISTORICAL = "/insights/engagement/historical";
	
	public static final String URL_ENGAGEMENT_API_TOTALS = URL_ENGAGEMENT_API + METHOD_TOTALS;
	
	public static final String ENGAGEMENT_TYPE_FAVORITES = "favorites";
	public static final String ENGAGEMENT_TYPE_REPLIES = "replies";
	public static final String ENGAGEMENT_TYPE_RETWEETS = "retweets";
	
	public static final String GROUP_BY_TWEET_ID = "tweet.id";
	public static final String GROUP_BY_ENGAGEMENT_TYPE = "engagement.type";
	
}