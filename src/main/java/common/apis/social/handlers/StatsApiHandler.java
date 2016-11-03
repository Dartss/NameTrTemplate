package common.apis.social.handlers;

import jsmarty.core.socialstats.model.StatsVO;

public interface StatsApiHandler {
	
    /**
     * @param statsVO StatsVO object to update
     * @return updated StatsVO object
     */
    public StatsVO getStats(StatsVO statsVO);

}
