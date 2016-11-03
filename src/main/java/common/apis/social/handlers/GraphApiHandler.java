package common.apis.social.handlers;


import com.restfb.exception.FacebookException;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.Post;
import jsmarty.core.common.apis.social.handlers.StatsApiHandler;
import jsmarty.core.common.apis.social.helpers.graph.GraphApiHelper;
import jsmarty.core.sdo.Stats;
import jsmarty.core.sdo.stats.FacebookPostStats;
import jsmarty.core.sdo.stats.ParseStatus;
import jsmarty.core.sdo.stats.StatsParserErrorCodes;
import jsmarty.core.socialstats.models.StatsVO;
/**
 *
 * Handler for Facebok Graph API
 *
 * @author vit
 *
 */
public class GraphApiHandler implements StatsApiHandler {
	private GraphApiHelper graphApiHelper;
	public GraphApiHandler() {
		this.graphApiHelper = new GraphApiHelper();
	}
	@Override
	public StatsVO getStats(StatsVO statsVO) {
		String[] keysArray = statsVO.getApiKeyVO().getApiKey().split(",");
		String appId = keysArray[0];
		String appSecret = keysArray[1];
		String postId = statsVO.getJob().getOrigin_id();
		if (!isPostIdValid(postId)) {
			ParseStatus status = new ParseStatus(statsVO.getApiKeyVO().getApiName());
			status.setSuccess(false);
			status.setError_message("Invalid Post id");
			statsVO.setParseStatus(status);
			return statsVO;
		}
		Post post = null;
		try{
			post = graphApiHelper.getPost(postId, appId, appSecret);
		} catch (FacebookOAuthException foaex){
			ParseStatus status = handleErrors(foaex);
			status.setApi_name(statsVO.getApiKeyVO().getApiName());
			statsVO.setParseStatus(status);
		} catch (FacebookException e){
			ParseStatus status = new ParseStatus(statsVO.getApiKeyVO().getApiName());
			status.setSuccess(false);
			status.setError_message(e.getMessage());
			status.setError_code(StatsParserErrorCodes.UNKNOWN_ERROR);
			statsVO.setParseStatus(status);
		}
		if(post != null){
			FacebookPostStats facebookPostStats = new FacebookPostStats();
			facebookPostStats.setComments(post.getCommentsCount());
			facebookPostStats.setLikes(post.getLikesCount());
			facebookPostStats.setShares(post.getSharesCount());
			facebookPostStats.setTimestamp(System.currentTimeMillis());
			Stats stats = new Stats();
			stats.setFacebook_post(facebookPostStats);
			statsVO.setStats(stats);
			ParseStatus status = new ParseStatus(statsVO.getApiKeyVO().getApiName());
			status.setSuccess(true);
			statsVO.setParseStatus(status);
		}
		return statsVO;
	}
	private boolean isPostIdValid(String postId) {
		return (postId.matches("[_0-9]+"));
	}
	private ParseStatus handleErrors(FacebookOAuthException e){
		int normalizedErrorCode = StatsParserErrorCodes.UNKNOWN_ERROR;
		int errorCode = e.getErrorCode();

		if(errorCode == 2 || errorCode == 4 || errorCode == 17){
			normalizedErrorCode = StatsParserErrorCodes.API_ERROR;
		} else if(errorCode == 10 || (errorCode >= 200 && errorCode <= 299)){
			normalizedErrorCode = StatsParserErrorCodes.PERMISSION_ERROR;
		} else if(errorCode == 341){
			normalizedErrorCode = StatsParserErrorCodes.APP_REACHED_LIMIT;
		} else if (errorCode == 102 || errorCode == 1){
			normalizedErrorCode = StatsParserErrorCodes.INVALID_APP;
		}

		ParseStatus status = new ParseStatus();
		status.setSuccess(false);
		status.setError_code(normalizedErrorCode);
		status.setError_message(e.getErrorMessage());
		return status;
	}
}
