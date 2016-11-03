package common.json;

public interface JsonHandler {
	String serialize(Object object);
	Object deserialize(String string, Object object);
}
