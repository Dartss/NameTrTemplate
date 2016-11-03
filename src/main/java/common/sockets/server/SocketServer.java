package common.sockets.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import jsmarty.core.common.logging.DefaultLogger;
import jsmarty.core.common.logging.core.Logger;

public class SocketServer implements Runnable
{
    private int port = 9000;
    private boolean isAsynchronous = true;
    private ServerSocket listener;
    private SocketServerCallBack socketServerCallBack;
    //
    private static Logger logger = DefaultLogger.getInstance();

    public SocketServer(SocketServerCallBack socketServerCallBack) {
	this.init(-1, socketServerCallBack, null);
    }

    public SocketServer(int port, SocketServerCallBack socketServerCallBack) {
	this.init(port, socketServerCallBack, null);
    }

    public SocketServer(SocketServerCallBack socketServerCallBack, boolean isAsynchronous) {
	this.init(-1, socketServerCallBack, isAsynchronous);
    }

    public SocketServer(int port, SocketServerCallBack socketServerCallBack, boolean isAsynchronous) {
	this.init(port, socketServerCallBack, isAsynchronous);
    }

    /**
     * Initiating the Socket Server class and listener
     * 
     * @param port
     * @param socketServerCallBack
     * @param isAsynchronous
     */
    private void init(int port, SocketServerCallBack socketServerCallBack, Boolean isAsynchronous)
    {
	this.socketServerCallBack = socketServerCallBack;
	if (port > 0)
	    this.port = port;
	if (null != isAsynchronous)
	    this.isAsynchronous = isAsynchronous;
	//
	try
	{
	    logger.debug("Initiating SocketServer on port=[" + this.port + "]");
	    //
	    this.listener = new ServerSocket(this.port);
	    //
	    new Thread(this).start();
	    //
	} catch (IOException e)
	{
	    logger.error("Error initializing ServerSocket on port=[" + this.port + "]", e);
	}
    }

    /**
     * Socket server always running and waiting requests from socket clients
     * 
     */
    @Override
    public void run()
    {
	logger.debug("Socket server start listening on port=[" + this.port + "]");
	//
	try
	{
	    while (true)
	    {
		Socket clientSocket;
		try
		{
		    if (this.listener.isClosed())
		    {
			logger.debug("Socket server listener is closed on port [" + this.port + "]");
			this.listener = new ServerSocket(this.port);
			logger.debug("Socket server listener re-initiated on port [" + this.port + "]");
		    }
		    //
		    logger.debug("Socket server waiting Messages on port=[" + this.port + "]");
		    clientSocket = this.listener.accept();
		    //
		    this.captureStringMessage(clientSocket);
		    //
		} catch (IOException e)
		{
		    logger.error("Error while waiting for Client Socket requests on port=[" + this.port + "]", e);
		}
	    }
	} finally
	{
	    try
	    {
		if (!this.listener.isClosed())
		    this.listener.close();
	    } catch (IOException e)
	    {
		logger.error("Error closing ServerSocket listener on port=[" + this.port + "]", e);
	    }
	}

    }

    /**
     * This method is used to extract the request string sent by a socket client
     * once extracted it should call the requestCallBack defined in the
     * constructor & init method
     * 
     * @param socket
     */
    private void captureStringMessage(Socket socket)
    {
	try
	{
	    logger.debug("Socket server captured a msg on [" + this.port + "]");
	    //
	    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    String request = bufferedReader.readLine();
	    //
	    logger.trace("Socket server[" + this.port + "] captured the msg [" + request + "]");
	    //
	    logger.trace("Captured msg from socket client is sent to callback [" + this.socketServerCallBack + "]...is Asynchronous=["
		    + this.isAsynchronous + "]");
	    if (this.isAsynchronous)
	    {
		final String localRequestMsg = request;
		final Socket localSocket = socket;
		new Thread(new Runnable()
		{
		    @Override
		    public void run()
		    {
			executeCallBack(localSocket, localRequestMsg);
		    }
		}).start();
	    } else
	    {
		executeCallBack(socket, request);
	    }
	} catch (Exception e)
	{
	    logger.error("Error capturing client socket message", e);
	    e.printStackTrace();
	}
    }

    /**
     * Executing the callback method
     * 
     * @param socket
     * @param request
     */
    private void executeCallBack(Socket socket, String request)
    {
	logger.debug("Socket server parsing socket client request");
	String parsedRequest = request;
	if (null != this.socketServerCallBack)
	    parsedRequest = this.socketServerCallBack.socketRequestCallback(request, socket);
	logger.debug("Socket server parsed socket client request");
	replyToClient(socket, parsedRequest);
    }

    /**
     * Used to reply back to the client with the parsed data returned from the
     * callback method
     * This method is automatically called after the return of the callback
     * method
     * 
     * @param socket
     * @param msg
     */
    public void replyToClient(Socket clientSocket, String response)
    {
	try
	{
	    logger.trace("Replying to Socket client with msg =[" + response + "]");
	    //
	    if (null != clientSocket)
	    {
		logger.debug("Replying to Socket client at REMOTE=[" + clientSocket.getRemoteSocketAddress() + ":" + clientSocket.getPort()
			+ "]...LOCAL=[" + clientSocket.getLocalAddress() + ":" + clientSocket.getLocalPort() + "]");
		//
		if (clientSocket.isConnected() && !clientSocket.isClosed())
		{
		    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		    out.println(response);
		    clientSocket.close();
		} else
		{
		    logger.warn("Connection to socket client is closed[" + clientSocket.isClosed() + "] or disconnected["
			    + !clientSocket.isConnected() + "]");
		}
	    }
	} catch (IOException e)
	{
	    logger.error("Error Replying to Socket client", e);
	}
    }

}
