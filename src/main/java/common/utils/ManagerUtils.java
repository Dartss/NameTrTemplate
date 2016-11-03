package common.utils;

import java.util.Calendar;
import java.util.Date;

import jsmarty.core.common.properties.QueuesProperties;
import jsmarty.core.common.queuer.QueuerPoolHandler;
import jsmarty.core.common.queuer.QueuerPoolHandlerImpl;

public class ManagerUtils {

	
	/**
	 * Check if the next balance reset date is reached,
	 * example for a daily reset, use the following parameters:
	 * checkForPeriodReset("name of the redis key where lastReset is stored", Calendar.DATE, 1)
	 * @param lastResetRedisKey
	 * @param calendarConstUnit
	 * @param calendarConstOffset
	 * @return
	 */
	public static boolean checkForPeriodReset(String lastResetRedisKey, int calendarConstUnit, int calendarConstOffset) {
		
		QueuerPoolHandler queuerPoolHandler  = new QueuerPoolHandlerImpl(QueuesProperties.getCACHE_SERVER_HOST(), QueuesProperties.getCACHE_SERVER_PORT());
		Date dailyResetDate = DateUtils.parseDate(queuerPoolHandler.get(lastResetRedisKey));

		Date now = new Date();
		if (dailyResetDate != null) {
			
			// getting next daily reset date
			Calendar cal = Calendar.getInstance();
			cal.setTime(dailyResetDate);
			cal.add(calendarConstUnit, calendarConstOffset);
			dailyResetDate = cal.getTime();
			
			// checking if period are reached and needs to reset
			if (dailyResetDate.before(now)) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}
	
}
