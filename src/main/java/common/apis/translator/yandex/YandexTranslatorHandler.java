package common.apis.translator.yandex;

import java.util.List;

import jsmarty.core.common.apis.translator.common.TranslatorHandler;
import jsmarty.core.common.apis.translator.common.model.TranslatorResponse;

/**
 * Handler for YandexHelper translator
 *
 * @author Dima
 */

public class YandexTranslatorHandler implements TranslatorHandler {

	private String apiKey;
	
	private YandexTranslatorHelper helper;
	private static final String API_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate";
	private static final String ENCODING = "UTF-8";
	private static final String SEPARATOR = "~";
	
	public YandexTranslatorHandler(){
	}

	public YandexTranslatorHandler(String API_KEY) {
		this.apiKey = apiKey;
	}

	public void initHelper() {
		this.helper = new YandexTranslatorHelper(API_URL, apiKey, SEPARATOR, ENCODING);		
	}

	@Override
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	@Override
	public TranslatorResponse translate(String text, String langFrom, String langTo) {
		if(this.helper == null){
			initHelper();
		}
		return this.helper.translate(text, langFrom, langTo);
	}

	@Override
	public TranslatorResponse translate(List<String> textList, String langFrom, String langTo) {
		if(this.helper == null){
			initHelper();
		}
		return this.helper.translate(textList, langFrom, langTo);
	}

}