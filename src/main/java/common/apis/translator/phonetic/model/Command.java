package common.apis.translator.phonetic.model;

import java.util.Map;

public class Command {
	
	private String name;
	private Map<String, String> args;
	
	public Command(){
		
	}
	
	public Map<String, String> getArgs() {
		return args;
	}
	
	public String getName() {
		return name;
	}
	
	public void setArgs(Map<String, String> args) {
		this.args = args;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}