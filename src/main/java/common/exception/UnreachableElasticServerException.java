package common.exception;

public class UnreachableElasticServerException extends Exception {
	public UnreachableElasticServerException(String message){
		super(message);
	}
}
