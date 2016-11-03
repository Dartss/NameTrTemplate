package common.json.jackson.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import common.json.JsonClientHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class JacksonHelper implements JsonClientHelper
{

	private ObjectMapper jackson;

	public JacksonHelper(String dateFormat) {
		init(dateFormat);
	}

	private void init(String dateFormatString) {
		this.jackson = new ObjectMapper();
		
		DateFormat dateFormat = new SimpleDateFormat(dateFormatString);
		this.jackson.setDateFormat(dateFormat);
		this.jackson.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
		this.jackson.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	@Override
	public Object deserialize(String string, Object object) throws Exception{
		try {
			return this.jackson.readValue(string, object.getClass());
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	public String serialize(Object object)  throws Exception{
		try {
			return this.jackson.writeValueAsString(object);
		} catch (Exception ex) {
			throw ex;
		}
	}

}
