package common.apis.social.helpers.gnip.engagement.models.response;

public class GnipResponse {

	private Long favorites;
	private Long replies;
	private Long retweets;

	public GnipResponse(){
	}

	public Long getFavorites() {
		return favorites;
	}

	public Long getReplies() {
		return replies;
	}

	public Long getRetweets() {
		return retweets;
	}

	public void setFavorites(Long favorites) {
		this.favorites = favorites;
	}

	public void setReplies(Long replies) {
		this.replies = replies;
	}

	public void setRetweets(Long retweets) {
		this.retweets = retweets;
	}

}