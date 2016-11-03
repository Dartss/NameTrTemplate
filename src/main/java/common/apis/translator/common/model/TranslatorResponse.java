package common.apis.translator.common.model;

import java.util.Map;

import jsmarty.core.sdo.Api;

public class TranslatorResponse {

	private String id;
	private Map<String,String> translation;
	private Api api;
	
	public TranslatorResponse(){
	}

	public Map<String, String> getTranslation() {
		return translation;
	}

	public void setTranslation(Map<String, String> translation) {
		this.translation = translation;
	}

	public Api getApi() {
		return api;
	}

	public void setApi(Api api) {
		this.api = api;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}