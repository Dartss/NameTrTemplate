package common.balance;

import java.util.HashMap;
import java.util.Map;

public class KeyNamePattern{

	private Map<String, String> map;

	public KeyNamePattern(){
		map = new HashMap<>();		
	}

	public KeyNamePattern setApiName(String apiName){
		map.put("$apiName$", apiName);
		return this;
	}
	public KeyNamePattern setIp(String ip){
		map.put("$ip$", ip);
		return this;
	}
	public KeyNamePattern setApiKey(String apiKey){
		map.put("$apiKey$", apiKey);		
		return this;
	}
	public KeyNamePattern setCallType(String callType){
		map.put("$callType$", callType);	
		return this;
	}
	public KeyNamePattern setAdaptorName(String adaptorName){
		map.put("$adaptor$", adaptorName);
		return this;
	}
	public KeyNamePattern setJobType(String jobType){
		map.put("$jobType$", jobType);
		return this;
	}
	public KeyNamePattern setKeyName(String keyname) {
		map.put("$keyname$", keyname);
		return this;
	}

	public Map<String, String> getMap() {
		return map;
	}

}