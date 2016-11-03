package common.balance.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ApiKeyVO implements Serializable {

	private String apiName;
	private String apiKey;
	private String apiKeyName;
	private List<String> hostsList;
	private boolean isCommonForAllIps;

	private int availableCalls;
	private String serverHost; // we must register here the actual ip of actual adaptor that processed this statsvo--> apikeyvo
	private List<String> keysQueuesNames = new ArrayList<>(); // daily, monthly, interval
	private String concurrentKeyQueueName; // concurrent

	//	Integer maxBalance;
	//	Integer currentBalance;

	public ApiKeyVO(){
	}

	public ApiKeyVO(String apiKey, String apiName){
		this.apiKey = apiKey;
		this.apiName = apiName;
	}

	public ApiKeyVO(String apiKey, String apiName, String serverHost) {
		super();
		this.apiKey = apiKey;
		this.apiName = apiName;
		this.serverHost = serverHost;
	}

	public ApiKeyVO(String apiKey, String apiName, String serverHost, int availableCalls) {
		super();
		this.apiKey = apiKey;
		this.apiName = apiName;
		this.serverHost = serverHost;
		this.availableCalls = availableCalls;
	}

	public ApiKeyVO(String apiName, String apiKey, String apiKeyName, List<String> hostsList, boolean isCommonForAllIps) {
		super();
		this.apiName = apiName;
		this.apiKey = apiKey;
		this.apiKeyName = apiKeyName;
		this.hostsList = hostsList;
		this.isCommonForAllIps = isCommonForAllIps;
	}

	public String getApiKeyName() {
		return apiKeyName;
	}

	public void setApiKeyName(String apiKeyName) {
		this.apiKeyName = apiKeyName;
	}

	public String getConcurrentKeyQueueName() {
		return concurrentKeyQueueName;
	}

	public void setConcurrentKeyQueueName(String concurrentKeyQueueName) {
		this.concurrentKeyQueueName = concurrentKeyQueueName;
	}

	public void setKeysQueuesNames(List<String> keysQueuesNames) {
		this.keysQueuesNames = keysQueuesNames;
	}

	public List<String> getKeysQueuesNames() {
		return keysQueuesNames;
	}

	public void setQueuesNames(List<String> queuesNames) {
		this.keysQueuesNames = queuesNames;
	}

	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	public String getApiKey() {
		return apiKey;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apiKey == null) ? 0 : apiKey.hashCode());
		result = prime * result + ((apiName == null) ? 0 : apiName.hashCode());
		return result;
	}

	public int getAvailableCalls() {
		return availableCalls;
	}

	public void setAvailableCalls(int availableCalls) {
		this.availableCalls = availableCalls;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object){
			return true;
		}
		if (object == null){
			return false;
		}
		if (this.getClass() != object.getClass()){
			return false;
		}

		ApiKeyVO apiKeyVO = (ApiKeyVO) object;
		if (this.apiKey == null) {
			if (apiKeyVO.apiKey != null){
				return false;
			}
		} else if (!apiKey.equals(apiKeyVO.apiKey)){
			return false;
		}

		if (apiName == null) {
			if (apiKeyVO.apiName != null){
				return false;
			}
		} else if (!apiName.equals(apiKeyVO.apiName)){
			return false;
		}

		return true;
	}

	public List<String> getHostsList() {
		return hostsList;
	}

	public void setHostsList(List<String> hostsList) {
		this.hostsList = hostsList;
	}

	public boolean isCommonForAllIps() {
		return isCommonForAllIps;
	}

	public void setCommonForAllIps(boolean isCommonForAllIps) {
		this.isCommonForAllIps = isCommonForAllIps;
	}

}