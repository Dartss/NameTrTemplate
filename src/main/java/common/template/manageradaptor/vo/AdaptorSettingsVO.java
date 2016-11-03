package common.template.manageradaptor.vo;


import java.io.Serializable;

import jsmarty.core.common.template.manageradaptor.Adaptor;

public class AdaptorSettingsVO implements Serializable{

	private String name;
	private String host;
	private int port;
	private String bindingName;
	private int maximumWorkersNumber;
	private int actualWorkersCount;
	private boolean active;

	private Adaptor adaptor;

	public int getActualWorkersCount() {
		return actualWorkersCount;
	}

	public String getBindingName() {
		return bindingName;
	}

	public String getHost() {
		return host;
	}

	public int getMaximumWorkersNumber() {
		return maximumWorkersNumber;
	}

	public String getName() {
		return name;
	}

	public int getPort() {
		return port;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setActualWorkersCount(int actualWorkersCount) {
		actualWorkersCount = actualWorkersCount;
	}

	public void setBindingName(String bindingName) {
		this.bindingName = bindingName;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setMaximumWorkersNumber(int maximumWorkersNumber) {
		this.maximumWorkersNumber = maximumWorkersNumber;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isActive() {
		return active;
	}

	public Adaptor getAdaptor() {
		return adaptor;
	}

	public void setAdaptor(Adaptor adaptor) {
		this.adaptor = adaptor;
	}

	public void incrementActualWorkersCount(){
		actualWorkersCount++;
	}

	public void decrementActualWorkersCount(){
		actualWorkersCount--;
	}

	public boolean isWorkerAvailable(){
		return actualWorkersCount < this.maximumWorkersNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bindingName == null) ? 0 : bindingName.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + maximumWorkersNumber;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + port;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdaptorSettingsVO other = (AdaptorSettingsVO) obj;
		if (bindingName == null) {
			if (other.bindingName != null)
				return false;
		} else if (!bindingName.equals(other.bindingName))
			return false;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (maximumWorkersNumber != other.maximumWorkersNumber)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (port != other.port)
			return false;
		return true;
	}

}