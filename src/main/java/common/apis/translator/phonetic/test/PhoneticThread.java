package common.apis.translator.phonetic.test;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;

import jsmarty.core.common.apis.translator.common.model.TranslatorResponse;
import jsmarty.core.common.apis.translator.phonetic.PhoneticTranslatorHandler;
import jsmarty.core.common.json.JsonHandler;
import jsmarty.core.common.json.impl.JsonHandlerImpl;

public class PhoneticThread implements Runnable {

	private PhoneticTranslatorHandler phoneticHandler;
	private List<String> words;

	public PhoneticThread(PhoneticTranslatorHandler handler , List<String> words) throws UnknownHostException, IOException {
		this.phoneticHandler = handler;
		this.words = words;
	}

	@Override
	public void run() {
		JsonHandler jsonHandler = new JsonHandlerImpl();

		for (int i = 0; i < 3; i++) {
			TranslatorResponse response = null;
			try {
				response = this.phoneticHandler.translate(words,"rus","eng");
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("result - " + jsonHandler.serialize(response));

		}

	}

}
