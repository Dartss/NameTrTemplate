package common.apis.translator.phonetic;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jsmarty.core.common.apis.translator.common.model.TranslatorResponse;
import jsmarty.core.common.apis.translator.phonetic.model.Command;
import jsmarty.core.common.apis.translator.phonetic.model.Data;
import jsmarty.core.common.apis.translator.phonetic.model.Entity;
import jsmarty.core.common.apis.translator.phonetic.model.PhoneticRequest;
import jsmarty.core.common.apis.translator.phonetic.model.PhoneticResponse;
import jsmarty.core.common.json.JsonHandler;
import jsmarty.core.common.json.impl.JsonHandlerImpl;
import jsmarty.core.common.sockets.client.SocketClient;
import jsmarty.core.common.utils.UuidUtils;
import jsmarty.core.sdo.Api;

/**
 * Responsible for communication with Phonetic API via socket.
 * @author vit
 *
 */

public class PhoneticTranslatorHelper {

	private final static String COMMAND_NAME = "gettransliteration";
	private final static String ARG_FROM_LANG_NAME = "from_lang";
	private final static String ARG_TO_LANG_NAME = "to_lang";
	
	private static final String DEFAULT_API_HOST = "localhost";
	private static final int DEFAULT_API_PORT = 9000;

	private String host;
	private int port;
	private SocketClient socketClient;
	private JsonHandler jsonHandler;

	public PhoneticTranslatorHelper() throws UnknownHostException, IOException{
		this.host = DEFAULT_API_HOST;
		this.port = DEFAULT_API_PORT;
		init();
	}

	public PhoneticTranslatorHelper(String host, int port) throws UnknownHostException, IOException{
		this.host = host;
		this.port = port;
		init();
	}

	private void init() throws UnknownHostException, IOException{
		this.socketClient = new SocketClient(this.host, this.port);
		this.jsonHandler = new JsonHandlerImpl();
	}

	/**
	 * Returns the transliteration of given words in @param request
	 * @param request
	 * @return PhoneticResponse on success, null - otherwise.
	 */
	public TranslatorResponse getTransliteration(List<String> textList, String langFrom, String langTo){
		TranslatorResponse translatorResponse = null;
		PhoneticResponse phoneticResponse = null;
		
		PhoneticRequest request = prepareRequest(textList, langFrom, langTo);

		String responseStr = null;
		try {
			responseStr = this.socketClient.sendRequest(this.jsonHandler.serialize(request));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(responseStr != null){
			Api api = new Api();
			api.setName("phonetic translator");
			api.setSuccess(true);
			
			/*
			 * 
			 * TODO
			 * the response model should be changed from python side (phonetic server)
			 * to become a list of json objects, which simplify the normalize the retrieval of translation (by using only deserialize...)
			 * 
			 */
			phoneticResponse = (PhoneticResponse) jsonHandler.deserialize(responseStr, new PhoneticResponse());
			translatorResponse = new TranslatorResponse();
			Map<String, String> translations = new HashMap();
			Entity entity;
			for (Entry entry : phoneticResponse.getEntities().entrySet()){
				entity = (Entity) entry.getValue();
				translations.put((String) entry.getKey(), entity.getEng());
			}
			translatorResponse.setTranslation(translations);
			translatorResponse.setId(request.getId());
			translatorResponse.setApi(api);
		}

		return translatorResponse;
	}

	private PhoneticRequest prepareRequest(List<String> textList, String langFrom, String langTo){
		Map<String, String> args = new HashMap<String, String>();
		args.put(ARG_FROM_LANG_NAME, langFrom);
		args.put(ARG_TO_LANG_NAME, langTo);

		Command command = new Command();
		command.setName(COMMAND_NAME);
		command.setArgs(args);

		Data data = new Data();
		data.setEntities(textList);

		PhoneticRequest request = new PhoneticRequest();
		request.setCommand(command);
		request.setData(data);
		request.setId(UuidUtils.generateType1().toString());

		return request;
	}
	
}