package common.elastic.model;

import java.io.Serializable;

/**
 * Class-type for ElasticHandler
 * uses as argument for methods
 * @author Vit
 *
 */

public class ElasticDocumentVO implements Serializable{

	private String index;
	private String type;
	private String id;
	private String jsonSource;

	public ElasticDocumentVO(){
	}

	public ElasticDocumentVO(String index, String type, String id){
		this.index = index;
		this.type = type;
		this.id = id;
	}

	public ElasticDocumentVO(String index, String type, String id, String jsonSource){
		this.index = index;
		this.type = type;
		this.id = id;
		this.jsonSource = jsonSource;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setJsonSource(String jsonSource) {
		this.jsonSource = jsonSource;
	}

	public String getId() {
		return id;
	}

	public String getIndex() {
		return index;
	}

	public String getType() {
		return type;
	}

	public String getJsonSource() {
		return jsonSource;
	}

}