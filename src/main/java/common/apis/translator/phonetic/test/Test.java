package common.apis.translator.phonetic.test;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jsmarty.core.common.apis.translator.phonetic.PhoneticTranslatorHandler;

// TODO: remove this after testing
public class Test {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {		
		List<List<String>>  words = new ArrayList<>();
		words.add(Arrays.asList("Владимир Путин", "Петр Порошенко"));
		words.add(Arrays.asList("","Київ"));
		words.add(Arrays.asList("Киев"));
		words.add(Arrays.asList("Лондон"));
		words.add(Arrays.asList("кіт","пес","кіт"));
		
		PhoneticTranslatorHandler phoneticHandler = new PhoneticTranslatorHandler("82.192.87.234", 9000);

		for(int i=0; i < 5; i++){
			PhoneticThread phoneticThread = new PhoneticThread(phoneticHandler, words.get(i));
			Thread thread = new Thread(phoneticThread);
			thread.start();
		}

	}
}
