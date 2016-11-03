package common.balance.model;

import java.io.Serializable;
import java.util.List;

public class ApiSettings implements Serializable{
	
	private int apiId; //  for later use - when implementing the settings loading from mysql
	private String apiName;
	private String supportedJobType;

	private Integer maxCallsNumber;
	private List<String> hostsList;
	private List<ApiKeyVO> apiKeysList;

	private String apiResetSchedulerClass;
	private String fetcherHandlerClass;

	private int maxConcurrentConnectionsNumber;
	private boolean isConcurrentRestrictionEnabled;
	private boolean isConcurrentPerIp;
	private boolean isConcurrentPerKey;
	private boolean isConcurrentRandomized;
	private int minConcurrentRandom;
	private int maxConcurrentRandom;

	private long maxIntervalCallsNumber;
	private boolean isIntervalRestrictionEnabled;
	private boolean isIntervalPerIp;
	private boolean isIntervalPerKey;
	private long intervalTimeInSeconds;
	private boolean isIntervalPartitioned;
	private long partitionTimeRangeInSeconds;
	private boolean isIntervalRandomized;
	private int minIntervalRandom;
	private int maxIntervalRandom;

	private long maxDailyCallsNumber;
	private boolean isDailyRestrictionEnabled;
	private boolean isDailyPerIp;
	private boolean isDailyPerKey;
	private boolean isDailyRandomized;
	private int minDailyRandom;
	private int maxDailyRandom;

	private long maxMonthlyCallsNumber;
	private boolean isMonthlyRestrictionEnabled;
	private boolean isMonthlyPerIp;
	private boolean isMonthlyPerKey;
	private boolean isMonthlyRandomized;
	private int minMonthlyRandom;
	private int maxMonthlyRandom;

	public ApiSettings() {
	}

	public long getMaxDailyCallsNumber() {
		return maxDailyCallsNumber;
	}

	public void setMaxDailyCallsNumber(long maxDailyCallsNumber) {
		this.maxDailyCallsNumber = maxDailyCallsNumber;
	}

	public long getMaxMonthlyCallsNumber() {
		return maxMonthlyCallsNumber;
	}

	public void setMaxMonthlyCallsNumber(long maxMonthlyCallsNumber) {
		this.maxMonthlyCallsNumber = maxMonthlyCallsNumber;
	}

	public int getMaxConcurrentConnectionsNumber() {
		return maxConcurrentConnectionsNumber;
	}

	public void setMaxConcurrentConnectionsNumber(int maxConcurrentConnectionsNumber) {
		this.maxConcurrentConnectionsNumber = maxConcurrentConnectionsNumber;
	}

	public boolean isConcurrentRandomized() {
		return isConcurrentRandomized;
	}

	public void setConcurrentRandomized(boolean isConcurrentRandomized) {
		this.isConcurrentRandomized = isConcurrentRandomized;
	}

	public int getMinConcurrentRandom() {
		return minConcurrentRandom;
	}

	public void setMinConcurrentRandom(int minConcurrentRandom) {
		this.minConcurrentRandom = minConcurrentRandom;
	}

	public int getMaxConcurrentRandom() {
		return maxConcurrentRandom;
	}

	public void setMaxConcurrentRandom(int maxConcurrentRandom) {
		this.maxConcurrentRandom = maxConcurrentRandom;
	}

	public long getMaxIntervalCallsNumber() {
		return maxIntervalCallsNumber;
	}

	public void setMaxIntervalCallsNumber(long maxIntervalCallsNumber) {
		this.maxIntervalCallsNumber = maxIntervalCallsNumber;
	}

	public long getIntervalTimeInSeconds() {
		return intervalTimeInSeconds;
	}

	public void setIntervalTimeInSeconds(long maxIntervalTime) {
		this.intervalTimeInSeconds = maxIntervalTime;
	}

	public boolean isIntervalPartitioned() {
		return isIntervalPartitioned;
	}

	public void setIntervalPartitioned(boolean isIntervalPartitioned) {
		this.isIntervalPartitioned = isIntervalPartitioned;
	}

	public long getPartitionTimeRangeInSeconds() {
		return partitionTimeRangeInSeconds;
	}

	public void setPartitionTimeRangeInSeconds(long partitionTimeRangeInSeconds) {
		this.partitionTimeRangeInSeconds = partitionTimeRangeInSeconds;
	}

	public boolean isIntervalRandomized() {
		return isIntervalRandomized;
	}

	public void setIntervalRandomized(boolean isIntervalRandomized) {
		this.isIntervalRandomized = isIntervalRandomized;
	}

	public int getMinIntervalRandom() {
		return minIntervalRandom;
	}

	public void setMinIntervalRandom(int minIntervalRandom) {
		this.minIntervalRandom = minIntervalRandom;
	}

	public int getMaxIntervalRandom() {
		return maxIntervalRandom;
	}

	public void setMaxIntervalRandom(int maxIntervalRandom) {
		this.maxIntervalRandom = maxIntervalRandom;
	}

	public boolean isDailyRandomized() {
		return isDailyRandomized;
	}

	public void setDailyRandomized(boolean isDailyRandomized) {
		this.isDailyRandomized = isDailyRandomized;
	}

	public int getMinDailyRandom() {
		return minDailyRandom;
	}

	public void setMinDailyRandom(int minDailyRandom) {
		this.minDailyRandom = minDailyRandom;
	}

	public int getMaxDailyRandom() {
		return maxDailyRandom;
	}

	public void setMaxDailyRandom(int maxDailyRandom) {
		this.maxDailyRandom = maxDailyRandom;
	}

	public boolean isMonthlyRandomized() {
		return isMonthlyRandomized;
	}

	public void setMonthlyRandomized(boolean isMonthlyRandomized) {
		this.isMonthlyRandomized = isMonthlyRandomized;
	}

	public int getMinMonthlyRandom() {
		return minMonthlyRandom;
	}

	public void setMinMonthlyRandom(int minMonthlyRandom) {
		this.minMonthlyRandom = minMonthlyRandom;
	}

	public int getMaxMonthlyRandom() {
		return maxMonthlyRandom;
	}

	public void setMaxMonthlyRandom(int maxMonthlyRandom) {
		this.maxMonthlyRandom = maxMonthlyRandom;
	}

	public String getFetcherHandlerClass() {
		return fetcherHandlerClass;
	}

	public void setFetcherHandlerClass(String fetcherHandlerClass) {
		this.fetcherHandlerClass = fetcherHandlerClass;
	}

	public ApiSettings(String apiName) {
		this.apiName = apiName;
	}

	public String getSupportedJobType() {
		return supportedJobType;
	}

	public void setSupportedJobType(String supportedJobType) {
		this.supportedJobType = supportedJobType;
	}

	public String getApiName() {
		return apiName;
	}

	public Integer getMaxCallsNumber() {
		return maxCallsNumber;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public void setMaxCallsNumber(Integer maxCallsNumber) {
		this.maxCallsNumber = maxCallsNumber;
	}

	public String getApiResetSchedulerClass() {
		return apiResetSchedulerClass;
	}

	public void setApiResetTimerClass(String apiResetSchedulerClass) {
		this.apiResetSchedulerClass = apiResetSchedulerClass;
	}

	public boolean isConcurrentRestrictionEnabled() {
		return isConcurrentRestrictionEnabled;
	}

	public void setConcurrentRestrictionEnabled(boolean isConcurrentRestrictionEnabled) {
		this.isConcurrentRestrictionEnabled = isConcurrentRestrictionEnabled;
	}

	public boolean isIntervalRestrictionEnabled() {
		return isIntervalRestrictionEnabled;
	}

	public void setIntervalRestrictionEnabled(boolean isIntervalRestrictionEnabled) {
		this.isIntervalRestrictionEnabled = isIntervalRestrictionEnabled;
	}

	public boolean isDailyRestrictionEnabled() {
		return isDailyRestrictionEnabled;
	}

	public void setDailyRestrictionEnabled(boolean isDailyRestrictionEnabled) {
		this.isDailyRestrictionEnabled = isDailyRestrictionEnabled;
	}

	public boolean isMonthlyRestrictionEnabled() {
		return isMonthlyRestrictionEnabled;
	}

	public void setMonthlyRestrictionEnabled(boolean isMonthlyRestrictionEnabled) {
		this.isMonthlyRestrictionEnabled = isMonthlyRestrictionEnabled;
	}

	public void setApiResetSchedulerClass(String apiResetSchedulerClass) {
		this.apiResetSchedulerClass = apiResetSchedulerClass;
	}

	public boolean isConcurrentPerIp() {
		return isConcurrentPerIp;
	}

	public void setConcurrentPerIp(boolean isConcurrentPerIp) {
		this.isConcurrentPerIp = isConcurrentPerIp;
	}

	public boolean isConcurrentPerKey() {
		return isConcurrentPerKey;
	}

	public void setConcurrentPerKey(boolean isConcurrentPerKey) {
		this.isConcurrentPerKey = isConcurrentPerKey;
	}

	public boolean isIntervalPerIp() {
		return isIntervalPerIp;
	}

	public void setIntervalPerIp(boolean isIntervalPerIp) {
		this.isIntervalPerIp = isIntervalPerIp;
	}

	public boolean isIntervalPerKey() {
		return isIntervalPerKey;
	}

	public void setIntervalPerKey(boolean isIntervalPerKey) {
		this.isIntervalPerKey = isIntervalPerKey;
	}

	public boolean isDailyPerIp() {
		return isDailyPerIp;
	}

	public void setDailyPerIp(boolean isDailyPerIp) {
		this.isDailyPerIp = isDailyPerIp;
	}

	public boolean isDailyPerKey() {
		return isDailyPerKey;
	}

	public void setDailyPerKey(boolean isDailyPerKey) {
		this.isDailyPerKey = isDailyPerKey;
	}

	public boolean isMonthlyPerIp() {
		return isMonthlyPerIp;
	}

	public void setMonthlyPerIp(boolean isMonthlyPerIp) {
		this.isMonthlyPerIp = isMonthlyPerIp;
	}

	public boolean isMonthlyPerKey() {
		return isMonthlyPerKey;
	}

	public void setMonthlyPerKey(boolean isMonthlyPerKey) {
		this.isMonthlyPerKey = isMonthlyPerKey;
	}

	public List<String> getHostsList() {
		return hostsList;
	}

	public void setHostsList(List<String> hostsList) {
		this.hostsList = hostsList;
	}

	public List<ApiKeyVO> getApiKeysList() {
		return apiKeysList;
	}

	public void setApiKeysList(List<ApiKeyVO> apiKeysList) {
		this.apiKeysList = apiKeysList;
	}

}