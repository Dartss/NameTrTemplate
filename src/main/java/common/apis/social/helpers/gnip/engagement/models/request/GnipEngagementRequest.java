package common.apis.social.helpers.gnip.engagement.models.request;

import java.util.List;

public class GnipEngagementRequest {

	private List<String> tweet_ids;
	private List<String> engagement_types;
	private Groupings groupings;

	public GnipEngagementRequest() {
		super();
	}

	public List<String> getTweet_ids() {
		return tweet_ids;
	}

	public void setTweet_ids(List<String> tweet_ids) {
		this.tweet_ids = tweet_ids;
	}

	public List<String> getEngagement_types() {
		return engagement_types;
	}

	public void setEngagement_types(List<String> engagement_types) {
		this.engagement_types = engagement_types;
	}

	public Groupings getGroupings() {
		return groupings;
	}

	public void setGroupings(Groupings groupings) {
		this.groupings = groupings;
	}

}