package common.exception;

import javax.naming.LimitExceededException;

/**
 * Exception thrown by alchemy @patrick
 * @author rud
 * 
 * TODO An exception handling concept needs to be created and implemented in all JSmarty;
 *
 */
public class DailyTransactionLimitExceededException extends LimitExceededException {
	
	public DailyTransactionLimitExceededException(String message) {
		super(message);
	}
	
}