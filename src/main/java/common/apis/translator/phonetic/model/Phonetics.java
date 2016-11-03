package common.apis.translator.phonetic.model;

import java.util.List;

public class Phonetics {
	
	private List<String> doublemetaphone;
	private String soundex;
	
	public Phonetics(){
		
	}
	
	public List<String> getDoublemetaphone() {
		return doublemetaphone;
	}
	
	public String getSoundex() {
		return soundex;
	}
	
	public void setDoublemetaphone(List<String> doublemetaphone) {
		this.doublemetaphone = doublemetaphone;
	}
	
	public void setSoundex(String soundex) {
		this.soundex = soundex;
	}
	
}