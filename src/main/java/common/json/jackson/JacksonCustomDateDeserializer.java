package common.json.jackson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * Custom deserializer timestamp to date if we need use custom deserializer we
 * most use @JsonDeserialize.
 * 
 * @JsonDeserialize is used to indicate the use of a custom deserializer.
 * for example :   @JsonDeserialize(using = JacksonCustomDateDeserializer.class)
 * @author Tolik
 *
 */
public class JacksonCustomDateDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser jsonparser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		long timeAsLong = jsonparser.getLongValue();
		Date date = new Date(timeAsLong * 1000);
		return date;
	}

}