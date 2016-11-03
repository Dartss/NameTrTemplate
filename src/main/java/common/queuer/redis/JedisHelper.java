package common.queuer.redis;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;

/**
 * This class is designed to communicate with the Jedis API build upon Jedis java library.
 * It uses JedisPool for getting connections.
 * 
 * 
 * @author vit
 *
 */

public class JedisHelper {

	private static Map<String, JedisPool> pools;

	private JedisPool jedisPool;
	private Jedis jedis;
	private String host;
	private int port;
	private JedisPoolConfig config;
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private int trials;
	private static int momentaryDebugCounter;

	public JedisHelper(String host, int port, JedisPoolConfig config) {
		this.host = host;
		this.port = port;
		this.config = config;
		this.jedisPool = getPool(host, port, config);
	}

	/**
	 * checks if pool with input 'host:port' exists.
	 * If not exists, created it, otherwise return existing
	 * @param host host
	 * @param port port
	 * @param config configurations
	 * @return
	 */
	private static synchronized JedisPool getPool(String host, int port, JedisPoolConfig config) {

		JedisPool pool = null;
		if(pools == null){
			pools = new HashMap<String, JedisPool>();
			LOGGER.info("JedisHelper: Creating pools map");
		}

		String key = generateJedisPoolKey(host, port);

		if(pools.containsKey(key)){
			LOGGER.info("JedisHelper: getting pool - key:"+key);
			pool = pools.get(key);
		} else {
			pool = createNewPool(host, port, config);
			LOGGER.info("JedisHelper: putting pool - key:"+key);
			pools.put(key, pool);
		}
		return pool;
	}

	private static synchronized JedisPool createNewPool(String host, int port, JedisPoolConfig config){
		LOGGER.info("creating new pool at: " + System.currentTimeMillis());
		return 	new JedisPool(config, host, port);

	}

		public synchronized void reinitPool(JedisPoolConfig config) {
			LOGGER.info("JedisHelper: reinit pool");
			this.destroyPool();
			jedisPool = getPool(host, port, config);
		}

	private static String generateJedisPoolKey(String host, int port) {
		return host + ":" + String.valueOf(port);
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public void closePool(){
		LOGGER.info("JedisHelper: closing pool");
		this.jedisPool.close();
	}

	private void closeJedis(Jedis jedis) {
		try{
			jedis.close();
		}catch(Exception ex){
			LOGGER.info("Exception while closing jedis : "+ex.getMessage());
		}
	}

	public boolean destroyPool(){
		return this.destroyPool(this.host, this.port);
	}

	public boolean destroyPool(String host, int port) {
		LOGGER.info("destroying pool ("+host +" - "+port+") at: " + System.currentTimeMillis());
		String key = generateJedisPoolKey(host, port);
		if(pools.containsKey(key)) {
			JedisPool pool = pools.get(key);
			pools.remove(key);
			pool.close();
			pool.destroy();
			return true;
		}

		return false;
	}

	public boolean destroyAllPools() {
		LOGGER.info("JedisHelper: destroying all pools");
		boolean destroyed = false;
		if(pools != null){
			LOGGER.info("JedisHelper: pools not null - destroying all pools");
			for (Map.Entry<String, JedisPool> entry : pools.entrySet()){
				LOGGER.info("JedisHelper: destroying pool "+entry.getKey());
				JedisPool pool = pools.remove(entry.getKey());
				pool.close();
				pool.destroy();
				LOGGER.info("JedisHelper: destroyed pool "+entry.getKey());
			}
			destroyed = true;
			pools = null;
		}else{
			destroyed = true;
		}
		return destroyed;
	}

	public void printInfo(){
		LOGGER.info("Pool '" + host + ":" + port + "':");
		LOGGER.info("\tisClosed = " + this.jedisPool.isClosed());
		LOGGER.info("\tnumActive = " + this.jedisPool.getNumActive());
		LOGGER.info("\tnumIdle = " + this.jedisPool.getNumIdle());
		LOGGER.info("\tnumWaiters = " + this.jedisPool.getNumWaiters());
	}

	private static synchronized Jedis getResource(JedisHelper jedisHelper) {
		Jedis jedis = null;
		if (jedisHelper.jedisPool == null || jedisHelper.jedisPool.isClosed()) {
			LOGGER.log(Level.SEVERE, "JedisHelper can't get resource from the JedisPool cause it is null or closed");
			//			jedisHelper.jedisPool = jedisHelper.getPool(jedisHelper.host, jedisHelper.port, jedisHelper.config);
		}else{
			jedis = jedisHelper.jedisPool.getResource();
		}

		momentaryDebugCounter++;
		if(!jedis.isConnected()){
			LOGGER.info("reconnection jedis re getting resource");
			return jedisHelper.getResource(jedisHelper);
		}
		return jedis;

	}

	/**
	 * Set the string value as value of the key. The string can't be longer than
	 * 1073741824 bytes (1 GB).
	 * <p>
	 * Time complexity: O(1)
	 * 
	 * @param key
	 * @param value
	 * @return Status code reply
	 */
	public String set(final String key, final String value) {

		try{
			Jedis jedis = this.getResource(this);
			String response = jedis.set(key, value);
			closeJedis(jedis);
			return response;
		}catch(JedisConnectionException | JedisDataException | ClassCastException ex){
			if(trials == 10){
				ex.printStackTrace();
				LOGGER.info("active: "+jedisPool.getNumActive() + " - idle:" +jedisPool.getNumIdle() + " -waiters: " + jedisPool.getNumWaiters());
				LOGGER.info("momentaryDebugCounter :" +momentaryDebugCounter);
				LOGGER.info("momentaryDebugCounter :" +momentaryDebugCounter);
				return null;
			}else{
				trials++;
				LOGGER.info(ex.getMessage());
				closeJedis(jedis);
				return set(key, value);
			}
		}
	}

	/**
	 * Get the value of the specified key. If the key does not exist null is
	 * returned. If the value stored at key is not a string an error is returned
	 * because GET can only handle string values.
	 * <p>
	 * Time complexity: O(1)
	 * 
	 * @param key
	 * @return Bulk reply
	 */
	public String get(final String key) {
		try{
			Jedis jedis = this.getResource(this);
			String response = jedis.get(key);
			closeJedis(jedis);
			return response;
		}catch(JedisConnectionException | JedisDataException | ClassCastException ex){
			if(trials == 10){
				ex.printStackTrace();
				LOGGER.info("active: "+jedisPool.getNumActive() + " - idle:" +jedisPool.getNumIdle() + " -waiters: " + jedisPool.getNumWaiters());
				LOGGER.info("momentaryDebugCounter :" +momentaryDebugCounter);
				LOGGER.info("time of exception: " + System.currentTimeMillis());
				return null;
			}else{
				trials++;
				LOGGER.info(ex.getMessage());
				/*
				 * 
				 * TODO CLOSE JEDIS IN FINALLY AFFTER CATCH !?....EVERYWEHERE 
				 * 
				 */
				closeJedis(jedis);
				return get(key);
			}
		}
	}

	/**
	 * Add the string value to the head (LPUSH) or tail (RPUSH) of the list
	 * stored at key. If the key does not exist an empty list is created just
	 * before the append operation. If the key exists but is not a List an error
	 * is returned.
	 * <p>
	 * Time complexity: O(1)
	 * 
	 * @param key
	 * @param strings
	 * @return Integer reply, specifically, the number of elements inside the
	 *         list after the push operation.
	 */
	public Long lpush(final String key, final String value) {
		try{
			Jedis jedis = this.getResource(this);
			Long response = jedis.lpush(key,  value);
			closeJedis(jedis);
			return response;
		}catch(JedisConnectionException | JedisDataException | ClassCastException ex){
			if(trials == 10){
				ex.printStackTrace();
				LOGGER.info("active: "+jedisPool.getNumActive() + " - idle:" +jedisPool.getNumIdle() + " -waiters: " + jedisPool.getNumWaiters());
				LOGGER.info("momentaryDebugCounter :" +momentaryDebugCounter);
				LOGGER.info("time of exception: " + System.currentTimeMillis());
				return null;
			}else{
				trials++;
				LOGGER.info(ex.getMessage());
				closeJedis(jedis);
				return lpush(key, value);
			}
		}
	}

	/**
	 * Add the string value to the head (LPUSH) or tail (RPUSH) of the list
	 * stored at key. If the key does not exist an empty list is created just
	 * before the append operation. If the key exists but is not a List an error
	 * is returned.
	 * <p>
	 * Time complexity: O(1)
	 * 
	 * @param key
	 * @param strings
	 * @return Integer reply, specifically, the number of elements inside the
	 *         list after the push operation.
	 */
	public Long rpush(final String key, final String value) {
		try{
			Jedis jedis = this.getResource(this);
			Long response = jedis.rpush(key, value);
			closeJedis(jedis);
			return response;
		}catch(JedisConnectionException | JedisDataException | ClassCastException ex){
			if(trials == 10){
				ex.printStackTrace();
				LOGGER.info("active: "+jedisPool.getNumActive() + " - idle:" +jedisPool.getNumIdle() + " -waiters: " + jedisPool.getNumWaiters());
				LOGGER.info("momentaryDebugCounter :" +momentaryDebugCounter);
				LOGGER.info("time of exception: " + System.currentTimeMillis());
				return null;
			}else{
				trials++;
				LOGGER.info(ex.getMessage());
				closeJedis(jedis);
				return rpush(key, value);
			}
		}
	}

	/**
	 * Atomically return and remove the first (LPOP) or last (RPOP) element of
	 * the list. For example if the list contains the elements "a","b","c" LPOP
	 * will return "a" and the list will become "b","c".
	 * <p>
	 * If the key does not exist or the list is already empty the special value
	 * 'nil' is returned.
	 * 
	 * @see #rpop(String)
	 * @param key
	 * @return Bulk reply
	 */
	public String lpop(final String key) {
		try{
			Jedis jedis = this.getResource(this);
			String response = jedis.lpop(key);
			closeJedis(jedis);
			return response;
		}catch(JedisConnectionException | JedisDataException | ClassCastException ex){
			if(trials == 10){
				ex.printStackTrace();
				LOGGER.info("active: "+jedisPool.getNumActive() + " - idle:" +jedisPool.getNumIdle() + " -waiters: " + jedisPool.getNumWaiters());
				LOGGER.info("momentaryDebugCounter :" +momentaryDebugCounter);
				LOGGER.info("time of exception: " + System.currentTimeMillis());
				return null;
			}else{
				trials++;
				LOGGER.info(ex.getMessage());
				closeJedis(jedis);
				return lpop(key);
			}
		}
	}

	/**
	 * Atomically return and remove the first (LPOP) or last (RPOP) element of
	 * the list. For example if the list contains the elements "a","b","c" RPOP
	 * will return "c" and the list will become "a","b".
	 * <p>
	 * If the key does not exist or the list is already empty the special value
	 * 'nil' is returned.
	 * 
	 * @see #lpop(String)
	 * @param key
	 * @return Bulk reply
	 */
	public String rpop(final String key) {
		try{
			Jedis jedis = this.getResource(this);
			String response = this.getResource(this).rpop(key);
			closeJedis(jedis);
			return response;
		}catch(JedisConnectionException | JedisDataException | ClassCastException ex){
			if(trials == 10){
				ex.printStackTrace();
				LOGGER.info("active: "+jedisPool.getNumActive() + " - idle:" +jedisPool.getNumIdle() + " -waiters: " + jedisPool.getNumWaiters());
				LOGGER.info("momentaryDebugCounter :" +momentaryDebugCounter);
				LOGGER.info("time of exception: " + System.currentTimeMillis());
				return null;
			}else{
				trials++;
				LOGGER.info(ex.getMessage());
				closeJedis(jedis);
				return rpop(key);
			}
		}
	}

	/**
	 * Remove the first count occurrences of the value element from the list. If
	 * count is zero all the elements are removed. If count is negative elements
	 * are removed from tail to head, instead to go from head to tail that is
	 * the normal behaviour. So for example LREM with count -2 and hello as
	 * value to remove against the list (a,b,c,hello,x,hello,hello) will lave
	 * the list (a,b,c,hello,x). The number of removed elements is returned as
	 * an integer, see below for more information about the returned value. Note
	 * that non existing keys are considered like empty lists by LREM, so LREM
	 * against non existing keys will always return 0.
	 * <p>
	 * Time complexity: O(N) (with N being the length of the list)
	 * 
	 * @param key
	 * @param count
	 * @param value
	 * @return Integer Reply, specifically: The number of removed elements if
	 *         the operation succeeded
	 */
	public Long lrem(final String key, final int count, final String value) {
		try{
			Jedis jedis = this.getResource(this);
			Long response = jedis.lrem(key, count, value);
			closeJedis(jedis);
			return response;
		}catch(JedisConnectionException | JedisDataException | ClassCastException ex){
			if(trials == 10){
				ex.printStackTrace();
				LOGGER.info("active: "+jedisPool.getNumActive() + " - idle:" +jedisPool.getNumIdle() + " -waiters: " + jedisPool.getNumWaiters());
				LOGGER.info("momentaryDebugCounter :" +momentaryDebugCounter);
				LOGGER.info("time of exception: " + System.currentTimeMillis());
				return null;
			}else{
				trials++;
				LOGGER.info(ex.getMessage());
				closeJedis(jedis);
				return lrem(key, count, value);
			}
		}
	}

	/**
	 * Increment the number stored at key by one. If the key does not exist or
	 * contains a value of a wrong type, set the key to the value of "0" before
	 * to perform the increment operation.
	 * <p>
	 * INCR commands are limited to 64 bit signed integers.
	 * <p>
	 * Note: this is actually a string operation, that is, in Redis there are
	 * not "integer" types. Simply the string stored at the key is parsed as a
	 * base 10 64 bit signed integer, incremented, and then converted back as a
	 * string.
	 * <p>
	 * Time complexity: O(1)
	 * 
	 * @see #incrBy(String, long)
	 * @see #decr(String)
	 * @see #decrBy(String, long)
	 * @param key
	 * @return Integer reply, this commands will reply with the new value of key
	 *         after the increment.
	 */
	public Long incr(final String key) {
		try{
			Jedis jedis = this.getResource(this);
			Long response = jedis.incr(key);
			closeJedis(jedis);
			return response;
		}catch(JedisConnectionException | JedisDataException | ClassCastException ex){
			if(trials == 10){
				ex.printStackTrace();
				LOGGER.info("active: "+jedisPool.getNumActive() + " - idle:" +jedisPool.getNumIdle() + " -waiters: " + jedisPool.getNumWaiters());
				LOGGER.info("momentaryDebugCounter :" +momentaryDebugCounter);
				LOGGER.info("time of exception: " + System.currentTimeMillis());
				return null;
			}else{
				trials++;
				LOGGER.info(ex.getMessage());
				closeJedis(jedis);
				return incr(key);
			}
		}
	}

	/**
	 * Decrement the number stored at key by one. If the key does not exist or
	 * contains a value of a wrong type, set the key to the value of "0" before
	 * to perform the decrement operation.
	 * <p>
	 * INCR commands are limited to 64 bit signed integers.
	 * <p>
	 * Note: this is actually a string operation, that is, in Redis there are
	 * not "integer" types. Simply the string stored at the key is parsed as a
	 * base 10 64 bit signed integer, incremented, and then converted back as a
	 * string.
	 * <p>
	 * Time complexity: O(1)
	 * 
	 * @see #incr(String)
	 * @see #incrBy(String, long)
	 * @see #decrBy(String, long)
	 * @param key
	 * @return Integer reply, this commands will reply with the new value of key
	 *         after the increment.
	 */
	public Long decr(final String key) {
		try{
			Jedis jedis = this.getResource(this);
			Long response = jedis.decr(key);
			closeJedis(jedis);
			return response;
		}catch(JedisConnectionException | JedisDataException | ClassCastException ex){
			if(trials == 10){
				ex.printStackTrace();
				LOGGER.info("active: "+jedisPool.getNumActive() + " - idle:" +jedisPool.getNumIdle() + " -waiters: " + jedisPool.getNumWaiters());
				LOGGER.info("momentaryDebugCounter :" +momentaryDebugCounter);
				LOGGER.info("time of exception: " + System.currentTimeMillis());
				return null;
			}else{
				trials++;
				LOGGER.info(ex.getMessage());
				closeJedis(jedis);
				return decr(key);
			}
		}
	}

	/**
	 * INCRBY work just like {@link #incr(String) INCR} but instead to increment by 1 the increment is
	 * integer.
	 * <p>
	 * INCR commands are limited to 64 bit signed integers.
	 * <p>
	 * Note: this is actually a string operation, that is, in Redis there are not "integer" types.
	 * Simply the string stored at the key is parsed as a base 10 64 bit signed integer, incremented,
	 * and then converted back as a string.
	 * <p>
	 * Time complexity: O(1)
	 * @see #incr(String)
	 * @see #decr(String)
	 * @see #decrBy(String, long)
	 * @param key
	 * @param integer
	 * @return Integer reply, this commands will reply with the new value of key after the increment.
	 */
	public Long incrBy(final String key, final long integer) {
		try{
			Jedis jedis = this.getResource(this);
			Long response = jedis.incrBy(key, integer);
			closeJedis(jedis);
			return response;
		}catch(JedisConnectionException | JedisDataException | ClassCastException ex){
			if(trials == 10){
				ex.printStackTrace();
				LOGGER.info("active: "+jedisPool.getNumActive() + " - idle:" +jedisPool.getNumIdle() + " -waiters: " + jedisPool.getNumWaiters());
				LOGGER.info("momentaryDebugCounter :" +momentaryDebugCounter);
				LOGGER.info("time of exception: " + System.currentTimeMillis());
				return null;
			}else{
				trials++;
				LOGGER.info(ex.getMessage());
				closeJedis(jedis);
				return incrBy(key, integer);
			}
		}
	}

	/**
	 * IDECRBY work just like {@link #decr(String) INCR} but instead to decrement by 1 the decrement
	 * is integer.
	 * <p>
	 * INCR commands are limited to 64 bit signed integers.
	 * <p>
	 * Note: this is actually a string operation, that is, in Redis there are not "integer" types.
	 * Simply the string stored at the key is parsed as a base 10 64 bit signed integer, incremented,
	 * and then converted back as a string.
	 * <p>
	 * Time complexity: O(1)
	 * @see #incr(String)
	 * @see #decr(String)
	 * @see #incrBy(String, long)
	 * @param key
	 * @param integer
	 * @return Integer reply, this commands will reply with the new value of key after the increment.
	 */
	public Long decrBy(final String key, final long integer) {
		try{
			Jedis jedis = this.getResource(this);
			Long response = jedis.decrBy(key, integer);
			closeJedis(jedis);
			return response;
		}catch(JedisConnectionException | JedisDataException | ClassCastException ex){
			if(trials == 10){
				ex.printStackTrace();
				LOGGER.info("active: "+jedisPool.getNumActive() + " - idle:" +jedisPool.getNumIdle() + " -waiters: " + jedisPool.getNumWaiters());
				LOGGER.info("momentaryDebugCounter :" +momentaryDebugCounter);
				LOGGER.info("time of exception: " + System.currentTimeMillis());
				return null;
			}else{
				trials++;
				LOGGER.info(ex.getMessage());
				closeJedis(jedis);
				return decrBy(key, integer);
			}
		}
	}

	/**
	 * Return the length of the list stored at the specified key. If the key
	 * does not exist zero is returned (the same behaviour as for empty lists).
	 * If the value stored at key is not a list an error is returned.
	 * <p>
	 * Time complexity: O(1)
	 * 
	 * @param key
	 * @return The length of the list.
	 */
	public Long llen(final String key) {
		try{
			Jedis jedis = this.getResource(this);
			Long response = jedis.llen(key);
			closeJedis(jedis);
			return response;
		}catch(JedisConnectionException | JedisDataException | ClassCastException ex){
			if(trials == 10){
				ex.printStackTrace();
				LOGGER.info("active: "+jedisPool.getNumActive() + " - idle:" +jedisPool.getNumIdle() + " -waiters: " + jedisPool.getNumWaiters());
				LOGGER.info("momentaryDebugCounter :" +momentaryDebugCounter);
				LOGGER.info("time of exception: " + System.currentTimeMillis());
				return null;
			}else{
				trials++;
				LOGGER.info(ex.getMessage());
				closeJedis(jedis);
				return llen(key);
			}
		}
	}

	/**
	 * Set a timeout on the specified key. After the timeout the key will be
	 * automatically deleted by the server. A key with an associated timeout is
	 * said to be volatile in Redis terminology.
	 * <p>
	 * Voltile keys are stored on disk like the other keys, the timeout is
	 * persistent too like all the other aspects of the dataset. Saving a
	 * dataset containing expires and stopping the server does not stop the flow
	 * of time as Redis stores on disk the time when the key will no longer be
	 * available as Unix time, and not the remaining seconds.
	 * <p>
	 * Since Redis 2.1.3 you can update the value of the timeout of a key
	 * already having an expire set. It is also possible to undo the expire at
	 * all turning the key into a normal key using the {@link #persist(String)
	 * PERSIST} command.
	 * <p>
	 * Time complexity: O(1)
	 * 
	 * @see <a href="http://code.google.com/p/redis/wiki/ExpireCommand">
	 *      ExpireCommand</a>
	 * @param key
	 * @param seconds
	 * @return Integer reply, specifically: 1: the timeout was set. 0: the
	 *         timeout was not set since the key already has an associated
	 *         timeout (this may happen only in Redis versions &lt; 2.1.3, Redis
	 *         &gt;= 2.1.3 will happily update the timeout), or the key does not
	 *         exist.
	 */
	public Long expire(final String key, int seconds) {
		try{
			Jedis jedis = this.getResource(this);
			Long response = jedis.expire(key, seconds);
			closeJedis(jedis);
			return response;
		}catch(JedisConnectionException | JedisDataException | ClassCastException ex){
			if(trials == 10){
				ex.printStackTrace();
				LOGGER.info("active: "+jedisPool.getNumActive() + " - idle:" +jedisPool.getNumIdle() + " -waiters: " + jedisPool.getNumWaiters());
				LOGGER.info("momentaryDebugCounter :" +momentaryDebugCounter);
				LOGGER.info("time of exception: " + System.currentTimeMillis());
				return null;
			}else{
				trials++;
				LOGGER.info(ex.getMessage());
				closeJedis(jedis);
				return expire(key, seconds);
			}
		}
	}

	/**
	 * Test if the specified key exists. The command returns "1" if the key
	 * exists, otherwise "0" is returned. Note that even keys set with an empty
	 * string as value will return "1". Time complexity: O(1)
	 * 
	 * @param key
	 * @return Boolean reply, true if the key exists, otherwise false
	 */
	public Boolean exists(final String key) {
		try{
			Jedis jedis = this.getResource(this);
			Boolean response = jedis.exists(key);
			closeJedis(jedis);
			return response;	
		}catch(JedisConnectionException | JedisDataException | ClassCastException ex){
			if(trials == 10){
				ex.printStackTrace();
				LOGGER.info("active: "+jedisPool.getNumActive() + " - idle:" +jedisPool.getNumIdle() + " -waiters: " + jedisPool.getNumWaiters());
				LOGGER.info("momentaryDebugCounter :" +momentaryDebugCounter);
				LOGGER.info("time of exception: " + System.currentTimeMillis());
				return null;
			}else{
				trials++;
				LOGGER.info(ex.getMessage());
				closeJedis(jedis);
				return exists(key);
			}
		}
	}

	/**
	 * Undo a {@link #expire(String, int) expire} at turning the expire key into
	 * a normal key.
	 * <p>
	 * Time complexity: O(1)
	 * 
	 * @param key
	 * @return Integer reply, specifically: 1: the key is now persist. 0: the
	 *         key is not persist (only happens when key not set).
	 */
	public Long persist(final String key) {
		try{
			Jedis jedis = this.getResource(this);
			Long response = jedis.persist(key);
			closeJedis(jedis);
			return response;
		}catch(JedisConnectionException | JedisDataException | ClassCastException ex){
			if(trials == 10){
				ex.printStackTrace();
				LOGGER.info("active: "+jedisPool.getNumActive() + " - idle:" +jedisPool.getNumIdle() + " -waiters: " + jedisPool.getNumWaiters());
				LOGGER.info("momentaryDebugCounter :" +momentaryDebugCounter);
				LOGGER.info("time of exception: " + System.currentTimeMillis());
				return null;
			}else{
				trials++;
				LOGGER.info(ex.getMessage());
				closeJedis(jedis);
				return persist(key);
			}
		}
	}
	
    /**
     * @bulanuiy
     * temporary method for using mass insert in redis
     *
     * @return
     */
    public Pipeline getJedisPipeline()
    {
	return this.getResource(this).pipelined();
    }

	public void resetTrialsCounter(){
		this.trials=0;
	}

}