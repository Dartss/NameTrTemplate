package common.json.jackson;

import java.io.IOException;
import java.sql.Timestamp;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * Custom deserializer date to timestamp if we need to use custom deserializer we
 * most use @JsonDeserialize.
 * 
 * @JsonDeserialize is used to indicate the use of a custom deserializer.
 * for example :   @JsonDeserialize(using = JacksonCustomTimestampDeserializer.class)
 * 
 * @author Tolik
 *
 */
public class JacksonCustomTimestampDeserializer extends JsonDeserializer<Timestamp> {

	@Override
	public Timestamp deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
		JsonToken jsonToken = jsonParser.getCurrentToken();
		if (jsonToken == JsonToken.VALUE_STRING) {
			String string = jsonParser.getText().trim();
			return Timestamp.valueOf(string);
		}
		if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
			return new Timestamp(jsonParser.getLongValue());
		}
		return null;
	}
}