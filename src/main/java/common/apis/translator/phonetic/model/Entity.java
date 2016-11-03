package common.apis.translator.phonetic.model;

public class Entity {

	private String eng;
	private Phonetics phonetics;
	
	public Entity(){
		
	}
	
	public String getEng() {
		return eng;
	}
	
	public Phonetics getPhonetics() {
		return phonetics;
	}
	
	public void setEng(String eng) {
		this.eng = eng;
	}
	
	public void setPhonetics(Phonetics phonetics) {
		this.phonetics = phonetics;
	}
}
