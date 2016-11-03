package common.logging.core;

/**
 * 
 * Custom Logger interface used in the Components Loggers classes to be returned as a type
 * and classes will only have access to the declared methods of this interface 
 * 
 * 
 * @author fares
 *
 */
public interface Logger
{
    public void fatal(String message);
    public void fatal(String message, Throwable throwable);

    public void error(String message);
    public void error(String message, Throwable throwable);

    public void warn(String message);
    public void warn(String message, Throwable throwable);

    public void info(String message);
    public void info(String message, Throwable throwable);

    public void debug(String message);
    public void debug(String message, Throwable throwable);

    public void trace(String message);
    public void trace(String message, Throwable throwable);

}
