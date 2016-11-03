package common.balance.model;

import java.io.Serializable;
import java.util.HashMap;

public class AvailableApisCalls implements Serializable{

	private String jobType;
	private int totalAvailableCalls; 
	private HashMap<String, ApiKeys> availableApisKeysPerIp;

	public AvailableApisCalls(String jobType, int totalAvailableCalls,HashMap<String, ApiKeys> availableApisKeysPerIp) {
		super();
		this.jobType = jobType;
		this.totalAvailableCalls = totalAvailableCalls;
		this.availableApisKeysPerIp = availableApisKeysPerIp;
	}

	public AvailableApisCalls() {
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public int getTotalAvailableCalls() {
		return totalAvailableCalls;
	}

	public void setTotalAvailableCalls(int totalAvailableCalls) {
		this.totalAvailableCalls = totalAvailableCalls;
	}

	public HashMap<String, ApiKeys> getAvailableApisKeysPerIp() {
		if(availableApisKeysPerIp == null){
			availableApisKeysPerIp = new HashMap<>();
		}
		return availableApisKeysPerIp;
	}

	public void setAvailableApisKeysPerIp(HashMap<String, ApiKeys> availableApisKeysPerIp) {
		this.availableApisKeysPerIp = availableApisKeysPerIp;
	}

}