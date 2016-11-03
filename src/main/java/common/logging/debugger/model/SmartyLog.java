package common.logging.debugger.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import jsmarty.core.common.queuer.QueuerPoolHandler;


/**
 * 
 * @author rud
 *
 */
public class SmartyLog implements Serializable{

	private static final long serialVersionUID = 1L;

	private Timestamp timestamp;
	private String source="JSmarty";
	private String message;
	private Map<Object, Object> fields = new HashMap<Object, Object>();

	private QueuerPoolHandler queuerPoolHandler;
	private String logsQueueName;

	private int printFrequencyTime=0;
	private static boolean print=false;
	public SmartyLog(){}

	public SmartyLog(String logsQueueName, QueuerPoolHandler queuerPoolHandler, Timestamp timestamp, String component, String message,int level, int printFrequencyTime) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.fields.put("level", String.valueOf(level));
		this.fields.put("component", component);

		this.logsQueueName=logsQueueName;
		this.queuerPoolHandler= queuerPoolHandler;
		this.printFrequencyTime=printFrequencyTime;
	}

	public SmartyLog addField(Object fieldName, Object fieldValue){
		fields.put("ctxt_"+fieldName, fieldValue);
		return this;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Map<Object, Object> getFields() {
		return fields;
	}

	public void setFields(Map<Object, Object> fields) {
		this.fields = fields;
	}

	public void print() {
		if (isPrint()){
			String log=transform(this);
			pushLog(log);
			/**
			 * TODO Investigate why the message is not being printed in the log files
			 */
			System.out.println("JSmarty-log :"+this.message);
		}
	}

	private String transform(SmartyLog smartyLog) {
		JSONObject json = new JSONObject(this);
		json.put("@timestamp", json.getString("timestamp"));
		json.put("@source", json.getString("source"));
		json.put("@fields", json.getJSONObject("fields"));
		json.put("@message", json.getString("message"));

		json.remove("timestamp");
		json.remove("source");
		json.remove("message");
		json.remove("fields");

		return json.toString();
	}

	private void pushLog(String log){
		queuerPoolHandler.lpush(logsQueueName, log);
	}

	/**
	 * As a quick fix, this method will let us print the logs in an adaptable frequency; 
	 * (i.e. if printFrequencyTime=5 ; this means that the logs will be printed only for the first 5 minutes of each hour....)
	 * @return
	 */
	private boolean isPrint() {
		LocalDateTime now = LocalDateTime.now();
		if(now.getMinute() <= printFrequencyTime){
			print=true;
		}else{
			print=false;
		}
		return print;
	}

	public void setPrint(boolean print) {
		this.print = print;
	}

}