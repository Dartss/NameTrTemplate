package common.apis.translator.phonetic.model;

public class PhoneticRequest {
	
	private String id;
	private Command command;
	private Data data;
	
	public PhoneticRequest(){
		
	}
	
	public Command getCommand() {
		return command;
	}
	
	public Data getData() {
		return data;
	}
	
	public String getId() {
		return id;
	}
	
	public void setCommand(Command command) {
		this.command = command;
	}
	
	public void setData(Data data) {
		this.data = data;
	}
	
	public void setId(String id) {
		this.id = id;
	}
}