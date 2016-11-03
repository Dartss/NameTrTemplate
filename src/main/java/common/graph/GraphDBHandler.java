package common.graph;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.neo4j.driver.v1.StatementResult;

import jsmarty.core.common.properties.core.DefaultProperties;

/**
 * GraphDBHandler for NeoHelper
 * 
 * @author yev
 *
 */
public class GraphDBHandler {

	private NeoHelper neoHelper;
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private String host;
	private String port;
	private String user;
	private String password;

	public GraphDBHandler() {
		this.host = DefaultProperties.getDEFAULT_GRAPH_HOST();
		this.port = String.valueOf(DefaultProperties.getDEFAULT_GRAPH_PORT());
		this.user = DefaultProperties.getDEFAULT_GRAPH_USERNAME();
		this.password = DefaultProperties.getDEFAULT_GRAPH_PASSWORD();

		this.neoHelper = new NeoHelper(host, user, password);
		attachShutDownHook();
	}

	public GraphDBHandler(String host, String user, String password) {
		this.initHelper(host, null, user, password);
	}

	public GraphDBHandler(String host, String port, String user, String password) {
		this.initHelper(host, port, user, password);
	}

	private void initHelper(String host, String port, String user, String password) {
		this.neoHelper = new NeoHelper(host, port, user, password);
		attachShutDownHook();
	}

	private void attachShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				LOGGER.info("We entered the shutdownhook inside the GraphDBHandler");
				disconnect();

			}
		});
	}

	public void disconnect() {
		neoHelper.destroy();
	}

	public void executeBatch(List<String> cypher) {
		neoHelper.executeBatch(cypher);
	}

	public StatementResult query(String cypher, Map<String, Object> parameters) {
		return neoHelper.query(cypher, parameters);
	}

	public StatementResult query(String cypher) {
		return neoHelper.query(cypher);
	}

	/**
	 * This method return a list of uuids according to input query Notice: query
	 * must return NODE
	 * 
	 * @param query
	 * @return
	 */
	public List<String> queryUuids(String query) {
		return neoHelper.queryUuids(query);
	}

	public boolean delete(String query) {
		return neoHelper.delete(query);
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

}