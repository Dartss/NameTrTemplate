package common.utils.bean;

import common.utils.Constants;

import java.io.Serializable;


public class ObjectCallProperties implements Serializable{

	private Constants.CALL_TYPE calltype;
	private String host;
	private int port;
	private String objectName;

	public ObjectCallProperties(Constants.CALL_TYPE calltype, String host, int port, String objectName) {
		super();
		this.calltype = calltype;
		this.objectName = objectName;
		this.host = host;
		this.port = port;
	}

	public ObjectCallProperties(Constants.CALL_TYPE calltype, String objectName) {
		super();
		this.calltype = calltype;
		this.objectName = objectName;
	}

	public Constants.CALL_TYPE getCalltype() {
		return calltype;
	}

	public void setCalltype(Constants.CALL_TYPE calltype) {
		this.calltype = calltype;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}