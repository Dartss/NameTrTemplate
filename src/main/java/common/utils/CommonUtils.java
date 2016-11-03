package common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CommonUtils {

	public static Map<String, Object> jsonToMap(JSONObject object) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();

		Iterator<String> keysItr = object.keys();
		while(keysItr.hasNext()) {
			String key = keysItr.next();
			Object value = object.get(key);

			if(value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}

			else if(value instanceof JSONObject) {
				value = jsonToMap((JSONObject) value);
			}
			map.put(key, value);
		}
		return map;
	}

	public static List<Object> toList(JSONArray array) throws JSONException {
		List<Object> list = new ArrayList<Object>();
		for(int i = 0; i < array.length(); i++) {
			Object value = array.get(i);
			if(value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}

			else if(value instanceof JSONObject) {
				value = jsonToMap((JSONObject) value);
			}
			list.add(value);
		}
		return list;
	}

	/**
	 * NOTICE
	 * Only objects with string value will be appended to the new map
	 */
	public static Map<String,String> toMapString(Map<String,Object> mapObject){
		Map<String,String> mapString =new HashMap<String,String>();
		for (Map.Entry<String, Object> entry : mapObject.entrySet()) {
			if(entry.getValue() instanceof String){
				mapString.put(entry.getKey(), (String) entry.getValue());
			}
		}
		return mapString;
	}
	
}