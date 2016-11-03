package common.balance;

public class BalancePartitionHelper {

	/**
	 * Returns partition value in timeRangeInSeconds @param
	 * @param originalBalance
	 * @param timeRangeInSeconds
	 * @return
	 */
	public static long getBalancePartitionInTimeRange(float originalBalance, float originalTimeInSeconds, float timeRangeInSeconds){
		return Math.round((originalBalance/originalTimeInSeconds) * timeRangeInSeconds);
	}
	
}
