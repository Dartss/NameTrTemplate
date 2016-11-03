package common.http;

import java.io.Serializable;

import org.apache.http.NameValuePair;

public class NameValuePairObj  implements NameValuePair, Serializable{

	private String name;
	private String value;
	
	public NameValuePairObj(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getValue() {
		return value;
	}

}
