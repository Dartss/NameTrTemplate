package common.apis.social.helpers.facebook.graph;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.FacebookException;
import com.restfb.types.Post;

/**
 * 
 * @author dmitry
 *
 */
public class GraphApiHelper {

	private FacebookClient facebookClient;

	public GraphApiHelper(){
	}

	public GraphApiHelper(String accessToken, String appSecret) {
		this.facebookClient = getClient(accessToken, appSecret);
	}

	/**
	 * Fetch Facebook post by id
	 * Make sure that you added access token
	 * @param postId - Takes String in format :
	 * "userPageID_postID"; example :
	 * "283268618364619_1242392792452192"
	 *
	 * @return Returns restfb.types.Post object.
	 * @throws FacebookException
	 */

	public Post getPost(String postId) throws FacebookException {
		return this.facebookClient.fetchObject
				(postId, Post.class, Parameter.with("fields", "likes.summary(true), " +
						"comments.summary(true), reactions.summary(true), shares, id"));
	}

	/**
	 * @param postId - Takes String in format :
	 * "userPageID_postID"; example :
	 * "283268618364619_1242392792452192"
	 *
	 * @return Returns restfb.types.Post object.
	 *
	 * @throws FacebookException
	 */
	public Post getPost(String postId, String appId, String appSecret) throws FacebookException {
		this.facebookClient = getClient(appId, appSecret);
		return getPost(postId);
	}

	private FacebookClient getClient(String appId, String appSecret){
		FacebookClient facebookClient = new DefaultFacebookClient(Version.VERSION_2_7);
		AccessToken accessToken = facebookClient.obtainAppAccessToken(appId, appSecret);
		facebookClient = new DefaultFacebookClient(accessToken.getAccessToken(), Version.VERSION_2_7);
		return facebookClient;
	}

}