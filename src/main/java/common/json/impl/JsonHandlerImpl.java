package common.json.impl;

import common.json.JsonClientHelper;
import common.json.JsonHandler;
import common.json.common.JsonClient;
import common.json.common.JsonConstants;
import common.json.gson.impl.GsonHelper;
import common.json.jackson.impl.JacksonHelper;
import common.utils.bean.BeanUtils;

import java.util.logging.Logger;


/**
 * This handler offers Json service and is created to be used in JSmarty; So
 * whenever we decide to switch to another Java Json Client we will change it
 * here; It is very important to use the same Json service in JSmarty to avoid
 * incompatibility issues and errors between different components;
 * 
 * @author Tolik
 *
 */
public class JsonHandlerImpl implements JsonHandler
{


	private String dateFormat;
	private JsonClientHelper jsonClientHelper;
	private JsonClient jsonClient;

	/* 
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!TODO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * 
	 * Momentarily and in order to avoid wasting time to check for object size to choose between GSON or JACKSON (getJsonClient() l.92)
	 * And as long as it is so rare to have an object with a size greater than 1 MB - We will skip the check and use GSON as default
	 * this skipCheck boolean could be set as false using the public setter to apply the basic implemented logic
	 */
	private boolean skipCheck = true;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * Default constructor 
	 * Use default date format
	 */
	public JsonHandlerImpl() {
		this.dateFormat = JsonConstants.DEFAULT_DATE_FORMAT;
		this.jsonClient = JsonClient.UNDEFINED;
	}

	/**
	 * 
	 * @param dateFormat
	 */
	public JsonHandlerImpl(String dateFormat) {
		//		If dateFormat param is null or empty we load defaultDateFormat 
		if (dateFormat == null || dateFormat.isEmpty()) {
			this.dateFormat = JsonConstants.DEFAULT_DATE_FORMAT;
		} else {
			this.dateFormat = dateFormat;
		}
		this.jsonClient = JsonClient.UNDEFINED;
	}

	/**
	 * Desginate which json client to use
	 * Currently the choice is only between GSON (the best for small sized objects) or JACKXON (the best for big sized objects)
	 * @param dateFormat
	 * @param jsonClient
	 */
	public JsonHandlerImpl(String dateFormat, JsonClient jsonClient) {
		if(dateFormat != null || !dateFormat.isEmpty()) {
			dateFormat = JsonConstants.DEFAULT_DATE_FORMAT;
		}else{
			this.dateFormat = dateFormat;
		}

		if(jsonClient.equals(JsonClient.GSON)) {
			this.jsonClientHelper = new GsonHelper(dateFormat);
		}else if(jsonClient.equals(JsonClient.JACKSON)){
			this.jsonClientHelper = new JacksonHelper(dateFormat);
		}else{
			this.jsonClient = JsonClient.UNDEFINED;
		}
	}

	/**
	 * Determine which library to use and return it. If
	 * the library is not selected then determine the size of the object and
	 * return the suitable library.
	 * 
	 * @param object
	 * @return jsonClientHelper
	 */
	private JsonClientHelper getJsonClient(Object object) {
		if(skipCheck){
			if (this.jsonClientHelper == null) {
				this.jsonClientHelper = new GsonHelper(this.dateFormat);
			}
			return this.jsonClientHelper;
		}else{
			if (this.jsonClient.equals(JsonClient.GSON)) {
				if (this.jsonClientHelper == null) {
					this.jsonClientHelper = new GsonHelper(this.dateFormat);
				}
				return this.jsonClientHelper;
			}else if(this.jsonClient.equals(JsonClient.JACKSON)) {
				if (this.jsonClientHelper == null) {
					this.jsonClientHelper = new JacksonHelper(this.dateFormat);
				}
				return this.jsonClientHelper;
			}else if (object != null) {
				if (BeanUtils.getObjectSize(object) > JsonConstants.MIN_BIG_SIZE_LEVEL) {
					this.jsonClientHelper = new JacksonHelper(this.dateFormat);
				} else {
					this.jsonClientHelper = new GsonHelper(this.dateFormat);
				}
				return this.jsonClientHelper;
			}
		}
		return null;
	}

	/**
	 * Serialize java object to json and return string json
	 * 
	 * @param object
	 * @return string
	 */
	@Override
	public String serialize(Object object) {
		try{
			return getJsonClient(object).serialize(object);
		}catch(Exception ex){
			LOGGER.info("COULD NOT SERIALIZE OBJECT");
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Deserialize a string to a Java object
	 * This method should take a specimen object in parameter so it refers to
	 * its class
	 * 
	 * @param string
	 * @param object
	 * @return object
	 * 
	 */
	@Override
	public Object deserialize(String string, Object object) {
		try{
			return getJsonClient(object).deserialize(string, object);
		}catch(Exception ex){
			LOGGER.info("COULD NOT DESERIALIZE OBJECT : "+ string);
			ex.printStackTrace();
			return null;
		}

	}

	public void setSkipCheck(boolean skipCheck) {
		this.skipCheck = skipCheck;
	}

}