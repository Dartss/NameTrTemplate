package common.apis.social.helpers.twitter;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Helper built on top of twitter4j -
 * @author oleg
 *
 */
public class TwitterApiHelper {

	private Twitter twitter;

	public TwitterApiHelper(){
	}

	public TwitterApiHelper(TwitterApiKeyset keyset){
		this.twitter = initTwitter(keyset);
	}

	public TwitterApiHelper(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
		this.twitter = initTwitter(consumerKey, consumerSecret, accessToken, accessTokenSecret);
	}

	public void setApiKeys(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret){
		this.twitter = initTwitter(consumerKey, consumerSecret, accessToken, accessTokenSecret);
	}

	public void setApiKeys(TwitterApiKeyset keyset){
		this.twitter = initTwitter(keyset);
	}

	public Status getFavorites(long twitterId) throws TwitterException{
		return this.twitter.showStatus(twitterId);
	}

	public Status getRetweets(long twitterId) throws TwitterException{
		return this.twitter.retweetStatus(twitterId);
	}

	private Twitter initTwitter(TwitterApiKeyset keyset){
		Twitter twitter = initTwitter(keyset.getConsumerKey(), keyset.getConsumerSecretKey(), keyset.getAccessToken(), keyset.getAceessTokenSecret());
		return twitter;
	}

	private Twitter initTwitter(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret){
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.setDebugEnabled(false).setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecret)
		.setOAuthAccessToken(accessToken).setOAuthAccessTokenSecret(accessTokenSecret);

		TwitterFactory twitterFactory = new TwitterFactory(configurationBuilder.build());
		return twitterFactory.getInstance();
	}

}