package common.balance.model;

import java.io.Serializable;
import java.util.Map;

public class ApiKeys implements Serializable{

	private int totalAvailableCallsNumber;
	private Map<String, ApiKeyVO> apiKeysMap;

	public ApiKeys(int totalAvailableCallsNumber, Map<String, ApiKeyVO> apiKeysMap) {
		super();
		this.totalAvailableCallsNumber = totalAvailableCallsNumber;
		this.apiKeysMap = apiKeysMap;
	}

	public ApiKeys() {
	}

	public int getTotalAvailableCallsNumber() {
		return totalAvailableCallsNumber;
	}

	public void setTotalAvailableCallsNumber(int totalAvailableCallsNumber) {
		this.totalAvailableCallsNumber = totalAvailableCallsNumber;
	}

	public Map<String, ApiKeyVO> getApiKeysMap() {
		return apiKeysMap;
	}

	public void setApiKeysMap(Map<String, ApiKeyVO> apiKeysMap) {
		this.apiKeysMap = apiKeysMap;
	}

}