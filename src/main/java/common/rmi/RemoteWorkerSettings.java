package common.rmi;

import java.io.Serializable;

public class RemoteWorkerSettings implements Serializable{

	private String name;
	private String host;
	private int port;
	private String bindingName;
	private int maximumWorkersNumber;
	private static int actualWorkersCount;
	private boolean active;
	
	public RemoteWorkerSettings(){
	}

	public RemoteWorkerSettings(String host, int port, int maximumWorkersNumber) {
		super();
		this.host = host;
		this.port = port;
		this.maximumWorkersNumber = maximumWorkersNumber;
	}

	public RemoteWorkerSettings(String host, int port, String bindingName, int maximumWorkersNumber) {
		super();
		this.host = host;
		this.port = port;
		this.bindingName = bindingName;
		this.maximumWorkersNumber = maximumWorkersNumber;
	}

	public RemoteWorkerSettings(String name, String host, int port, String bindingName, int maximumWorkersNumber) {
		super();
		this.name=name;
		this.host = host;
		this.port = port;
		this.bindingName = bindingName;
		this.maximumWorkersNumber = maximumWorkersNumber;
	}

	public boolean isAvailableWorker(){
		if(actualWorkersCount < maximumWorkersNumber){
			return true;
		}else{
			return false;
		}
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

	public int getMaximumWorkersNumber() {
		return maximumWorkersNumber;
	}

	public void setMaximumWorkersNumber(int maximumWorkersNumber) {
		this.maximumWorkersNumber = maximumWorkersNumber;
	}

	public String getBindingName() {
		return bindingName;
	}

	public void setBindingName(String bindingName) {
		this.bindingName = bindingName;
	}

	public int getActualWorkersCount() {
		return actualWorkersCount;
	}

	public void setActualWorkersCount(int actualWorkersCount) {
		this.actualWorkersCount = actualWorkersCount;
	}

	public void incrementActualWorkersCount() {
		System.out.println("------------------------------------_!!! incrementing actual count - "+this.actualWorkersCount);
		this.actualWorkersCount++;
	}

	public void decrementActualWorkersCount() {
		System.out.println("------------------------------------_!!! decrementing actual count - "+this.actualWorkersCount);
		this.actualWorkersCount--;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}