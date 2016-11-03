package common.properties.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PropertiesMap {
	
	private Map<String, HashMap<String, String>> map;

	public PropertiesMap() {
		this.map = new HashMap<String, HashMap<String, String>>(1);
	}

	public int size() {
		return map.size();
	}

	/**
	 * Returns an iterator on the map as an EntrySet
	 * 
	 * @return
	 */
	public Iterator iterator() {
		return map.entrySet().iterator();
	}

	public Map<String, HashMap<String, String>> getMap() {
		return map;
	}

	public void setMap(Map<String, HashMap<String, String>> map) {
		this.map = map;
	}

	/**
	 * returns the value of the specified field at the specified key
	 */
	public String getFieldValue(String key, String field) {
		return map.get(key).get(field);
	}
	
}