package common.apis.social.helpers.facebook.url;

public class GraphUrlResponse {

	private OgObject og_object;
	private Share share;
	private String id;

	public GraphUrlResponse() {
		super();
	}

	public OgObject getOg_object() {
		return og_object;
	}
	public void setOg_object(OgObject og_object) {
		this.og_object = og_object;
	}
	public Share getShare() {
		return share;
	}
	public void setShare(Share share) {
		this.share = share;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}