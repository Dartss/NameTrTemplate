package common.rmi;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

/**
 * Here are the RMI methods used to bind or retrieve a remote stub
 */
public class RmiUtils
{

    private final static Logger LOGGER = Logger.getLogger("RmiUtils");

    public static boolean isReachable(String host)
    {
	try
	{
	    InetAddress address = InetAddress.getByName(host);
	    return address.isReachable(3000);
	} catch (UnknownHostException e)
	{
	    LOGGER.severe("Error:UnknownHostException in RmiUtils.isReachable");
	} catch (IOException e)
	{
	    LOGGER.severe("Error:IOException in RmiUtils.isReachable");
	}
	return false;
    }

    public static boolean telnetConnectionTest(String ip, int port)
    {
	InetSocketAddress address = new InetSocketAddress(ip, port);
	Socket socket = new Socket();
	boolean result;
	try
	{
	    socket.connect(address);
	    result = true;
	} catch (IOException e)
	{
	    result = false;
	    LOGGER.severe("Error:IOException in RmiUtils.telnetConnectionTest");
	} finally
	{
	    if (socket != null && !socket.isClosed())
	    {
		try
		{
		    socket.close();
		} catch (IOException e)
		{
		    LOGGER.severe("Error:IOException in RmiUtils.telnetConnectionTest -> finally");
		}
	    }
	}
	return result;
    }

    /*
     * This method is called to get an object remotely (the object that has been
     * already put using the method below "bindObject")
     * TODO this should be removed in favor of the getRemoteObject() method
     * below
     */
    public static Remote getStub(String host, int port, String name) throws MalformedURLException, RemoteException, NotBoundException
    {
	String path = "//" + host + ":" + port + "/" + name;
	if (RmiUtils.isReachable(host) && RmiUtils.telnetConnectionTest(host, port))
	{
	    Remote r = Naming.lookup(path);
	    LOGGER.info("RMI - Stub retrieved: " + path);
	    return r;
	} else
	{
	    // host is not reachable or RMI server have not been started
	    // try to connect anyway
	    try
	    {
		Remote r = Naming.lookup(path);
		LOGGER.info("RMI - Stub retrieved while pingTest or telnet test failed " + path);
		return r;
	    } catch (Exception e)
	    {
		LOGGER.severe("RMI - '" + path + "' host is not reachable");
	    }

	}
	return null;
    }

    public static Remote getRemoteObject(String host, int port, String objectName)
	    throws MalformedURLException, RemoteException, NotBoundException, Exception
    {
    	LOGGER.info("inside getRemoteObject - " + host + " - " + port +" - " + objectName);
	Registry registry = LocateRegistry.getRegistry(host, port);
	LOGGER.info("inside getRemoteObject - lookup");
	return registry.lookup(objectName);
    }

    /*
     * This method makes the object in hand "Remote" accessible via rmi
     */
    public static void bindObject(String host, int port, String bindingName, Remote toBindObj) throws RemoteException, MalformedURLException
    {
	String path = "//" + host + ":" + port + "/" + bindingName;
	try
	{
	    // special exception handler for registry creation
	    LocateRegistry.createRegistry(port);
	    LOGGER.info("java RMI registry created.");
	} catch (RemoteException e)
	{
	    LOGGER.severe("ERROR in RmiUtils.bindObject");
	    LOGGER.info("ERROR in RmiUtils.bindObject -> java RMI registry already exists.");
	}
	// Bind this object instance
	Naming.rebind(path, toBindObj);
	LOGGER.info("RMI - bound in registry: " + path);
    }

    public static void unbind(String host, int port, String bindingName)
    {
	String path = "//" + host + ":" + port + "/" + bindingName;
	try
	{
	    LocateRegistry.createRegistry(port);
	    LOGGER.info("java RMI registry created.");
	} catch (RemoteException e)
	{
	    LOGGER.info("java RMI registry already exists.");
	}
	try
	{
	    Naming.unbind(path);
	} catch (RemoteException e)
	{
	    LOGGER.severe("ERROR:RemoteException in RmiUtils.unbind");
	} catch (MalformedURLException e)
	{
	    LOGGER.severe("ERROR:MalformedURLException in RmiUtils.unbind");
	} catch (NotBoundException e)
	{
	    LOGGER.severe("ERROR:NotBoundException in RmiUtils.unbind");
	}
    }

    public static void setServeHostnameConfiguration(String host)
    {
	System.setProperty("java.rmi.server.hostname", host);
    }

    /**
     * @param object
     *            the remote object to be exported
     * @param port
     *            the port to export the object on
     * @return remote object stub
     * @throws RemoteException
     * 
     */
    public static Remote exportObject(Remote object, int port) throws RemoteException
    {
	// UnicastRemoteObject.unexportObject(object, false);
	return UnicastRemoteObject.exportObject(object, 0);
    }

}