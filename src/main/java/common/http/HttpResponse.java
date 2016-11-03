package common.http;

import java.io.InputStream;

import org.json.JSONObject;

/**
 * Http Response Model Object 
 * 
 * @author rud
 *
 */
public class HttpResponse {

	private int statusCode;
	private String message;
	private String body;
	private JSONObject json;
	private InputStream inputStream;

	public HttpResponse(){

	}

	public HttpResponse(int statusCode, String message, String body) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.body=body;
	}
	
	public HttpResponse(int statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}

	public int getStatusCode() {
		return this.statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public JSONObject getJsonBody() {
		return new JSONObject(this.body);
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}