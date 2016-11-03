package common.logging.debugger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class SmartyLoggerProperties {

	private static String LOGS_QUEUE_NAME;
	private static String LOGS_QUEUE_SERVER_HOST;
	private static int LOGS_QUEUE_SERVER_PORT;
	private static int PRINT_FREQUENCY_TIME;
	private static Properties properties;

	public static void init(String[] args) {
		properties = new Properties();
		String propFileName = "JSmarty.properties";
		String propFilePathOnServer = "/usr/local/etc/smarty/JSmarty.properties";
		String propFileBuildpath = "src/main/resources/META-INF/JSmarty.properties";

		// looking for application.properties file
		try {
			properties.load(new FileInputStream(propFilePathOnServer));
			System.out.println("config loaded from: "+ propFilePathOnServer);
		} catch (Exception ex) {
			try { // for dev environment only
				properties.load(new FileInputStream(propFileBuildpath));
				System.out.println("config loaded from (use in developpement environment only): "+ propFileBuildpath);
			} catch (FileNotFoundException e1) {
				System.out.println("file: " + propFileBuildpath+ " not found");
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		// setting up properties from file
		if (properties != null) {
			LOGS_QUEUE_SERVER_HOST = properties.getProperty("smarty.logger.queue.host");
			LOGS_QUEUE_SERVER_PORT = Integer.parseInt(properties.getProperty("smarty.logger.queue.port"));
			LOGS_QUEUE_NAME = properties.getProperty("smarty.logger.queue.name");
			PRINT_FREQUENCY_TIME = Integer.parseInt(properties.getProperty("smarty.logger.print.frequency.time"));
		}
	}

	public static int getPRINT_FREQUENCY_TIME() {
		return PRINT_FREQUENCY_TIME;
	}

	public static String getLOGS_QUEUE_NAME() {
		return LOGS_QUEUE_NAME;
	}
	public static void setLOGS_QUEUE_NAME(String lOGS_QUEUE_NAME) {
		LOGS_QUEUE_NAME = lOGS_QUEUE_NAME;
	}
	public static String getLOGS_QUEUE_SERVER_HOST() {
		return LOGS_QUEUE_SERVER_HOST;
	}
	public static void setLOGS_QUEUE_SERVER_HOST(String lOGS_QUEUE_SERVER_HOST) {
		LOGS_QUEUE_SERVER_HOST = lOGS_QUEUE_SERVER_HOST;
	}
	public static int getLOGS_QUEUE_SERVER_PORT() {
		return LOGS_QUEUE_SERVER_PORT;
	}
	public static void setLOGS_QUEUE_SERVER_PORT(int lOGS_QUEUE_SERVER_PORT) {
		LOGS_QUEUE_SERVER_PORT = lOGS_QUEUE_SERVER_PORT;
	}

}