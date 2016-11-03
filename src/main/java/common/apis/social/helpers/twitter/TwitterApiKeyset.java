package common.apis.social.helpers.twitter;

public class TwitterApiKeyset {

	private String consumerKey;
	private String consumerSecretKey;
	private String accessToken;
	private String aceessTokenSecret;

	public TwitterApiKeyset(){
	}

	public TwitterApiKeyset(String consumerKey, String consumerSecretKey, String accessToken, String accessTokenSecret){
		this.consumerKey = consumerKey;
		this.consumerSecretKey = consumerSecretKey;
		this.accessToken = accessToken;
		this.aceessTokenSecret = accessTokenSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getAceessTokenSecret() {
		return aceessTokenSecret;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public String getConsumerSecretKey() {
		return consumerSecretKey;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setAceessTokenSecret(String aceessTokenSecret) {
		this.aceessTokenSecret = aceessTokenSecret;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public void setConsumerSecretKey(String consumerSecretKey) {
		this.consumerSecretKey = consumerSecretKey;
	}

}