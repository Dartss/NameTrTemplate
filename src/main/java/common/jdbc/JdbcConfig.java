package common.jdbc;

/**
 * Configure needed connections pool.
 *
 * @author Dima
 */
public class JdbcConfig {

	private String url;
	private String port;
	private String dbDriver;

	private String username;
	private String password;

	private int maximumConnectionCount;
	private long lifeTime;

	/**
	 * Configure connections pool with given maximum connections count and life time.
	 * @param url
	 * @param port
	 * @param dbDriver - Uses "mysql" By Default 
	 * @param user
	 * @param password
	 * @param maximumConnectionCount -  Maximal count of connections in the pool
	 * @param lifeTime - Connections maximal lifetime in milliseconds
	 */
	public JdbcConfig(String url, String port, String dbDriver, String user, String password, int maximumConnectionCount, long lifeTime) {
		this.url = url;
		this.port = port;

		if (dbDriver == null) {
			this.dbDriver = "com.mysql.jdbc.Driver";
		} else {
			this.dbDriver = dbDriver;
		}

		this.username = user;
		this.password = password;
		this.maximumConnectionCount = maximumConnectionCount;
		this.lifeTime = lifeTime;
	}

	/**
	 * 
	 * Takes default maximumConnectionCount and lifetime
	 * @param url
	 * @param port
	 * @param dbDriver
	 * @param user
	 * @param password
	 * 
	 */
	public JdbcConfig(String url, String port, String dbDriver, String user, String password) {
		this.url = url;
		this.port = port;

		if (dbDriver == null || dbDriver.isEmpty()) {
			this.dbDriver = "com.mysql.jdbc.Driver";
		} else {
			this.dbDriver = dbDriver;
		}

		this.username = user;
		this.password = password;
		this.maximumConnectionCount = JdbcConstants.DEFAULT_MAXIMUM_CONNECTION_COUNT;
		this.lifeTime = JdbcConstants.DEFAULT_CONNECTION_LIFE_TIME;
	}

	public JdbcConfig(String url, String dbDriver, String user, String password) {
		this.url = url;

		if (dbDriver == null || dbDriver.isEmpty()) {
			this.dbDriver = "com.mysql.jdbc.Driver";
		} else {
			this.dbDriver = dbDriver;
		}

		this.username = user;
		this.password = password;
		this.maximumConnectionCount = JdbcConstants.DEFAULT_MAXIMUM_CONNECTION_COUNT;
		this.lifeTime = JdbcConstants.DEFAULT_CONNECTION_LIFE_TIME;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getUrl() {
		return url;
	}

	public String getPort() {
		return port;
	}

	public int getMaximumConnectionCount() {
		return maximumConnectionCount;
	}

	public long getLifeTime() {
		return lifeTime;
	}

	public String getDbDriver() {
		return dbDriver;
	}

	/**
	 * TODO Concatenate host and port - 
	 * to unify all connections to same server into single pool
	 * @return
	 */
	public String getPoolKey() {
		return url;
	}
	
}