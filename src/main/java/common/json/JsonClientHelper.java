package common.json;

public interface JsonClientHelper{
	String serialize(Object object) throws Exception;
	Object deserialize(String string, Object object) throws Exception;
}
