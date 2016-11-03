package common.jdbc;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Handler for JDBC connections pool
 * @author Dima
 */
public class JdbcPoolHandler {

	public JdbcConfig jdbcConfig;
	private static HashMap<String, JdbcPoolHelper> pools;
	private static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public JdbcPoolHandler() {
		if(pools == null){
			LOGGER.info("Creating Jdbc pools map");
			pools = new HashMap<String, JdbcPoolHelper>();
		}

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				LOGGER.info("shutdownhook: JdbcPoolHandler");
				closePools();
			}
		});
	}

	/**
	 * Checks if pool was created if not, creates it
	 * @return returns connection if limit is not reached
	 * otherwise request waits until pool gets an available connection
	 */
	public static synchronized Connection getConnection(JdbcPoolHandler jdbcPoolHandler) {
		String poolKey = jdbcPoolHandler.jdbcConfig.getPoolKey();
		JdbcPoolHelper pool = getConnectionPoolByKey(poolKey);
		if (pool != null) {
			LOGGER.info("Getting Jdbc connection from an  already created pool - "+ poolKey);
			return pool.getConnection();
		}else{
			LOGGER.info("Creating new JDBC Pool  - "+ poolKey);
			pool = createNewPool(jdbcPoolHandler.jdbcConfig);
			LOGGER.info("Created new JDBC Pool - adding it to pools map - "+ poolKey);
			pools.put(poolKey, pool);
			LOGGER.info("Getting Jdbc connection from a newly created pool - "+ poolKey);
			return pool.getConnection();
		}
	}

	public static synchronized JdbcPoolHelper createNewPool(JdbcConfig jdbcConfig){
		try {
			return new JdbcPoolHelper(jdbcConfig);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Checks if helper with such pool was created
	 * @param poolKey URL of pool
	 * @return if helper was created returns it
	 *  if not returns null
	 */
	private static synchronized JdbcPoolHelper getConnectionPoolByKey(String poolKey) {
		if(pools != null && pools.containsKey(poolKey)) {
			return pools.get(poolKey);
		}
		return null;
	}

	/**
	 * Close connection pool
	 */
	public static synchronized void closeConnectionPool(String poolKey) {
		JdbcPoolHelper helper = getConnectionPoolByKey(poolKey);

		if (helper != null) {
			LOGGER.info("Closing JDBC pool - "+poolKey);
			helper.closeConnectionPool();
		}
	}

	/**
	 * close all pools
	 */
	public static synchronized void closePools(){
		if (pools != null){
			for (String key : pools.keySet()){
				closeConnectionPool(key);
			}
			pools = null;
			LOGGER.info("shutdownhook: Closed JDBC connection pools");
		}else{
			LOGGER.info("shutdownhook: attempted to Close JDBC connection pools - but were already closed");
		}
	}

	/**
	 * @return limit of connections that have been set
	 */
	public int getTotalConnectionsCount(String poolKey) {
		JdbcPoolHelper helper = getConnectionPoolByKey(poolKey);

		if (helper != null) {
			return helper.getTotalConnectionsCount();
		}
		return 0;
	}

	/**
	 * @return available connections count
	 */
	public int getAvailableConnectionsCount(String poolKey) {
		JdbcPoolHelper helper = getConnectionPoolByKey(poolKey);

		if (helper != null) {
			return helper.getAvailableConnectionsCount();
		}
		return 0;
	}

	/**
	 * @return count of connections that are in use
	 */
	public int getActiveConnectionsCount(String poolKey) {
		JdbcPoolHelper helper = getConnectionPoolByKey(poolKey);

		if (helper != null) {
			return helper.getActiveConnectionsCount();
		}
		return 0;
	}

	public void setJdbcConfig(JdbcConfig jdbcConfig) {
		this.jdbcConfig = jdbcConfig;
	}

}