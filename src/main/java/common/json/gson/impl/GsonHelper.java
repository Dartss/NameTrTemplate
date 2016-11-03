package common.json.gson.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.json.JsonClientHelper;
import common.json.gson.GsonTimeHandler;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class GsonHelper implements JsonClientHelper
{

	private Gson gson;
	private GsonTimeHandler gsonTimeHandler;
	private GsonBuilder gsonBuilder;

	public GsonHelper(String dateFormat) {
		init(dateFormat);
	}

	private void init(String dateFormatString) {
		DateFormat dateFormat = new SimpleDateFormat(dateFormatString);
		this.gsonTimeHandler = new GsonTimeHandler(dateFormat);

		this.gsonBuilder = new GsonBuilder();
		this.gsonBuilder.setDateFormat(dateFormatString);
		this.gsonBuilder.registerTypeAdapter(Timestamp.class, this.gsonTimeHandler.jsonTimestampDeserializer);
		this.gsonBuilder.registerTypeAdapter(Timestamp.class, this.gsonTimeHandler.jsonTimestampSerializer);
		this.gsonBuilder.registerTypeAdapter(Date.class, this.gsonTimeHandler.jsonDateDeserializer);
		this.gsonBuilder.registerTypeAdapter(Date.class, this.gsonTimeHandler.jsonDateSerializer);
		this.gson = this.gsonBuilder.create();
	}

	@Override
	public String serialize(Object object) throws Exception{
		try{
			return this.gson.toJson(object);
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	public Object deserialize(String string, Object object) throws Exception{
		try{
			return this.gson.fromJson(string, object.getClass());
		} catch (Exception ex) {
			throw ex;
		}
	}

}