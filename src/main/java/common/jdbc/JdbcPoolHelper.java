package common.jdbc;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;


/**
 * JDBCPoolHelper creates and manages connection pool
 * Using BoneCP API
 * 
 * @author Dima
 */
public class JdbcPoolHelper {

	private String url;
	private String dbDriver;
	private String username;
	private String password;

	private long lifeTime;
	private int maximumConnectionCount;

	private BoneCP pool = null;
	private Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public JdbcPoolHelper(JdbcConfig config) throws SQLException {
		this.url = config.getUrl();
		this.dbDriver = config.getDbDriver();
		this.username = config.getUsername();
		this.password = config.getPassword();
		this.lifeTime = config.getLifeTime();
		this.maximumConnectionCount = config.getMaximumConnectionCount();
		generatePool();
	}

	/**
	 * Generates and configures connection pool.
	 * @throws SQLException
	 */
	private synchronized void generatePool() throws SQLException {
		try {
			// load the database driver (make sure it exists in classpath!)
			Class.forName(this.dbDriver);
		} catch (Exception ex) {
			LOGGER.info("Invalid driver"+ this.dbDriver + " - " + ex);
		}
		
		BoneCPConfig config = new BoneCPConfig();
		config.setJdbcUrl(url);
		config.setUsername(username);
		config.setPassword(password);
		config.setMaxConnectionsPerPartition(maximumConnectionCount);
		config.setMaxConnectionAge(lifeTime, TimeUnit.MILLISECONDS);

		try{
			this.pool = new BoneCP(config);
		}catch(Exception ex){
			LOGGER.info("Error upon trying to create a jdbc pool (BOnceCP) - " + url);
			ex.printStackTrace();
		}
	}

	/**
	 * @return returns a connection if limit count is not reached,
	 * otherwise the request will wait until a free connection appears
	 */
	public synchronized Connection getConnection() {
		try {
			return this.pool.getConnection();
		} catch (SQLException e) {
			LOGGER.info("Geting connection failed: "+ e);
		}
		return null;
	}

	/**
	 * Close connection pool
	 */
	public synchronized void closeConnectionPool() {
		this.pool.shutdown();
		this.pool.close();
	}

	/**
	 * @return limit of connections that have been set
	 */
	public int getTotalConnectionsCount() {
		return this.pool.getTotalCreatedConnections();
	}

	/**
	 * @return available connections count
	 */
	public int getAvailableConnectionsCount() {
		return this.pool.getTotalFree();
	}

	/**
	 * @return count of connections that are in use
	 */
	public int getActiveConnectionsCount(){
		return this.pool.getTotalLeased();
	}

}