package common.queuer.redis.cache;

public class TextCacheObject {

	private String body;
	private Long id;

	/**
	 * 
	 * @param body
	 * @param id
	 */
	public TextCacheObject(String body, Long id) {
		super();
		this.body = body;
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}