package common.json.gson;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * 
 * Gson time type handler.
 * 
 * @author tolik
 */
public class GsonTimeHandler {

	private DateFormat dateFormat;

	public GsonTimeHandler(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	/**
	 * jsonTimestampSerializer
	 */
	public JsonSerializer<Timestamp> jsonTimestampSerializer = new JsonSerializer<Timestamp>() {
		@Override
		public JsonElement serialize(Timestamp timestamp, Type type, JsonSerializationContext jsonSerializationContext) {
			String dateFormatString = dateFormat.format(new Date(timestamp.getTime()));
			return new JsonPrimitive(dateFormatString);
		}
	};
	
	/**
	 * jsonDateSerializer
	 */
	public JsonSerializer<Date> jsonDateSerializer = new JsonSerializer<Date>() {
		@Override
		public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
			return src == null ? null : new JsonPrimitive(src.getTime());
		}
	};
	
	/**
	 * jsonDateDeserializer
	 */
	public JsonDeserializer<Date> jsonDateDeserializer = new JsonDeserializer<Date>() {
		@Override
		public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			try {
				long datetime = json.getAsJsonPrimitive().getAsLong();
				Date date = new Date(datetime);
				return date;
			} catch (NumberFormatException e) {
				String s = json.getAsString();
				Date date = null;
				try {
					date = dateFormat.parse(s);
				} catch (ParseException e1) {

				}
				return date;
			}

		}
	};
	
	/**
	 * jsonTimestampDeserializer
	 */
	public JsonDeserializer<Timestamp> jsonTimestampDeserializer = new JsonDeserializer<Timestamp>() {
		@Override
		public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)throws JsonParseException {
			if (!(json instanceof JsonPrimitive)) {
				throw new JsonParseException("The date should be a string value");
			}
			try {
				Date date = dateFormat.parse(json.getAsString());
				return new Timestamp(date.getTime());
			} catch (ParseException e) {
				throw new JsonParseException(e);
			}

		};
	};

}