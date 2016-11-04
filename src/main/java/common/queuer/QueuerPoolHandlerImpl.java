package common.queuer;

import java.util.logging.Logger;

import common.properties.GlobalProperties;
import common.queuer.redis.JedisHelper;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

/**
 * !!!!!!!!!!!!!!!!!!NOTICE!!!!!!!!!!!!!!!!!!!
 * Jedis pool does not support multi threading
 * So in order to share the same pool - we are setting a static synchronized access to the create and get methods in jedisHelper...
 * 
 */


/**
 * This class is designed for managing JedisPool
 * It has a static map of JedisPool objects as value and 'host:post' as a key
 * 
 * 
 * @author vit
 *
 */

public class QueuerPoolHandlerImpl implements QueuerPoolHandler{

	private static JedisPoolConfig poolDefaultConfig;

	private String host;
	private int port;
	private JedisHelper jedisHelper;

	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public QueuerPoolHandlerImpl(String host, int port) {
		this.host = host;
		this.port = port;
		this.jedisHelper = new JedisHelper(host, port, getDefaultConfigs());

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				LOGGER.info("shutdownhook: Closing connections to redis ");
				destroyAllPools();
				LOGGER.info("shutdownhook: Closed connections to redis ");
			}
		});
	}

	public QueuerPoolHandlerImpl(String host, int port, QueuerPoolConfig config) {
		this.host = host;
		this.port = port;
		this.jedisHelper = new JedisHelper(host, port, updateConfig(config));
	}

	@Override
	public void reinitPool(QueuerPoolConfig config) {
		this.jedisHelper.reinitPool(updateConfig(config));
	};

	@Override
	public boolean destroyPool() {
		return this.destroyPool(this.host, this.port);
	}

	@Override
	public boolean closePool() {
		this.jedisHelper.closePool();
		return true;
	};

	@Override
	public boolean destroyPool(String host, int port) {
		return this.jedisHelper.destroyPool(host, port);
	}

	@Override
	public boolean destroyAllPools() {
		return jedisHelper.destroyAllPools();
	}

	/**
	 * Loads global configurations from properties files
	 * 
	 * @return
	 */
	private static JedisPoolConfig getDefaultConfigs() {

		if (poolDefaultConfig == null) {
			new GlobalProperties();
			poolDefaultConfig = new JedisPoolConfig();
			poolDefaultConfig.setMaxIdle(GlobalProperties.getREDIS_POOL_MAX_IDLE());
			poolDefaultConfig.setMaxTotal(GlobalProperties.getREDIS_POOL_MAX_TOTAL());
			poolDefaultConfig.setMaxWaitMillis(GlobalProperties.getREDIS_POOL_MAX_WAIT_MILLIS());
			poolDefaultConfig.setMinIdle(GlobalProperties.getREDIS_POOL_MIN_IDLE());
			poolDefaultConfig.setMinEvictableIdleTimeMillis(GlobalProperties.getMIN_EVICTABLE_IDLE_TIME_MILLIS());
			poolDefaultConfig.setTimeBetweenEvictionRunsMillis(GlobalProperties.getTIME_BETWEEN_EVICTION_RUNS_MILLIS());
			poolDefaultConfig.setTestOnBorrow(true);
		}

		return poolDefaultConfig;
	}

	/**
	 * Converts (!) QueuerPoolConfig to JedisPoolConfig
	 * @param config
	 * @return JedisPoolConfig
	 */
	private static JedisPoolConfig updateConfig(QueuerPoolConfig config) {
		JedisPoolConfig poolConfig = new JedisPoolConfig();

		poolConfig.setMaxIdle(config.getMaxIdle());
		poolConfig.setMaxTotal(config.getMaxTotal());
		poolConfig.setMinIdle(config.getMinIdle());
		poolConfig.setMaxWaitMillis(config.getMaxWaitMillis());

		poolConfig.getJmxEnabled();

		return poolConfig;

	}

	/**
	 * prints pools info. Uses for debugging only
	 */
	@Override
	public String spop(String key) {
	    return this.jedisHelper.spop(key);
	}

	@Override
	public void printInfo(boolean withDetails) {
		this.jedisHelper.printInfo();
	}

    @Override public Long sadd(String key, String value)
    {
	this.jedisHelper.resetTrialsCounter();
	return this.jedisHelper.sadd(key, value);
    }

    @Override
	public String set(String key, String value) {
		this.jedisHelper.resetTrialsCounter();
		return this.jedisHelper.set(key, value);
	}

	@Override
	public String get(String key) {
		this.jedisHelper.resetTrialsCounter();
		return this.jedisHelper.get(key);
	}

	@Override
	public Long lpush(String key, String value) {
		this.jedisHelper.resetTrialsCounter();
		return this.jedisHelper.lpush(key, value);
	}

	@Override
	public Long rpush(String key, String value) {
		this.jedisHelper.resetTrialsCounter();
		return this.jedisHelper.rpush(key, value);
	}

	@Override
	public String lpop(String key) {
		this.jedisHelper.resetTrialsCounter();
		return this.jedisHelper.lpop(key);
	}

	@Override
	public String rpop(String key) {
		this.jedisHelper.resetTrialsCounter();
		return this.jedisHelper.rpop(key);
	}

	@Override
	public Long lrem(String key, int count, String value) {
		this.jedisHelper.resetTrialsCounter();
		return this.jedisHelper.lrem(key, count, value);
	}

	@Override
	public Long incr(String key) {
		this.jedisHelper.resetTrialsCounter();
		return this.jedisHelper.incr(key);
	}

	@Override
	public Long decr(String key) {
		this.jedisHelper.resetTrialsCounter();
		return this.jedisHelper.decr(key);
	}

	@Override
	public Long llen(String key) {
		this.jedisHelper.resetTrialsCounter();
		return this.jedisHelper.llen(key);
	}

	@Override
	public Long expire(String key, int seconds) {
		this.jedisHelper.resetTrialsCounter();
		return this.jedisHelper.expire(key, seconds);
	}

	@Override
	public Boolean exists(String key) {
		this.jedisHelper.resetTrialsCounter();
		return this.jedisHelper.exists(key);
	}

	@Override
	public Long persist(String key) {
		this.jedisHelper.resetTrialsCounter();
		return this.jedisHelper.persist(key);
	}

	@Override
	public Long incrBy(String key, long integer) {
		this.jedisHelper.resetTrialsCounter();
		return this.jedisHelper.incrBy(key, integer);
	}

	@Override
	public Long decrBy(String key, long integer) {
		this.jedisHelper.resetTrialsCounter();
		return this.jedisHelper.decrBy(key, integer);
	}

    @Override public Pipeline getJedisPipeline()
    {
	return this.jedisHelper.getJedisPipeline();
    }
}