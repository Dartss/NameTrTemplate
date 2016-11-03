package common.queuer.redis.cache;

import jsmarty.core.common.properties.QueuesProperties;
import jsmarty.core.common.queuer.QueuerPoolHandler;
import jsmarty.core.common.queuer.QueuerPoolHandlerImpl;

/**
 * This class acts as a manager to maintain the communication with the remote
 * cache server;
 * 
 * Roles:
 * 
 * Set connection with the server Close connection with the server Get the
 * cached objects Store objects in the cache Check the existence of objects in
 * the cache
 * 
 * 
 * @author rud
 *
 */
public class CacheManager {

	private QueuerPoolHandler queuerPoolHandler;
	private String redisHost;
	private int redisPort;
	private int expireTimeSeconds;

	/**
	 * 
	 * @param host
	 * @param port
	 */
	public CacheManager(String host, int port) {
		this.redisHost = host;
		this.redisPort = port;
		this.expireTimeSeconds = QueuesProperties.getCACHE_EXPIRY_SECONDS();
		queuerPoolHandler = new QueuerPoolHandlerImpl(this.redisHost, this.redisPort);
	}

	/**
	 * Get cached objects
	 * 
	 * @param hashedBody
	 * @return
	 */
	public TextCacheObject getCachedObject(String hashedBody) {
		String idStr = queuerPoolHandler.get(hashedBody);
		if (idStr == null) {	// not in cache
			return null;
		}
		return new TextCacheObject(hashedBody, Long.parseLong(idStr));
	}

	/**
	 * Store object in cache
	 * 
	 * @param hashedBody
	 * @param entityId
	 */
	public void cacheObject(String hashedBody, Long entityId) {
		queuerPoolHandler.set(hashedBody, entityId.toString());
		queuerPoolHandler.expire(hashedBody, expireTimeSeconds);
	}

	/**
	 * Check if object exists in cache If it exists, we renew the lifetime of
	 * the object inside the cache [giving priority for the retweeted tweets to
	 * remain in the cache for a longer time, on the other hand the unimportant
	 * tweets will be deleted from cache when they expire]
	 * 
	 * @param hashedBody
	 * @return
	 * @throws Exception
	 */
	public boolean cacheContains(String hashedBody) throws Exception {
		if (queuerPoolHandler.exists(hashedBody)) {
			queuerPoolHandler.persist(hashedBody);
			queuerPoolHandler.expire(hashedBody, expireTimeSeconds);
			return true;
		} else {
			return false;
		}
	}

}