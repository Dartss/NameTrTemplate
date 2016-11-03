package common.apis.translator.yandex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import jsmarty.core.common.apis.translator.common.model.TranslatorResponse;
import jsmarty.core.common.http.HttpRequestHandler;
import jsmarty.core.common.http.HttpResponse;
import jsmarty.core.common.logging.DefaultLogger;
import jsmarty.core.common.logging.core.Logger;
import jsmarty.core.common.utils.StringsUtils;
import jsmarty.core.sdo.Api;

/**
 * Uses Yandex.Translator API for translation Needed API key.
 *
 * @author Dima
 */

public class YandexTranslatorHelper {

	private final String API_KEY;

	private String API_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate";
	private String SEPARATOR = "~";
	private String encoding = "UTF-8";

	HttpRequestHandler httpRequestHandler;

	private static final Logger LOGGER = DefaultLogger.getInstance();

	public YandexTranslatorHelper(String API_URL,String API_KEY, String separator, String encoding) {
		this.API_KEY = API_KEY;

		if(API_URL != null){
			this.API_URL = API_URL;
		}
		if(encoding != null){
			this.encoding = encoding;
		}
		if(separator != null){
			this.SEPARATOR = separator;
		}

		//
		httpRequestHandler = new HttpRequestHandler();
	}


	/**
	 * 
	 * @param text
	 * @param langFrom
	 * @param langTo
	 * @return
	 * @throws Exception
	 */
	public TranslatorResponse translate(String text, String langFrom, String langTo) {
		TranslatorResponse translatorResponse = new TranslatorResponse();
		Api api = new Api();

		Map<String, String> parameters = new HashMap<>();
		parameters.put("key", API_KEY);
		parameters.put("lang", langFrom);
		parameters.put("lang", langTo);
		parameters.put("text", text);

		HttpResponse response = null;
		try {
			response = httpRequestHandler.executePost(API_URL, null, parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO handle null response case
		JSONObject jsonResponse = new JSONObject(response.getBody());
		int responseCode = jsonResponse.getInt("code");

		if (responseCode == 200) {
			Map<String, String> translation = new HashMap<>();
			translation.put(text,  jsonResponse.get("text").toString().replace("[", "").replace("]", "").replace("\"", ""));
			translatorResponse.setTranslation(translation);

			api.setName("yandex");
			api.setSuccess(true);
			translatorResponse.setApi(api);
		} else if (responseCode == 404) {
			api.setName("yandex");
			api.setMessage("Daily limit of translations has been reached");
			api.setSuccess(false);
			LOGGER.warn("Daily limit of translations has been reached");
			//			throw new Exception("[" + responseCode + "] - Daily limit of translations has been reached");
		} else if (responseCode == 401 | responseCode == 402) {
			api.setName("yandex");
			api.setMessage("Yandex.Translator API key is invalid or blocked");
			api.setSuccess(false);
			LOGGER.warn("Yandex.Translator API key is invalid or blocked");
			//			throw new Exception("[" + responseCode + "] - Yandex.Translator API key is invalid or blocked");
		} else if (responseCode == 413) {
			api.setName("yandex");
			api.setMessage("Maximal text size has been exceeded");
			api.setSuccess(false);
			LOGGER.warn("Maximal text size has been exceeded");
			//			throw new Exception("[" + responseCode + "] - Maximal text size has been exceeded");
		} else {
			api.setName("yandex");
			api.setMessage("Given text can not be translated by Yandex translator");
			api.setSuccess(false);
			LOGGER.warn("Given text can not be translated by Yandex translator");
			//			throw new Exception("[" + responseCode + "] - Given text can not be translated by Yandex translator");
		}
		translatorResponse.setApi(api);
		return translatorResponse;
	}

	/**
	 * 
	 * @param textsList
	 * @param langFrom
	 * @param langTo
	 * @return
	 * @throws Exception
	 */
	public TranslatorResponse translate(List<String> textsList, String langFrom, String langTo) {
		TranslatorResponse translatorResponse;
		Map<String, String> resultMap = new HashMap<>();

		String stringToTrans = StringsUtils.joinList(textsList, SEPARATOR);

		translatorResponse = translate(stringToTrans, langFrom, langTo);

		List<String> translatedList = StringsUtils.split(translatorResponse.getTranslation().get(stringToTrans), SEPARATOR);
		//
		for (int i = 0; i < textsList.size(); i++) {
			resultMap.put(textsList.get(i), translatedList.get(i));
		}

		translatorResponse.setTranslation(resultMap);
		return translatorResponse;
	}
}
