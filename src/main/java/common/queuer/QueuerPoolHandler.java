package common.queuer;

import redis.clients.jedis.Pipeline;

public interface QueuerPoolHandler {

	/**
	 * Reinit pool with new configurations
	 * @return
	 */
	public void reinitPool(QueuerPoolConfig config);

	/**
	 * Close current pool
	 * @return true if pool was destroyed, false - otherwise
	 */
	public boolean closePool();

	/**
	 * Destroy pool with key 'host:port'
	 * @param host redis host
	 * @param port redis port
	 * @return true if pool was destroyed, false - otherwise
	 */
	public boolean destroyPool(String host, int port);

	/**
	 * Destroy current pool
	 * @return true if pool was destroyed, false - otherwise
	 */
	public boolean destroyPool();

	/**
	 * Destroy all pools
	 * @return true if any pool was destroyed, false - otherwise
	 */
	public boolean destroyAllPools();

	/**
	 * prints info about all pools that are managed by this QueuerPoolHandler
	 * @param withDetails if true prints info about each pool
	 */
	public void printInfo(boolean withDetails);

    /**
     * @bulanuiy
     * 
     * temporary method for using mass insert in redis
     * @return
     */
    public Pipeline getJedisPipeline();

	String set(final String key, final String value);
	String get(final String key);
	Long lpush(final String key, final String value);
	Long rpush(final String key, final String value);
	String lpop(final String key);
	String rpop(final String key);
	Long lrem(final String key, final int count, final String value);
	Long incr(final String key);
	Long decr(final String key);
	Long incrBy(final String key, long integer);
	Long decrBy(final String key, long integer);
	Long llen(final String key);
	Long expire(final String key, int seconds);
	Boolean exists(final String key);
	Long persist(final String key);
}
