package common.apis.translator.phonetic;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import jsmarty.core.common.apis.translator.common.TranslatorHandler;
import jsmarty.core.common.apis.translator.common.model.TranslatorResponse;

/**
 * This handler uses for getting the transliteration of words.
 * 
 * @author vit
 *
 */

public class PhoneticTranslatorHandler implements TranslatorHandler{

	private String key; // concatenation of phonetic server ip and port
	private String host;
	private int port;

	private PhoneticTranslatorHelper phoneticHelper;

	public PhoneticTranslatorHandler() throws UnknownHostException, IOException{
	}

	public PhoneticTranslatorHandler(String host, int port) {
		this.host = host;
		this.port = port;

		try {
			this.phoneticHelper = new PhoneticTranslatorHelper(this.host, this.port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * this method should be triggered when setting the key after creating new instance using EMPTY constructor
	 */
	public void init(){

		String[] keys = this.key.split(",");
		this.host = keys[0];
		this.port = Integer.parseInt(keys[1]);

		try {
			this.phoneticHelper = new PhoneticTranslatorHelper(this.host, this.port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public TranslatorResponse translate(List<String> textList, String langFrom, String langTo) {
		if(this.phoneticHelper == null){
			init();
		}
		return this.phoneticHelper.getTransliteration(textList, langFrom, langTo);
	}

	/**
	 * key is the concatenation of ip and port (separated with ",")
	 */
	@Override
	public void setApiKey(String key) {
		this.key = key;
	}

	/**
	 * NO OP
	 */
	@Override
	public TranslatorResponse translate(String text, String langFrom, String langTo) {
		return null;
	}

}