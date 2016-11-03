package common.apis.translator.phonetic.model;

import java.util.Map;

public class PhoneticResponse {

	private Map<String,Entity> entities;

	public PhoneticResponse(){

	}

	public Map<String,Entity> getEntities() {
		return entities;
	}

	public void setEntities(Map<String,Entity> entities) {
		this.entities = entities;
	}

}