package common.queuer;

public class QueuerPoolConfig {

	// The maximum number of active connections that can be allocated from this
	// pool at the same time.
	private int maxTotal;

	// The maximum number of connections that should be kept in the pool at all
	// times
	private int maxIdle;

	// The minimum number of established connections that should be kept in the
	// pool at all times. The connection pool can shrink below this number if
	// validation queries fail.
	private int minIdle;

	// The maximum number of milliseconds that the pool will wait (when there
	// are no available connections) for a connection to be returned before
	// throwing an exception.
	private long maxWaitMillis;

	// The minimum amount of time an object may sit idle in the pool before it
	// is eligible for eviction
	private long minEvictableIdleTimeMillis;

	// The number of milliseconds to sleep between runs of the idle connection
	// validation/cleaner thread. This value should not be set under 1 second.
	// It dictates how often we check for idle, abandoned connections, and how
	// often we validate idle connections.
	private long timeBetweenEvictionRunsMillis;

	public QueuerPoolConfig() {

	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public int getMaxTotal() {
		return maxTotal;
	}

	public long getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public void setMaxWaitMillis(long maxWait) {
		this.maxWaitMillis = maxWait;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public long getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public long getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

}
